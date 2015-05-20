package pl.sebcel.genealogy.gui.component;

import java.util.List;

import javax.swing.JOptionPane;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.RelationshipEditData;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Etykieta;
import pl.sebcel.genealogy.gui.control.ObszarTekstowy;
import pl.sebcel.genealogy.gui.control.PoleTekstowe;
import pl.sebcel.genealogy.gui.control.SingleValueReferenceField;

public class EdycjaZwiazkuKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private RelationshipEditData daneZwiazku;

    private Etykieta lMale = new Etykieta("Mê¿czyzna:");
    private Etykieta lFemale = new Etykieta("Female:");
    private Etykieta lFirstMetDate = new Etykieta("Data poznania:");
    private Etykieta lFirstMetPlace = new Etykieta("Miejsce poznania:");
    private Etykieta lMarriageDate = new Etykieta("Data œlubu:");
    private Etykieta lMarriagePlace = new Etykieta("Miejsce œlubu:");
    private Etykieta lSeparationDate = new Etykieta("Data rozstania:");
    private Etykieta lSeparationPlace = new Etykieta("Miejsce rozstania:");
    private Etykieta lDivorceDate = new Etykieta("Data rozwodu:");
    private Etykieta lDivorcePlace = new Etykieta("Miejsce rozwodu:");
    private Etykieta lDescription = new Etykieta("Description");

    private SingleValueReferenceField tMale = new SingleValueReferenceField();
    private SingleValueReferenceField tFemale = new SingleValueReferenceField();
    private PoleTekstowe tFirstMetDate = new PoleTekstowe();
    private PoleTekstowe tFirstMetPlace = new PoleTekstowe();
    private PoleTekstowe tMarriageDate = new PoleTekstowe();
    private PoleTekstowe tMarriagePlace = new PoleTekstowe();
    private PoleTekstowe tSeparationDate = new PoleTekstowe();
    private PoleTekstowe tSeparationPlace = new PoleTekstowe();
    private PoleTekstowe tDivorceDate = new PoleTekstowe();
    private PoleTekstowe tDivorcePlace = new PoleTekstowe();
    private ObszarTekstowy tDescription = new ObszarTekstowy();

    public EdycjaZwiazkuKomponent() {
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