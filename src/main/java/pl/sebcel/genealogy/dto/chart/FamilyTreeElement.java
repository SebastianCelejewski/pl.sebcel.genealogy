package pl.sebcel.genealogy.dto.chart;

import java.util.List;

public class FamilyTreeElement {

    private Long relationshipId;
    private Long spouseId;
    private List<Long> childrenIds;
    private String firstMetData;
    private String marriageData;
    private String separationData;
    private String divorceData;

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Long getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(Long spouseId) {
        this.spouseId = spouseId;
    }

    public List<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public String getFirstMetData() {
        return firstMetData;
    }

    public void setFirstMetData(String firstMetData) {
        this.firstMetData = firstMetData;
    }

    public String getMarriageData() {
        return marriageData;
    }

    public void setMarriageData(String marriageData) {
        this.marriageData = marriageData;
    }

    public String getSeparationData() {
        return separationData;
    }

    public void setSeparationData(String separationData) {
        this.separationData = separationData;
    }

    public String getDivorceData() {
        return divorceData;
    }

    public void setDivorceData(String divorceData) {
        this.divorceData = divorceData;
    }

}