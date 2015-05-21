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
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.FamilyElementForPersonEditData;
import pl.sebcel.genealogy.dto.edit.PersonEditData;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Tree;
import pl.sebcel.genealogy.gui.control.Label;
import pl.sebcel.genealogy.gui.control.MultiValueReference;
import pl.sebcel.genealogy.gui.control.TextArea;
import pl.sebcel.genealogy.gui.control.TextField;
import pl.sebcel.genealogy.gui.control.SingleValueReference;

public class PersonEditComponent extends AbstractEditComponent {

    public final static long serialVersionUID = 0l;

    private PersonEditData personEditData;

    private Label lSex = new Label("P³eæ:");
    private Label lNames = new Label("Names:");
    private Label lSurname = new Label("Surname:");
    private Label lBirthDate = new Label("Data urodzenia:");
    private Label lBirthPlace = new Label("Miejsce urodzenia:");
    private Label lDeathDate = new Label("Data œmierci:");
    private Label lDeathPlace = new Label("Miejsce œmierci:");
    private Label lBurialDate = new Label("Data pochowania:");
    private Label lBurialPlace = new Label("Miejsce pochowania:");
    private Label lMiejsceZamiekszania = new Label("Miejsce zamieszkania:");
    private Label lEducation = new Label("Wykszta³cenie");
    private Label lOccupation = new Label("Zawody wykonywane");
    private Label lParents = new Label("Parents");
    private Label lDescription = new Label("Description");
    private Label lFamily = new Label("Rodzina");
    private Label lRelatedDocuments = new Label("Dokumenty");

    private SingleValueReference tSex = new SingleValueReference();
    private TextField tNames = new TextField();
    private TextField tSurname = new TextField();
    private TextField tBirthDate = new TextField();
    private TextField tBirthPlace = new TextField();
    private TextField tDeathDate = new TextField();
    private TextField tDeathPlace = new TextField();
    private TextField tBurialDate = new TextField();
    private TextField tBurialPlace = new TextField();
    private TextField tResidence = new TextField();
    private TextField tEducation = new TextField();
    private TextField tOccupation = new TextField();
    private TextArea tDescription = new TextArea();
    private SingleValueReference tParents = new SingleValueReference();
    private Tree tFamily = new Tree();
    private MultiValueReference tRelatedDocuments = new MultiValueReference();

    public PersonEditComponent() {
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
        this.add(lFamily, lFamily.getConstraints(0, y++));
        this.add(lRelatedDocuments, lRelatedDocuments.getConstraints(0, y++));

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
        this.add(tFamily, tFamily.getConstraints(1, y++));
        this.add(tRelatedDocuments, tRelatedDocuments.getConstraints(1, y++));

        tSex.removeAllItems();
        tSex.addItem("<Wybierz>");
        tSex.addItem("Mê¿czyzna");
        tSex.addItem("Female");
    }

    public void loadData(Long personId) {
        refreshLists();

        if (personId == null) {
            clearAllFields();
            return;
        }
        personEditData = DatabaseDelegate.getPersonEditData(personId);

        if (personEditData.getSex() == null) {
            tSex.setSelectedIndex(0);
        } else if (personEditData.getSex().equalsIgnoreCase("male")) {
            tSex.setSelectedIndex(1);
        } else if (personEditData.getSex().equalsIgnoreCase("female")) {
            tSex.setSelectedIndex(2);
        } else {
            tSex.setSelectedIndex(0);
        }
        tNames.setText(personEditData.getNames());
        tSurname.setText(personEditData.getSurname());
        tBirthDate.setText(personEditData.getBirthDate());
        tBirthPlace.setText(personEditData.getBirthPlace());
        tDeathDate.setText(personEditData.getDeathDate());
        tDeathPlace.setText(personEditData.getDeathPlace());
        tBurialDate.setText(personEditData.getBurialDate());
        tBurialPlace.setText(personEditData.getBurialPlace());
        tEducation.setText(personEditData.getEducation());
        tOccupation.setText(personEditData.getOccupation());
        tResidence.setText(personEditData.getResidence());
        tDescription.setText(personEditData.getDescription());
        if (personEditData.getParents() != null) {
            tParents.setSelectedItem(personEditData.getParents());
        } else {
            tParents.setSelectedIndex(0);
        }

        List<FamilyElementForPersonEditData> families = personEditData.getFamilies();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        if (families != null && families.size() > 0) {
            for (FamilyElementForPersonEditData family : families) {
                DefaultMutableTreeNode familyNode = new DefaultMutableTreeNode(family.getSpouse());
                root.add(familyNode);
                List<ReferenceListElement> children = family.getChildren();
                if (children != null && children.size() > 0) {
                    for (ReferenceListElement child : children) {
                        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
                        familyNode.add(childNode);
                    }
                }
            }
        }
        TreeModel treeModel = new DefaultTreeModel(root);
        tFamily.setModel(treeModel);

        List<ReferenceListElement> relatedDocuments = new ArrayList<ReferenceListElement>(personEditData.getRelatedDocuments());
        tRelatedDocuments.setSelectedItems(relatedDocuments);
    }

    private void refreshLists() {
        List<ReferenceListElement> allRelationships = DatabaseDelegate.getRelationships();
        List<ReferenceListElement> allDocuments = DatabaseDelegate.getDocuments();
        tParents.removeAllItems();
        tParents.addItem(new String("<Brak>"));
        for (ReferenceListElement relationship : allRelationships) {
            tParents.addItem(relationship);
        }

        tRelatedDocuments.setAllItems(allDocuments);
    }

    public boolean saveData() {
        if (editMode == EditMode.CREATE_NEW)
            personEditData = new PersonEditData();

        if (tSex.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wybierz p³eæ", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tSex.getSelectedIndex() == 1) {
            personEditData.setSex("male");
        } else {
            personEditData.setSex("female");
        }
        personEditData.setNames(tNames.getText().trim());
        personEditData.setSurname(tSurname.getText().trim());
        personEditData.setBirthDate(tBirthDate.getText().trim());
        personEditData.setBirthPlace(tBirthPlace.getText().trim());
        personEditData.setDeathDate(tDeathDate.getText().trim());
        personEditData.setDeathPlace(tDeathPlace.getText().trim());
        personEditData.setBurialDate(tBurialDate.getText().trim());
        personEditData.setBurialPlace(tBurialPlace.getText().trim());
        personEditData.setEducation(tEducation.getText().trim());
        personEditData.setOccupation(tOccupation.getText().trim());
        personEditData.setResidence(tResidence.getText().trim());
        personEditData.setDescription(tDescription.getText().trim());
        if (tParents.getSelectedIndex() == 0) {
            personEditData.setParents(null);
        } else {
            personEditData.setParents((ReferenceListElement) tParents.getSelectedItem());
        }
        personEditData.setRelatedDocuments(new HashSet<ReferenceListElement>(tRelatedDocuments.getSelectedItems()));

        if (editMode == EditMode.EDIT_EXISTING) {
            DatabaseDelegate.saveEditedPerson(personEditData);
        } else {
            DatabaseDelegate.saveNewPerson(personEditData);
        }

        return true;
    }

    private void clearAllFields() {
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
        tFamily.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
    }

    @Override
    public void deleteElement(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ tê osobê?", "Usuwanie osoby", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deletePersonOsobe(id);
        }
    }

    @Override
    public String getTitle() {
        if (editMode == EditMode.CREATE_NEW) {
            return "Dodawanie nowej osoby";
        } else {
            return "Edycja danych osoby";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        PersonEditData personEditData = DatabaseDelegate.getPersonEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.setRootId(personEditData.getId());
        diagramInfo.setName(personEditData.getNames() + " " + personEditData.getSurname() + " (" + personEditData.getId() + ")");
        diagramInfo.setDescription("Diagram osoby '" + personEditData.getNames() + " " + personEditData.getSurname() + "'");
        return diagramInfo;
    }
}