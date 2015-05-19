package pl.sebcel.genealogy.gui.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DocumentEditData;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Etykieta;
import pl.sebcel.genealogy.gui.control.MultiValueReferenceField;
import pl.sebcel.genealogy.gui.control.ObszarTekstowy;
import pl.sebcel.genealogy.gui.control.PoleTekstowe;

public class EdycjaDokumentuKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private DocumentEditData daneDokumentu;

    private Etykieta lTytul = new Etykieta("Tytu³:");
    private Etykieta lSymbol = new Etykieta("Symbol:");
    private Etykieta lOpis = new Etykieta("Opis:");
    private Etykieta lOsoby = new Etykieta("Osoby: ");

    private PoleTekstowe tTytul = new PoleTekstowe();
    private PoleTekstowe tSymbol = new PoleTekstowe();
    private ObszarTekstowy tOpis = new ObszarTekstowy();
    private MultiValueReferenceField tOsoby = new MultiValueReferenceField();

    public EdycjaDokumentuKomponent() {

        int y = 0;
        this.add(lTytul, lTytul.getConstraints(0, y++));
        this.add(lSymbol, lSymbol.getConstraints(0, y++));
        this.add(lOpis, lOpis.getConstraints(0, y++));
        this.add(lOsoby, lOsoby.getConstraints(0, y++));

        y = 0;
        this.add(tTytul, tTytul.getConstraints(1, y++));
        this.add(tSymbol, tSymbol.getConstraints(1, y++));
        this.add(tOpis, tOpis.getConstraints(1, y++));
        this.add(tOsoby, tOsoby.getConstraints(1, y++));
    }

    public void wczytajDane(Long id) {
        odswiezListy();

        if (id == null) {
            wyczyscPola();
            return;
        }
        daneDokumentu = DatabaseDelegate.getDocumentEditData(id);

        tTytul.setText(daneDokumentu.getTitle());
        tSymbol.setText(daneDokumentu.getSymbol());
        tOpis.setText(daneDokumentu.getDescription());
        tOsoby.setSelectedItems(new ArrayList<ReferenceListElement>(daneDokumentu.getRelatedPeople()));
    }

    private void odswiezListy() {
        List<ReferenceListElement> allPeople = DatabaseDelegate.getPeople();
        tOsoby.setAllItems(allPeople);
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            daneDokumentu = new DocumentEditData();
        }

        daneDokumentu.setTitle(tTytul.getText().trim());
        daneDokumentu.setSymbol(tSymbol.getText().trim());
        daneDokumentu.setDescription(tOpis.getText().trim());
        daneDokumentu.setRelatedPeople(new HashSet<ReferenceListElement>(tOsoby.getSelectedItems()));

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.saveEditedDocument(daneDokumentu);
        } else {
            DatabaseDelegate.saveNewDocument(daneDokumentu);
        }

        return true;
    }

    private void wyczyscPola() {
        tTytul.setText("");
        tSymbol.setText("");
        tOpis.setText("");
        tOsoby.setSelectedItems(new ArrayList<ReferenceListElement>());
    }

    @Override
    public void usunObiekt(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ ten dokument?", "Usuwanie dokumentu", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deleteDocument(id);
        }
    }

    @Override
    public String getTitle() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            return "Dodawanie nowego dokumentu";
        } else {
            return "Edycja danych dokumentu";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        throw new RuntimeException("Ta funkcja nie jest zaimplementowana.");
    }
}