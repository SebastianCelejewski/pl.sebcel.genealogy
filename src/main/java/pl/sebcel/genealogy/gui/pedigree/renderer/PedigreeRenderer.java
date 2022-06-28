package pl.sebcel.genealogy.gui.pedigree.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.pedigree.FamilyTreeElement;
import pl.sebcel.genealogy.dto.pedigree.PersonTreeElement;
import pl.sebcel.genealogy.gui.pedigree.PedigreeChartOptions;

public class PedigreeRenderer {

	private static Color personColor = Color.RED;
    private static Color spouseColor = Color.BLUE;
    private static Color personInfoColor = Color.GRAY;
    private static Color childrenColor = Color.BLACK;
    private static Color spouseLineColor = new Color(200, 200, 200);
    private static Color marriageInfoColor = new Color(0, 200, 0);
	
    public Component drawTree(Long personId, Font font, int widthOfGeneration, PedigreeAdapter g, PedigreeChartOptions chartOptions) {
    	g.initialize(font.getSize(), widthOfGeneration);
    	Dimension dimension = draw(personId, g, 0, 0, widthOfGeneration, chartOptions);
    	return g.getResult(dimension);
    }
    
    private Dimension draw(Long personId, PedigreeAdapter g, int x, int y, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = 12; //g.getFont().getSize();
        int width = widthOfGeneration * fontSize;
        int height = fontSize;

        PersonTreeElement person = DatabaseDelegate.getPersonDataForPedigree(personId);
        String personName = person.getDescription();
        if (chartOptions.isShowIdentifiers()) {
            personName += " (" + person.getId() + ")";
        }

        g.drawText(personName, x, y + height, fontSize, personColor);

        String birthInfo = person.getBirthData();
        if (birthInfo.length() > 0 && chartOptions.isShowBirthInfo()) {
            height += fontSize;
            g.drawText(birthInfo, x, y + height, fontSize, personInfoColor);
        }
        String deathInfo = person.getDeathData();
        if (deathInfo.length() > 0 && chartOptions.isShowDeathInfo()) {
            height += fontSize;
            g.drawText(deathInfo, x, y + height, fontSize, personInfoColor);
        }
        String residenceInfo = person.getResidenceData();
        if (residenceInfo.length() > 0 && chartOptions.isShowResidenceInfo()) {
            height += fontSize;
            g.drawText(residenceInfo, x, y + height, fontSize, personInfoColor);
        }
        String occupationInfo = person.getOccupationData();
        if (occupationInfo.length() > 0 && chartOptions.isShowOccupationInfo()) {
            height += fontSize;
            g.drawText(occupationInfo, x, y + height, fontSize, personInfoColor);
        }
        if (birthInfo.length() > 0 || deathInfo.length() > 0 || occupationInfo.length() > 0 || residenceInfo.length() > 0) {
            height += fontSize / 2;
        }
        
        int personNameWidth = g.getTextWidth(personName);
        Dimension familiesDimension = drawFamilies(person, g, x, y + height, personNameWidth, height, widthOfGeneration, chartOptions);
        height += familiesDimension.height;
        if (familiesDimension.width > width) {
            width = familiesDimension.width;
        }

        return new Dimension(width, height);
    }

    private Dimension drawFamilies(PersonTreeElement person, PedigreeAdapter g, int x, int y, int spouseInfoWidth, int spouseInfoHeight, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFontSize();
        int height = 0;
        int width = widthOfGeneration * fontSize;

        List<FamilyTreeElement> families = person.getFamilies();

        if (families != null && families.size() > 0) {
            int x1 = 0;
            int y1 = 0;
            int counter = 0;
            for (FamilyTreeElement family : families) {
                Long spouseId = family.getSpouseId();
                PersonTreeElement spouse = DatabaseDelegate.getPersonDataForPedigree(spouseId);

                int x0 = x;
                int y0 = y + height;
                if (x1 > 0 && y1 > 0) {
                    g.drawLine(x0, y0, x1, y1, spouseLineColor);
                }

                x1 = x0;
                y1 = y0 + fontSize;
                
                Dimension familiesDimension = drawFamily(family, spouse, g, x, y + height, counter == 0, spouseInfoWidth, spouseInfoHeight, widthOfGeneration, chartOptions);
                int familiesHeight = familiesDimension.height;
                if (familiesDimension.width > width) {
                    width = familiesDimension.width;
                }
                height += familiesHeight + fontSize;
                counter++;
            }
        }

        return new Dimension(width, height);
    }

    private Dimension drawFamily(FamilyTreeElement family, PersonTreeElement spouse, PedigreeAdapter g, int x, int y, boolean lowered, int spouseInfoWidth, int spouseInfoHeight, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFontSize();
        int width = fontSize * widthOfGeneration;

        int childrenWidth = 0;
        int childrenHeight = 0;

        Dimension spouseDimension = drawSpouse(family, spouse, g, x, y, widthOfGeneration, chartOptions);

        String spouseName = spouse.getDescription();
        if (chartOptions.isShowIdentifiers()) {
            spouseName += " (" + spouse.getId() + ")";
        }

        int spouseNameWidth = g.getTextWidth("+ " + spouseName);
        int margin = 5;

        if (family.getChildrenIds() != null && family.getChildrenIds().size() > 0) {
            int yy = y;
            int xx = x + spouseNameWidth + margin;
            if (lowered) {
                yy -= spouseInfoHeight;
                xx = x + spouseInfoWidth + margin;
            }
            Dimension childrenDimension = drawChildren(family.getChildrenIds(), g, x + width, yy, widthOfGeneration, chartOptions);
            childrenHeight += childrenDimension.height;

            if (childrenDimension.width > childrenWidth) {
                childrenWidth = childrenDimension.width;
            }
            g.drawLine(xx, yy + fontSize / 2, x + width - margin, yy + fontSize / 2, childrenColor);
        }

        if (lowered) {
            childrenHeight -= spouseInfoHeight;
        }
        int familiesHeight = Math.max(spouseDimension.height, childrenHeight);

        return new Dimension(width + childrenWidth, familiesHeight);
    }

    private Dimension drawSpouse(FamilyTreeElement family, PersonTreeElement spouse, PedigreeAdapter g, int x, int y, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFontSize();
        int height = fontSize;

        String spouseName = spouse.getDescription();
        if (chartOptions.isShowIdentifiers()) {
            spouseName += " (" + spouse.getId() + ")";
        }
        int width = fontSize * widthOfGeneration;
        
        g.drawText("+ " + spouseName, x, y + fontSize, fontSize, spouseColor);
        
        
        String birthInfo = spouse.getBirthData();
        if (birthInfo.length() > 0 && chartOptions.isShowBirthInfo()) {
            height += fontSize;
            g.drawText(birthInfo, x, y + height, fontSize, personInfoColor);
        }
        String deathInfo = spouse.getDeathData();
        if (deathInfo.length() > 0 && chartOptions.isShowDeathInfo()) {
            height += fontSize;
            g.drawText(deathInfo, x, y + height, fontSize, personInfoColor);
        }
        String residenceInfo = spouse.getResidenceData();
        if (residenceInfo.length() > 0 && chartOptions.isShowResidenceInfo()) {
            height += fontSize;
            g.drawText(residenceInfo, x, y + height, fontSize, personInfoColor);
        }
        String occupationInfo = spouse.getOccupationData();
        if (occupationInfo.length() > 0 && chartOptions.isShowOccupationInfo()) {
            height += fontSize;
            g.drawText(occupationInfo, x, y + height, fontSize, personInfoColor);
        }

        height += fontSize / 2;

        if (chartOptions.isShowIdentifiers()) {
            String relationshipInfo = "Id związku: " + family.getRelationshipId();
            height += fontSize;
            g.drawText(relationshipInfo, x, y + height, fontSize, marriageInfoColor);
        }

        String firstMetInfo = family.getFirstMetData();
        if (firstMetInfo.length() > 0 && chartOptions.isShowFirstMetInfo()) {
            height += fontSize;
            g.drawText(firstMetInfo, x, y + height, fontSize, marriageInfoColor);
        }
        String marriageInfo = family.getMarriageData();
        if (marriageInfo.length() > 0 && chartOptions.isShowMarriageInfo()) {
            height += fontSize;
            g.drawText(marriageInfo, x, y + height, fontSize, marriageInfoColor);
        }
        String separationInfo = family.getSeparationData();
        if (separationInfo.length() > 0 && chartOptions.isShowSeparationInfo()) {
            height += fontSize;
            g.drawText(separationInfo, x, y + height, fontSize, marriageInfoColor);
        }
        String divorceInfo = family.getDivorceData();
        if (divorceInfo.length() > 0 && chartOptions.isShowDivorceInfo()) {
            height += fontSize;
            g.drawText(divorceInfo, x, y + height, fontSize, marriageInfoColor);
        }

        return new Dimension(width, height);
    }

    private Dimension drawChildren(List<Long> childrenIds, PedigreeAdapter g, int x, int y, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFontSize();
        int height = 0;
        int width = widthOfGeneration * fontSize;
        int oldX = 0;
        int oldY = 0;

        if (childrenIds != null && childrenIds.size() > 0) {
            for (Long childId : childrenIds) {
                Dimension childDimension = draw(childId, g, x + fontSize, y + height, widthOfGeneration, chartOptions);
                int childHeight = childDimension.height;
                if (childDimension.width > width) {
                    width = childDimension.width;
                }
                int x0 = x - fontSize / 2;
                int y0 = y + height + fontSize / 2;
                int x1 = x + fontSize / 2;
                int y1 = y + height + fontSize / 2;
                g.drawLine(x0, y0, x1, y1, childrenColor);
                
                if (oldX > 0 && oldY > 0) {
                    g.drawLine(x0, y0, oldX, oldY, childrenColor);
                }
                
                height += childHeight + fontSize;
                oldX = x0;
                oldY = y0;
            }
        }

        return new Dimension(width, height);
    }
}