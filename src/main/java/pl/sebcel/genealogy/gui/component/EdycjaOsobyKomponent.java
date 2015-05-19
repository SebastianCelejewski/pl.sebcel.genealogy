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
import pl.sebcel.genealogy.dto.DaneEdycjiOsoby;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.dto.RodzinaStruct;
import pl.sebcel.genealogy.dto.ZwiazekStruct;
import pl.sebcel.genealogy.gui.control.Drzewo;
import pl.sebcel.genealogy.gui.control.Etykieta;
import pl.sebcel.genealogy.gui.control.MultiValueReferenceField;
import pl.sebcel.genealogy.gui.control.ObszarTekstowy;
import pl.sebcel.genealogy.gui.control.PoleTekstowe;
import pl.sebcel.genealogy.gui.control.SingleValueReferenceField;

public class EdycjaOsobyKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private DaneEdycjiOsoby daneOsoby;

    private Etykieta lPlec = new Etykieta("P³eæ:");
    private Etykieta lImiona = new Etykieta("Imiona:");
    private Etykieta lNazwisko = new Etykieta("Nazwisko:");
    private Etykieta lDataUrodzenia = new Etykieta("Data urodzenia:");
    private Etykieta lMiejsceUrodzenia = new Etykieta("Miejsce urodzenia:");
    private Etykieta lDataSmierci = new Etykieta("Data œmierci:");
    private Etykieta lMiejsceSmierci = new Etykieta("Miejsce œmierci:");
    private Etykieta lDataPochowania = new Etykieta("Data pochowania:");
    private Etykieta lMiejscePochowania = new Etykieta("Miejsce pochowania:");
    private Etykieta lMiejsceZamiekszania = new Etykieta("Miejsce zamieszkania:");
    private Etykieta lWyksztalcenie = new Etykieta("Wykszta³cenie");
    private Etykieta lZawodyWykonywane = new Etykieta("Zawody wykonywane");
    private Etykieta lRodzice = new Etykieta("Rodzice");
    private Etykieta lOpis = new Etykieta("Opis");
    private Etykieta lRodzina = new Etykieta("Rodzina");
    private Etykieta lDokumenty = new Etykieta("Dokumenty");

    private SingleValueReferenceField tPlec = new SingleValueReferenceField();
    private PoleTekstowe tImiona = new PoleTekstowe();
    private PoleTekstowe tNazwisko = new PoleTekstowe();
    private PoleTekstowe tDataUrodzenia = new PoleTekstowe();
    private PoleTekstowe tMiejsceUrodzenia = new PoleTekstowe();
    private PoleTekstowe tDataSmierci = new PoleTekstowe();
    private PoleTekstowe tMiejsceSmierci = new PoleTekstowe();
    private PoleTekstowe tDataPochowania = new PoleTekstowe();
    private PoleTekstowe tMiejscePochowania = new PoleTekstowe();
    private PoleTekstowe tMiejsceZamieszkania = new PoleTekstowe();
    private PoleTekstowe tWyksztalcenie = new PoleTekstowe();
    private PoleTekstowe tZawodyWykonywane = new PoleTekstowe();
    private ObszarTekstowy tOpis = new ObszarTekstowy();
    private SingleValueReferenceField tRodzice = new SingleValueReferenceField();
    private Drzewo tRodzina = new Drzewo();
    private MultiValueReferenceField tDokumenty = new MultiValueReferenceField();

    public EdycjaOsobyKomponent() {
        this.setLayout(new GridBagLayout());

        int y = 0;
        this.add(lPlec, lPlec.getConstraints(0, y++));
        this.add(lImiona, lImiona.getConstraints(0, y++));
        this.add(lNazwisko, lNazwisko.getConstraints(0, y++));
        this.add(lDataUrodzenia, lDataUrodzenia.getConstraints(0, y++));
        this.add(lMiejsceUrodzenia, lMiejsceUrodzenia.getConstraints(0, y++));
        this.add(lDataSmierci, lDataUrodzenia.getConstraints(0, y++));
        this.add(lMiejsceSmierci, lMiejsceSmierci.getConstraints(0, y++));
        this.add(lDataPochowania, lDataUrodzenia.getConstraints(0, y++));
        this.add(lMiejscePochowania, lMiejscePochowania.getConstraints(0, y++));
        this.add(lMiejsceZamiekszania, lMiejsceZamiekszania.getConstraints(0, y++));
        this.add(lWyksztalcenie, lWyksztalcenie.getConstraints(0, y++));
        this.add(lZawodyWykonywane, lZawodyWykonywane.getConstraints(0, y++));
        this.add(lRodzice, lRodzice.getConstraints(0, y++));
        this.add(lOpis, lOpis.getConstraints(0, y++));
        this.add(lRodzina, lRodzina.getConstraints(0, y++));
        this.add(lDokumenty, lDokumenty.getConstraints(0, y++));

        y = 0;
        this.add(tPlec, tPlec.getConstraints(1, y++));
        this.add(tImiona, tImiona.getConstraints(1, y++));
        this.add(tNazwisko, tNazwisko.getConstraints(1, y++));
        this.add(tDataUrodzenia, tDataUrodzenia.getConstraints(1, y++));
        this.add(tMiejsceUrodzenia, tMiejsceUrodzenia.getConstraints(1, y++));
        this.add(tDataSmierci, tDataSmierci.getConstraints(1, y++));
        this.add(tMiejsceSmierci, tMiejsceSmierci.getConstraints(1, y++));
        this.add(tDataPochowania, tDataPochowania.getConstraints(1, y++));
        this.add(tMiejscePochowania, tMiejscePochowania.getConstraints(1, y++));
        this.add(tMiejsceZamieszkania, tMiejsceZamieszkania.getConstraints(1, y++));
        this.add(tWyksztalcenie, tWyksztalcenie.getConstraints(1, y++));
        this.add(tZawodyWykonywane, tZawodyWykonywane.getConstraints(1, y++));
        this.add(tRodzice, tRodzice.getConstraints(1, y++));
        this.add(tOpis, tOpis.getConstraints(1, y++));
        this.add(tRodzina, tRodzina.getConstraints(1, y++));
        this.add(tDokumenty, tDokumenty.getConstraints(1, y++));

        tPlec.removeAllItems();
        tPlec.addItem("<Wybierz>");
        tPlec.addItem("Mê¿czyzna");
        tPlec.addItem("Kobieta");
    }

    public void wczytajDane(Long idOsoby) {
        odswiezListy();

        if (idOsoby == null) {
            wyczyscPola();
            return;
        }
        daneOsoby = DatabaseDelegate.getPersonEditData(idOsoby);

        if (daneOsoby.getPlec() == null) {
            tPlec.setSelectedIndex(0);
        } else if (daneOsoby.getPlec().equalsIgnoreCase("mezczyzna")) {
            tPlec.setSelectedIndex(1);
        } else if (daneOsoby.getPlec().equalsIgnoreCase("kobieta")) {
            tPlec.setSelectedIndex(2);
        } else {
            tPlec.setSelectedIndex(0);
        }
        tImiona.setText(daneOsoby.getImiona());
        tNazwisko.setText(daneOsoby.getNazwisko());
        tDataUrodzenia.setText(daneOsoby.getDataUrodzenia());
        tMiejsceUrodzenia.setText(daneOsoby.getMiejsceUrodzenia());
        tDataSmierci.setText(daneOsoby.getDataSmierci());
        tMiejsceSmierci.setText(daneOsoby.getMiejsceSmierci());
        tDataPochowania.setText(daneOsoby.getDataPochowania());
        tMiejscePochowania.setText(daneOsoby.getMiejscePochowania());
        tWyksztalcenie.setText(daneOsoby.getWyksztalcenie());
        tZawodyWykonywane.setText(daneOsoby.getZawodyWykonywane());
        tMiejsceZamieszkania.setText(daneOsoby.getMiejsceZamieszkania());
        tOpis.setText(daneOsoby.getOpis());
        if (daneOsoby.getRodzice() != null) {
            tRodzice.setSelectedItem(daneOsoby.getRodzice());
        } else {
            tRodzice.setSelectedIndex(0);
        }

        List<RodzinaStruct> rodziny = daneOsoby.getRodziny();
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
        List<ZwiazekStruct> zwiazki = DatabaseDelegate.getRelationships();
        List<ReferenceListElement> wszystkieDokumenty = DatabaseDelegate.getDocuments();
        tRodzice.removeAllItems();
        tRodzice.addItem(new String("<Brak>"));
        for (ZwiazekStruct zwiazek : zwiazki) {
            tRodzice.addItem(zwiazek);
        }

        tDokumenty.setAllItems(wszystkieDokumenty);
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE)
            daneOsoby = new DaneEdycjiOsoby();

        if (tPlec.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wybierz p³eæ", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tPlec.getSelectedIndex() == 1) {
            daneOsoby.setPlec("mezczyzna");
        } else {
            daneOsoby.setPlec("kobieta");
        }
        daneOsoby.setImiona(tImiona.getText().trim());
        daneOsoby.setNazwisko(tNazwisko.getText().trim());
        daneOsoby.setDataUrodzenia(tDataUrodzenia.getText().trim());
        daneOsoby.setMiejsceUrodzenia(tMiejsceUrodzenia.getText().trim());
        daneOsoby.setDataSmierci(tDataSmierci.getText().trim());
        daneOsoby.setMiejsceSmierci(tMiejsceSmierci.getText().trim());
        daneOsoby.setDataPochowania(tDataPochowania.getText().trim());
        daneOsoby.setMiejscePochowania(tMiejscePochowania.getText().trim());
        daneOsoby.setWyksztalcenie(tWyksztalcenie.getText().trim());
        daneOsoby.setZawodyWykonywane(tZawodyWykonywane.getText().trim());
        daneOsoby.setMiejsceZamieszkania(tMiejsceZamieszkania.getText().trim());
        daneOsoby.setOpis(tOpis.getText().trim());
        if (tRodzice.getSelectedIndex() == 0) {
            daneOsoby.setRodzice(null);
        } else {
            daneOsoby.setRodzice((ZwiazekStruct) tRodzice.getSelectedItem());
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
        tImiona.setText("");
        tNazwisko.setText("");
        tDataUrodzenia.setText("");
        tMiejsceUrodzenia.setText("");
        tDataSmierci.setText("");
        tMiejsceSmierci.setText("");
        tDataPochowania.setText("");
        tMiejscePochowania.setText("");
        tMiejsceZamieszkania.setText("");
        tWyksztalcenie.setText("");
        tZawodyWykonywane.setText("");
        tOpis.setText("");
        tRodzice.setSelectedIndex(0);
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
        DaneEdycjiOsoby daneOsoby = DatabaseDelegate.getPersonEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.idKorzenia = daneOsoby.getId();
        diagramInfo.nazwa = daneOsoby.getImiona() + " " + daneOsoby.getNazwisko() + " (" + daneOsoby.getId() + ")";
        diagramInfo.opis = "Diagram osoby '" + daneOsoby.getImiona() + " " + daneOsoby.getNazwisko() + "'";
        return diagramInfo;
    }
}