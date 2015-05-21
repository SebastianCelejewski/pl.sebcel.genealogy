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

    private RelationshipEditData daneZwiazku;

    private Label lMale = new Label("Mê¿czyzna:");
    private Label lFemale = new Label("Female:");
    private Label lFirstMetDate = new Label("Data poznania:");
    private Label lFirstMetPlace = new Label("Miejsce poznania:");
    private Label lMarriageDate = new Label("Data œlubu:");
    private Label lMarriagePlace = new Label("Miejsce œlubu:");
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

    public void wczytajDane(Long idZwiazku) {
        odswiezListy();

        if (idZwiazku == null) {
            wyczyscPola();
            return;
        }
        daneZwiazku = DatabaseDelegate.getRelationshipEditData(idZwiazku);

        tMale.setSelectedItem(daneZwiazku.getMale());
        tFemale.setSelectedItem(daneZwiazku.getFemale());
        tFirstMetDate.setText(daneZwiazku.getFirstMetDate());
        tFirstMetPlace.setText(daneZwiazku.getFirstMetPlace());
        tMarriageDate.setText(daneZwiazku.getMarriageDate());
        tMarriagePlace.setText(daneZwiazku.getMarriagePlace());
        tSeparationDate.setText(daneZwiazku.getSeparationDate());
        tSeparationPlace.setText(daneZwiazku.getSeparationPlace());
        tDivorceDate.setText(daneZwiazku.getDivorceDate());
        tDivorcePlace.setText(daneZwiazku.getDivorcePlace());
        tDescription.setText(daneZwiazku.getDescription());
    }

    private void odswiezListy() {
        List<ReferenceListElement> mezczyzni = DatabaseDelegate.getMales();
        List<ReferenceListElement> kobiety = DatabaseDelegate.getFemales();

        tMale.removeAllItems();
        tFemale.removeAllItems();

        tMale.addItem("<Wybierz>");
        tFemale.addItem("<Wybierz>");

        for (ReferenceListElement osoba : mezczyzni) {
            tMale.addItem(osoba);
        }
        for (ReferenceListElement osoba : kobiety) {
            tFemale.addItem(osoba);
        }
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE)
            daneZwiazku = new RelationshipEditData();

        if (tMale.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Musisz wybraæ mê¿czyznê", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (tFemale.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Musisz wybraæ kobietê", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        daneZwiazku.setMale((ReferenceListElement) tMale.getSelectedItem());
        daneZwiazku.setFemale((ReferenceListElement) tFemale.getSelectedItem());
        daneZwiazku.setFirstMetDate(tFirstMetDate.getText().trim());
        daneZwiazku.setFirstMetPlace(tFirstMetPlace.getText().trim());
        daneZwiazku.setMarriageDate(tMarriageDate.getText().trim());
        daneZwiazku.setMarriagePlace(tMarriagePlace.getText().trim());
        daneZwiazku.setSeparationDate(tSeparationDate.getText().trim());
        daneZwiazku.setSeparationPlace(tSeparationPlace.getText().trim());
        daneZwiazku.setDivorceDate(tDivorceDate.getText().trim());
        daneZwiazku.setDivorcePlace(tDivorcePlace.getText().trim());
        daneZwiazku.setDescription(tDescription.getText().trim());

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.saveEditedRelationship(daneZwiazku);
        } else {
            DatabaseDelegate.saveNewRelationship(daneZwiazku);
        }

        return true;
    }

    private void wyczyscPola() {
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
    public void usunObiekt(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ ten zwi¹zek?", "Usuwanie zwi¹zku", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deleteRelationship(id);
        }
    }

    @Override
    public String getTitle() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            return "Dodawanie nowego zwi¹zku";
        } else {
            return "Edycja danych zwi¹zku";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        RelationshipEditData daneZwiazku = DatabaseDelegate.getRelationshipEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();

        diagramInfo.idKorzenia = daneZwiazku.getMale().getId();
        diagramInfo.nazwa = daneZwiazku.getMale().getDescription() + " i " + daneZwiazku.getFemale().getDescription();
        diagramInfo.description = "Diagram zwi¹zku " + daneZwiazku.getMale().getDescription() + " " + daneZwiazku.getFemale().getDescription();

        return diagramInfo;
    }
}