package pl.sebcel.genealogy.gui.component;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.PersonEditData;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.dto.RodzinaStruct;
import pl.sebcel.genealogy.gui.control.Drzewo;
import pl.sebcel.genealogy.gui.control.Etykieta;
import pl.sebcel.genealogy.gui.control.MultiValueReferenceField;
import pl.sebcel.genealogy.gui.control.ObszarTekstowy;
import pl.sebcel.genealogy.gui.control.PoleTekstowe;
import pl.sebcel.genealogy.gui.control.SingleValueReferenceField;

public class EdycjaOsobyKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private PersonEditData daneOsoby;

    private Etykieta lSex = new Etykieta("P³eæ:");
    private Etykieta lNames = new Etykieta("Names:");
    private Etykieta lSurname = new Etykieta("Surname:");
    private Etykieta lBirthDate = new Etykieta("Data urodzenia:");
    private Etykieta lBirthPlace = new Etykieta("Miejsce urodzenia:");
    private Etykieta lDeathDate = new Etykieta("Data œmierci:");
    private Etykieta lDeathPlace = new Etykieta("Miejsce œmierci:");
    private Etykieta lBurialDate = new Etykieta("Data pochowania:");
    private Etykieta lBurialPlace = new Etykieta("Miejsce pochowania:");
    private Etykieta lMiejsceZamiekszania = new Etykieta("Miejsce zamieszkania:");
    private Etykieta lEducation = new Etykieta("Wykszta³cenie");
    private Etykieta lOccupation = new Etykieta("Zawody wykonywane");
    private Etykieta lParents = new Etykieta("Parents");
    private Etykieta lDescription = new Etykieta("Description");
    private Etykieta lRodzina = new Etykieta("Rodzina");
    private Etykieta lDokumenty = new Etykieta("Dokumenty");

    private SingleValueReferenceField tSex = new SingleValueReferenceField();
    private PoleTekstowe tNames = new PoleTekstowe();
    private PoleTekstowe tSurname = new PoleTekstowe();
    private PoleTekstowe tBirthDate = new PoleTekstowe();
    private PoleTekstowe tBirthPlace = new PoleTekstowe();
    private PoleTekstowe tDeathDate = new PoleTekstowe();
    private PoleTekstowe tDeathPlace = new PoleTekstowe();
    private PoleTekstowe tBurialDate = new PoleTekstowe();
    private PoleTekstowe tBurialPlace = new PoleTekstowe();
    private PoleTekstowe tResidence = new PoleTekstowe();
    private PoleTekstowe tEducation = new PoleTekstowe();
    private PoleTekstowe tOccupation = new PoleTekstowe();
    private ObszarTekstowy tDescription = new ObszarTekstowy();
    private SingleValueReferenceField tParents = new SingleValueReferenceField();
    private Drzewo tRodzina = new Drzewo();
    private MultiValueReferenceField tDokumenty = new MultiValueReferenceField();

    public EdycjaOsobyKomponent() {
        this.setLayout(new GridBagLayout());

        int y = 0;
        this.add(lSex, lSex.getConstraints(0, y++));
        this.add(lNames, lNames.getConstraints(0, y++));
        this.add(lSurname, lSurname.getConstraints(0, y++));
        this.add(lBirthDate, lBirthDate.getConstraints(0, y++));
        this.add(lBirthPlace, lBirthPlace.getConstraints(0, y++));
        this.add(lDeathDate, lBirthDate.getConstraints(0, y++));
        this.add(lDeathPlace, lDeathPlace.getConstraints(0, y++));
        this.add(lBurialDate, lBirthDate.getConstraints(0, y++));
        this.add(lBurialPlace, lBurialPlace.getConstraints(0, y++));
        this.add(lMiejsceZamiekszania, lMiejsceZamiekszania.getConstraints(0, y++));
        this.add(lEducation, lEducation.getConstraints(0, y++));
        this.add(lOccupation, lOccupation.getConstraints(0, y++));
        this.add(lParents, lParents.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));
        this.add(lRodzina, lRodzina.getConstraints(0, y++));
        this.add(lDokumenty, lDokumenty.getConstraints(0, y++));

        y = 0;
        this.add(tSex, tSex.getConstraints(1, y++));
        this.add(tNames, tNames.getConstraints(1, y++));
        this.add(tSurname, tSurname.getConstraints(1, y++));
        this.add(tBirthDate, tBirthDate.getConstraints(1, y++));
        this.add(tBirthPlace, tBirthPlace.getConstraints(1, y++));
        this.add(tDeathDate, tDeathDate.getConstraints(1, y++));
        this.add(tDeathPlace, tDeathPlace.getConstraints(1, y++));
        this.add(tBurialDate, tBurialDate.getConstraints(1, y++));
        this.add(tBurialPlace, tBurialPlace.getConstraints(1, y++));
        this.add(tResidence, tResidence.getConstraints(1, y++));
        this.add(tEducation, tEducation.getConstraints(1, y++));
        this.add(tOccupation, tOccupation.getConstraints(1, y++));
        this.add(tParents, tParents.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));
        this.add(tRodzina, tRodzina.getConstraints(1, y++));
        this.add(tDokumenty, tDokumenty.getConstraints(1, y++));

        tSex.removeAllItems();
        tSex.addItem("<Wybierz>");
        tSex.addItem("Mê¿czyzna");
        tSex.addItem("Female");
    }

    public void wczytajDane(Long idOsoby) {
        odswiezListy();

        if (idOsoby == null) {
            wyczyscPola();
            return;
        }
        daneOsoby = DatabaseDelegate.getPersonEditData(idOsoby);

        if (daneOsoby.getSex() == null) {
            tSex.setSelectedIndex(0);
        } else if (daneOsoby.getSex().equalsIgnoreCase("male")) {
            tSex.setSelectedIndex(1);
        } else if (daneOsoby.getSex().equalsIgnoreCase("female")) {
            tSex.setSelectedIndex(2);
        } else {
            tSex.setSelectedIndex(0);
        }
        tNames.setText(daneOsoby.getNames());
        tSurname.setText(daneOsoby.getSurname());
        tBirthDate.setText(daneOsoby.getBirthDate());
        tBirthPlace.setText(daneOsoby.getBirthPlace());
        tDeathDate.setText(daneOsoby.getDeathDate());
        tDeathPlace.setText(daneOsoby.getDeathPlace());
        tBurialDate.setText(daneOsoby.getBurialDate());
        tBurialPlace.setText(daneOsoby.getBurialPlace());
        tEducation.setText(daneOsoby.getEducation());
        tOccupation.setText(daneOsoby.getOccupation());
        tResidence.setText(daneOsoby.getResidence());
        tDescription.setText(daneOsoby.getDescription());
        if (daneOsoby.getParents() != null) {
            tParents.setSelectedItem(daneOsoby.getParents());
        } else {
            tParents.setSelectedIndex(0);
        }

        List<RodzinaStruct> rodziny = daneOsoby.getFamilies();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        if (rodziny != null && rodziny.size() > 0) {
            for (RodzinaStruct rodzina : rodziny) {
                DefaultMutableTreeNode nodeRodzina = new DefaultMutableTreeNode(rodzina.malzonek);
                root.add(nodeRodzina);
                List<ReferenceListElement> dzieci = rodzina.idDzieci;
                if (dzieci != null && dzieci.size() > 0) {
                    for (ReferenceListElement dziecko : dzieci) {
                        DefaultMutableTreeNode nodeDziecko = new DefaultMutableTreeNode(dziecko);
                        nodeRodzina.add(nodeDziecko);
                    }
                }
            }
        }
        TreeModel treeModel = new DefaultTreeModel(root);
        tRodzina.setModel(treeModel);

        List<ReferenceListElement> dokumentyWybrane = new ArrayList<ReferenceListElement>(daneOsoby.getDokumenty());
        tDokumenty.setSelectedItems(dokumentyWybrane);
    }

    private void odswiezListy() {
        List<ReferenceListElement> zwiazki = DatabaseDelegate.getRelationships();
        List<ReferenceListElement> wszystkieDokumenty = DatabaseDelegate.getDocuments();
        tParents.removeAllItems();
        tParents.addItem(new String("<Brak>"));
        for (ReferenceListElement zwiazek : zwiazki) {
            tParents.addItem(zwiazek);
        }

        tDokumenty.setAllItems(wszystkieDokumenty);
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE)
            daneOsoby = new PersonEditData();

        if (tSex.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wybierz p³eæ", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tSex.getSelectedIndex() == 1) {
            daneOsoby.setSex("male");
        } else {
            daneOsoby.setSex("female");
        }
        daneOsoby.setNames(tNames.getText().trim());
        daneOsoby.setSurname(tSurname.getText().trim());
        daneOsoby.setBirthDate(tBirthDate.getText().trim());
        daneOsoby.setBirthPlace(tBirthPlace.getText().trim());
        daneOsoby.setDeathDate(tDeathDate.getText().trim());
        daneOsoby.setDeathPlace(tDeathPlace.getText().trim());
        daneOsoby.setBurialDate(tBurialDate.getText().trim());
        daneOsoby.setBurialPlace(tBurialPlace.getText().trim());
        daneOsoby.setEducation(tEducation.getText().trim());
        daneOsoby.setOccupation(tOccupation.getText().trim());
        daneOsoby.setResidence(tResidence.getText().trim());
        daneOsoby.setDescription(tDescription.getText().trim());
        if (tParents.getSelectedIndex() == 0) {
            daneOsoby.setParents(null);
        } else {
            daneOsoby.setParents((ReferenceListElement) tParents.getSelectedItem());
        }
        daneOsoby.setDokumenty(new HashSet<ReferenceListElement>(tDokumenty.getSelectedItems()));

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.saveEditedPerson(daneOsoby);
        } else {
            DatabaseDelegate.saveNewPerson(daneOsoby);
        }

        return true;
    }

    private void wyczyscPola() {
        tNames.setText("");
        tSurname.setText("");
        tBirthDate.setText("");
        tBirthPlace.setText("");
        tDeathDate.setText("");
        tDeathPlace.setText("");
        tBurialDate.setText("");
        tBurialPlace.setText("");
        tResidence.setText("");
        tEducation.setText("");
        tOccupation.setText("");
        tDescription.setText("");
        tParents.setSelectedIndex(0);
        tRodzina.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
    }

    @Override
    public void usunObiekt(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ tê osobê?", "Usuwanie osoby", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deletePersonOsobe(id);
        }
    }

    @Override
    public String getTitle() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            return "Dodawanie nowej osoby";
        } else {
            return "Edycja danych osoby";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        PersonEditData daneOsoby = DatabaseDelegate.getPersonEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.idKorzenia = daneOsoby.getId();
        diagramInfo.nazwa = daneOsoby.getNames() + " " + daneOsoby.getSurname() + " (" + daneOsoby.getId() + ")";
        diagramInfo.description = "Diagram osoby '" + daneOsoby.getNames() + " " + daneOsoby.getSurname() + "'";
        return diagramInfo;
    }
}