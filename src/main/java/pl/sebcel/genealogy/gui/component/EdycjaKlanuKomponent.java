package pl.sebcel.genealogy.gui.component;

import java.util.List;

import javax.swing.JOptionPane;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DaneEdycjiKlanuStruct;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Etykieta;
import pl.sebcel.genealogy.gui.control.PoleTekstowe;
import pl.sebcel.genealogy.gui.control.SingleValueReferenceField;

public class EdycjaKlanuKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private DaneEdycjiKlanuStruct daneKlanu;

    private Etykieta lNazwa = new Etykieta("Nazwa:");
    private Etykieta lOpis = new Etykieta("Opis:");
    private Etykieta lKorzen = new Etykieta("Protoplasta:");

    private PoleTekstowe tNazwa = new PoleTekstowe();
    private PoleTekstowe tOpis = new PoleTekstowe();
    private SingleValueReferenceField tKorzen = new SingleValueReferenceField();

    public EdycjaKlanuKomponent() {

        int y = 0;
        this.add(lNazwa, lNazwa.getConstraints(0, y++));
        this.add(lOpis, lOpis.getConstraints(0, y++));
        this.add(lKorzen, lKorzen.getConstraints(0, y++));

        y = 0;
        this.add(tNazwa, tNazwa.getConstraints(1, y++));
        this.add(tOpis, tOpis.getConstraints(1, y++));
        this.add(tKorzen, tKorzen.getConstraints(1, y++));

    }

    public void wczytajDane(Long id) {
        odswiezListy();

        if (id == null) {
            wyczyscPola();
            return;
        }
        daneKlanu = DatabaseDelegate.getClanEditData(id);

        tNazwa.setText(daneKlanu.nazwa);
        tOpis.setText(daneKlanu.opis);
        tKorzen.setSelectedItem(daneKlanu.korzen);

    }

    private void odswiezListy() {
        List<ReferenceListElement> osoby = DatabaseDelegate.getPeople();
        tKorzen.removeAllItems();
        tKorzen.addItem(new String("<Wybierz>"));
        for (ReferenceListElement osoba : osoby) {
            tKorzen.addItem(osoba);
        }
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE)
            daneKlanu = new DaneEdycjiKlanuStruct();

        if (tKorzen.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wska� protoplast� klanu", "B��d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        daneKlanu.nazwa = tNazwa.getText().trim();
        daneKlanu.opis = tOpis.getText().trim();
        daneKlanu.korzen = (ReferenceListElement) tKorzen.getSelectedItem();

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.saveEditedClan(daneKlanu);
        } else {
            DatabaseDelegate.saveNewClan(daneKlanu);
        }

        return true;
    }

    private void wyczyscPola() {
        tNazwa.setText("");
        tOpis.setText("");
        tKorzen.setSelectedItem(0);
    }

    @Override
    public void usunObiekt(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun�� ten klan?", "Usuwanie klanu", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deleteClan(id);
        }
    }

    @Override
    public String getTitle() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            return "Dodawanie nowego klanu";
        } else {
            return "Edycja danych klanu";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        DaneEdycjiKlanuStruct daneKlanu = DatabaseDelegate.getClanEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.idKorzenia = daneKlanu.korzen.getId();
        diagramInfo.nazwa = daneKlanu.nazwa;
        diagramInfo.opis = "Diagram klanu '" + daneKlanu.nazwa + "'";
        return diagramInfo;
    }
}