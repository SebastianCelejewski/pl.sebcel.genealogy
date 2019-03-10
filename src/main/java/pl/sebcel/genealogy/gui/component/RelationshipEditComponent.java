package pl.sebcel.genealogy.gui.component;

import java.util.List;

import javax.swing.JOptionPane;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.edit.RelationshipEditData;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Label;
import pl.sebcel.genealogy.gui.control.TextArea;
import pl.sebcel.genealogy.gui.control.TextField;
import pl.sebcel.genealogy.gui.control.SingleValueReference;

public class RelationshipEditComponent extends AbstractEditComponent {

    public final static long serialVersionUID = 0l;

    private RelationshipEditData relationshipEditData;

    private Label lMale = new Label("Mężczyzna:");
    private Label lFemale = new Label("Kobieta:");
    private Label lFirstMetDate = new Label("Data poznania:");
    private Label lFirstMetPlace = new Label("Miejsce poznania:");
    private Label lMarriageDate = new Label("Data ślubu:");
    private Label lMarriagePlace = new Label("Miejsce ślubu:");
    private Label lSeparationDate = new Label("Data rozstania:");
    private Label lSeparationPlace = new Label("Miejsce rozstania:");
    private Label lDivorceDate = new Label("Data rozwodu:");
    private Label lDivorcePlace = new Label("Miejsce rozwodu:");
    private Label lDescription = new Label("Description");

    private SingleValueReference tMale = new SingleValueReference();
    private SingleValueReference tFemale = new SingleValueReference();
    private TextField tFirstMetDate = new TextField();
    private TextField tFirstMetPlace = new TextField();
    private TextField tMarriageDate = new TextField();
    private TextField tMarriagePlace = new TextField();
    private TextField tSeparationDate = new TextField();
    private TextField tSeparationPlace = new TextField();
    private TextField tDivorceDate = new TextField();
    private TextField tDivorcePlace = new TextField();
    private TextArea tDescription = new TextArea();

    public RelationshipEditComponent() {
        int y = 0;
        this.add(lMale, lMale.getConstraints(0, y++));
        this.add(lFemale, lFemale.getConstraints(0, y++));
        this.add(lFirstMetDate, lFirstMetDate.getConstraints(0, y++));
        this.add(lFirstMetPlace, lFirstMetPlace.getConstraints(0, y++));
        this.add(lMarriageDate, lMarriageDate.getConstraints(0, y++));
        this.add(lMarriagePlace, lMarriagePlace.getConstraints(0, y++));
        this.add(lSeparationDate, lSeparationDate.getConstraints(0, y++));
        this.add(lSeparationPlace, lSeparationPlace.getConstraints(0, y++));
        this.add(lDivorceDate, lDivorceDate.getConstraints(0, y++));
        this.add(lDivorcePlace, lDivorcePlace.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));

        y = 0;
        this.add(tMale, tMale.getConstraints(1, y++));
        this.add(tFemale, tFemale.getConstraints(1, y++));
        this.add(tFirstMetDate, tFirstMetDate.getConstraints(1, y++));
        this.add(tFirstMetPlace, tFirstMetPlace.getConstraints(1, y++));
        this.add(tMarriageDate, tMarriageDate.getConstraints(1, y++));
        this.add(tMarriagePlace, tMarriagePlace.getConstraints(1, y++));
        this.add(tSeparationDate, tSeparationDate.getConstraints(1, y++));
        this.add(tSeparationPlace, tSeparationPlace.getConstraints(1, y++));
        this.add(tDivorceDate, tDivorceDate.getConstraints(1, y++));
        this.add(tDivorcePlace, tDivorcePlace.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));

    }

    public void loadData(Long idZwiazku) {
        refreshLists();

        if (idZwiazku == null) {
            clearAllFields();
            return;
        }
        relationshipEditData = DatabaseDelegate.getRelationshipEditData(idZwiazku);

        tMale.setSelectedItem(relationshipEditData.getMale());
        tFemale.setSelectedItem(relationshipEditData.getFemale());
        tFirstMetDate.setText(relationshipEditData.getFirstMetDate());
        tFirstMetPlace.setText(relationshipEditData.getFirstMetPlace());
        tMarriageDate.setText(relationshipEditData.getMarriageDate());
        tMarriagePlace.setText(relationshipEditData.getMarriagePlace());
        tSeparationDate.setText(relationshipEditData.getSeparationDate());
        tSeparationPlace.setText(relationshipEditData.getSeparationPlace());
        tDivorceDate.setText(relationshipEditData.getDivorceDate());
        tDivorcePlace.setText(relationshipEditData.getDivorcePlace());
        tDescription.setText(relationshipEditData.getDescription());
    }

    private void refreshLists() {
        List<ReferenceListElement> allMales = DatabaseDelegate.getMales();
        List<ReferenceListElement> allFemales = DatabaseDelegate.getFemales();

        tMale.removeAllItems();
        tFemale.removeAllItems();

        tMale.addItem("<Wybierz>");
        tFemale.addItem("<Wybierz>");

        for (ReferenceListElement male : allMales) {
            tMale.addItem(male);
        }
        for (ReferenceListElement female : allFemales) {
            tFemale.addItem(female);
        }
    }

    public boolean saveData() {
        if (editMode == EditMode.CREATE_NEW)
            relationshipEditData = new RelationshipEditData();

        if (tMale.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Musisz wybrać mężczyznę", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (tFemale.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Musisz wybrać kobietę", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        relationshipEditData.setMale((ReferenceListElement) tMale.getSelectedItem());
        relationshipEditData.setFemale((ReferenceListElement) tFemale.getSelectedItem());
        relationshipEditData.setFirstMetDate(tFirstMetDate.getText().trim());
        relationshipEditData.setFirstMetPlace(tFirstMetPlace.getText().trim());
        relationshipEditData.setMarriageDate(tMarriageDate.getText().trim());
        relationshipEditData.setMarriagePlace(tMarriagePlace.getText().trim());
        relationshipEditData.setSeparationDate(tSeparationDate.getText().trim());
        relationshipEditData.setSeparationPlace(tSeparationPlace.getText().trim());
        relationshipEditData.setDivorceDate(tDivorceDate.getText().trim());
        relationshipEditData.setDivorcePlace(tDivorcePlace.getText().trim());
        relationshipEditData.setDescription(tDescription.getText().trim());

        if (editMode == EditMode.EDIT_EXISTING) {
            DatabaseDelegate.saveEditedRelationship(relationshipEditData);
        } else {
            DatabaseDelegate.saveNewRelationship(relationshipEditData);
        }

        return true;
    }

    private void clearAllFields() {
        tMale.setSelectedItem(0);
        tFemale.setSelectedItem(0);
        tFirstMetDate.setText("");
        tFirstMetPlace.setText("");
        tMarriageDate.setText("");
        tMarriagePlace.setText("");
        tSeparationDate.setText("");
        tSeparationPlace.setText("");
        tDivorceDate.setText("");
        tDivorcePlace.setText("");
        tDescription.setText("");
    }

    @Override
    public void deleteElement(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usunąć ten związek?", "Usuwanie związku", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deleteRelationship(id);
        }
    }

    @Override
    public String getTitle() {
        if (editMode == EditMode.CREATE_NEW) {
            return "Dodawanie nowego związku";
        } else {
            return "Edycja danych związku";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        RelationshipEditData relationshipEditData = DatabaseDelegate.getRelationshipEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();

        diagramInfo.setRootId(relationshipEditData.getMale().getId());
        diagramInfo.setName(relationshipEditData.getMale().getDescription() + " i " + relationshipEditData.getFemale().getDescription());
        diagramInfo.setDescription("Diagram związku " + relationshipEditData.getMale().getDescription() + " " + relationshipEditData.getFemale().getDescription());

        return diagramInfo;
    }
}