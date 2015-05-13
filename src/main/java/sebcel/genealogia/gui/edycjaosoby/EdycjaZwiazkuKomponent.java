package sebcel.genealogia.gui.edycjaosoby;

import java.util.List;

import javax.swing.JOptionPane;

import sebcel.genealogia.gui.component.Etykieta;
import sebcel.genealogia.gui.component.ObszarTekstowy;
import sebcel.genealogia.gui.component.PoleTekstowe;
import sebcel.genealogia.gui.component.SingleValueReferenceField;
import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.DaneEdycjiZwiazkuStruct;
import sebcel.genealogia.struct.DiagramInfoStruct;
import sebcel.genealogia.struct.ReferenceListElement;

public class EdycjaZwiazkuKomponent extends EdycjaKomponent {

    public final static long serialVersionUID = 0l;

    private DaneEdycjiZwiazkuStruct daneZwiazku;

    private Etykieta lMezczyzna = new Etykieta("Mê¿czyzna:");
    private Etykieta lKobieta = new Etykieta("Kobieta:");
    private Etykieta lDataPoznania = new Etykieta("Data poznania:");
    private Etykieta lMiejscePoznania = new Etykieta("Miejsce poznania:");
    private Etykieta lDataSlubu = new Etykieta("Data œlubu:");
    private Etykieta lMiejsceSlubu = new Etykieta("Miejsce œlubu:");
    private Etykieta lDataRozstania = new Etykieta("Data rozstania:");
    private Etykieta lMiejsceRozstania = new Etykieta("Miejsce rozstania:");
    private Etykieta lDataRozwodu = new Etykieta("Data rozwodu:");
    private Etykieta lMiejsceRozwodu = new Etykieta("Miejsce rozwodu:");
    private Etykieta lOpis = new Etykieta("Opis");

    private SingleValueReferenceField tMezczyzna = new SingleValueReferenceField();
    private SingleValueReferenceField tKobieta = new SingleValueReferenceField();
    private PoleTekstowe tDataPoznania = new PoleTekstowe();
    private PoleTekstowe tMiejscePoznania = new PoleTekstowe();
    private PoleTekstowe tDataSlubu = new PoleTekstowe();
    private PoleTekstowe tMiejsceSlubu = new PoleTekstowe();
    private PoleTekstowe tDataRozstania = new PoleTekstowe();
    private PoleTekstowe tMiejsceRozstania = new PoleTekstowe();
    private PoleTekstowe tDataRozwodu = new PoleTekstowe();
    private PoleTekstowe tMiejsceRozwodu = new PoleTekstowe();
    private ObszarTekstowy tOpis = new ObszarTekstowy();

    public EdycjaZwiazkuKomponent() {
        int y = 0;
        this.add(lMezczyzna, lMezczyzna.getConstraints(0, y++));
        this.add(lKobieta, lKobieta.getConstraints(0, y++));
        this.add(lDataPoznania, lDataPoznania.getConstraints(0, y++));
        this.add(lMiejscePoznania, lMiejscePoznania.getConstraints(0, y++));
        this.add(lDataSlubu, lDataSlubu.getConstraints(0, y++));
        this.add(lMiejsceSlubu, lMiejsceSlubu.getConstraints(0, y++));
        this.add(lDataRozstania, lDataRozstania.getConstraints(0, y++));
        this.add(lMiejsceRozstania, lMiejsceRozstania.getConstraints(0, y++));
        this.add(lDataRozwodu, lDataRozwodu.getConstraints(0, y++));
        this.add(lMiejsceRozwodu, lMiejsceRozwodu.getConstraints(0, y++));
        this.add(lOpis, lOpis.getConstraints(0, y++));

        y = 0;
        this.add(tMezczyzna, tMezczyzna.getConstraints(1, y++));
        this.add(tKobieta, tKobieta.getConstraints(1, y++));
        this.add(tDataPoznania, tDataPoznania.getConstraints(1, y++));
        this.add(tMiejscePoznania, tMiejscePoznania.getConstraints(1, y++));
        this.add(tDataSlubu, tDataSlubu.getConstraints(1, y++));
        this.add(tMiejsceSlubu, tMiejsceSlubu.getConstraints(1, y++));
        this.add(tDataRozstania, tDataRozstania.getConstraints(1, y++));
        this.add(tMiejsceRozstania, tMiejsceRozstania.getConstraints(1, y++));
        this.add(tDataRozwodu, tDataRozwodu.getConstraints(1, y++));
        this.add(tMiejsceRozwodu, tMiejsceRozwodu.getConstraints(1, y++));
        this.add(tOpis, tOpis.getConstraints(1, y++));

    }

    public void wczytajDane(Long idZwiazku) {
        odswiezListy();

        if (idZwiazku == null) {
            wyczyscPola();
            return;
        }
        daneZwiazku = DatabaseDelegate.getDaneDoEdycjiZwiazku(idZwiazku);

        tMezczyzna.setSelectedItem(daneZwiazku.mezczyzna);
        tKobieta.setSelectedItem(daneZwiazku.kobieta);
        tDataPoznania.setText(daneZwiazku.dataPoznania);
        tMiejscePoznania.setText(daneZwiazku.miejscePoznania);
        tDataSlubu.setText(daneZwiazku.dataSlubu);
        tMiejsceSlubu.setText(daneZwiazku.miejsceSlubu);
        tDataRozstania.setText(daneZwiazku.dataRozstania);
        tMiejsceRozstania.setText(daneZwiazku.miejsceRozstania);
        tDataRozwodu.setText(daneZwiazku.dataRozwodu);
        tMiejsceRozwodu.setText(daneZwiazku.miejsceRozwodu);
        tOpis.setText(daneZwiazku.opis);
    }

    private void odswiezListy() {
        List<ReferenceListElement> mezczyzni = DatabaseDelegate.getMezczyzni();
        List<ReferenceListElement> kobiety = DatabaseDelegate.getKobiety();

        tMezczyzna.removeAllItems();
        tKobieta.removeAllItems();

        tMezczyzna.addItem("<Wybierz>");
        tKobieta.addItem("<Wybierz>");

        for (ReferenceListElement osoba : mezczyzni) {
            tMezczyzna.addItem(osoba);
        }
        for (ReferenceListElement osoba : kobiety) {
            tKobieta.addItem(osoba);
        }
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE)
            daneZwiazku = new DaneEdycjiZwiazkuStruct();

        if (tMezczyzna.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Musisz wybraæ mê¿czyznê", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (tKobieta.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Musisz wybraæ kobietê", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        daneZwiazku.mezczyzna = (ReferenceListElement) tMezczyzna.getSelectedItem();
        daneZwiazku.kobieta = (ReferenceListElement) tKobieta.getSelectedItem();
        daneZwiazku.dataPoznania = tDataPoznania.getText().trim();
        daneZwiazku.miejscePoznania = tMiejscePoznania.getText().trim();
        daneZwiazku.dataSlubu = tDataSlubu.getText().trim();
        daneZwiazku.miejsceSlubu = tMiejsceSlubu.getText().trim();
        daneZwiazku.dataRozstania = tDataRozstania.getText().trim();
        daneZwiazku.miejsceRozstania = tMiejsceRozstania.getText().trim();
        daneZwiazku.dataRozwodu = tDataRozwodu.getText().trim();
        daneZwiazku.miejsceRozwodu = tMiejsceRozwodu.getText().trim();
        daneZwiazku.opis = tOpis.getText().trim();

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.zapiszDanePoEdycjiZwiazku(daneZwiazku);
        } else {
            DatabaseDelegate.zapiszNowyZwiazek(daneZwiazku);
        }

        return true;
    }

    private void wyczyscPola() {
        tMezczyzna.setSelectedItem(0);
        tKobieta.setSelectedItem(0);
        tDataPoznania.setText("");
        tMiejscePoznania.setText("");
        tDataSlubu.setText("");
        tMiejsceSlubu.setText("");
        tDataRozstania.setText("");
        tMiejsceRozstania.setText("");
        tDataRozwodu.setText("");
        tMiejsceRozwodu.setText("");
        tOpis.setText("");
    }

    @Override
    public void usunObiekt(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ ten zwi¹zek?", "Usuwanie zwi¹zku", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.usunZwiazek(id);
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
        DaneEdycjiZwiazkuStruct daneZwiazku = DatabaseDelegate.getDaneDoEdycjiZwiazku(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();

        diagramInfo.idKorzenia = daneZwiazku.mezczyzna.getId();
        diagramInfo.nazwa = daneZwiazku.mezczyzna.getDescription() + " i " + daneZwiazku.kobieta.getDescription();
        diagramInfo.opis = "Diagram zwi¹zku " + daneZwiazku.mezczyzna.getDescription() + " " + daneZwiazku.kobieta.getDescription();

        return diagramInfo;
    }
}