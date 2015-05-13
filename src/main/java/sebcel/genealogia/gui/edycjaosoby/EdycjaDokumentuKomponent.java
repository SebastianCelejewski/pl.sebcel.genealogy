package sebcel.genealogia.gui.edycjaosoby;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import sebcel.genealogia.gui.component.Etykieta;
import sebcel.genealogia.gui.component.MultiValueReferenceField;
import sebcel.genealogia.gui.component.ObszarTekstowy;
import sebcel.genealogia.gui.component.PoleTekstowe;
import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.DaneEdycjiDokumentu;
import sebcel.genealogia.struct.DiagramInfoStruct;
import sebcel.genealogia.struct.ReferenceListElement;

public class EdycjaDokumentuKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private DaneEdycjiDokumentu daneDokumentu;

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
        daneDokumentu = DatabaseDelegate.getDaneDoEdycjiDokumentu(id);

        tTytul.setText(daneDokumentu.getTytul());
        tSymbol.setText(daneDokumentu.getSymbol());
        tOpis.setText(daneDokumentu.getOpis());
        tOsoby.setSelectedItems(new ArrayList<ReferenceListElement>(daneDokumentu.getOsoby()));
    }

    private void odswiezListy() {
        List<ReferenceListElement> allPeople = DatabaseDelegate.getOsoby();
        tOsoby.setAllItems(allPeople);
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            daneDokumentu = new DaneEdycjiDokumentu();
        }

        daneDokumentu.setTytul(tTytul.getText().trim());
        daneDokumentu.setSymbol(tSymbol.getText().trim());
        daneDokumentu.setOpis(tOpis.getText().trim());
        daneDokumentu.setOsoby(new HashSet<ReferenceListElement>(tOsoby.getSelectedItems()));

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.zapiszDanePoEdycjiDokumentu(daneDokumentu);
        } else {
            DatabaseDelegate.zapiszNowyDokument(daneDokumentu);
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
            DatabaseDelegate.usunDokument(id);
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