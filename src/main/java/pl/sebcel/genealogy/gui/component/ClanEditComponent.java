package pl.sebcel.genealogy.gui.component;

import java.util.List;

import javax.swing.JOptionPane;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.edit.ClanEditData;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Label;
import pl.sebcel.genealogy.gui.control.TextField;
import pl.sebcel.genealogy.gui.control.SingleValueReference;

public class ClanEditComponent extends AbstractEditComponent {

    public final static long serialVersionUID = 0l;

    private ClanEditData daneKlanu;

    private Label lNazwa = new Label("Nazwa:");
    private Label lDescription = new Label("Description:");
    private Label lKorzen = new Label("Protoplasta:");

    private TextField tNazwa = new TextField();
    private TextField tDescription = new TextField();
    private SingleValueReference tKorzen = new SingleValueReference();

    public ClanEditComponent() {

        int y = 0;
        this.add(lNazwa, lNazwa.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));
        this.add(lKorzen, lKorzen.getConstraints(0, y++));

        y = 0;
        this.add(tNazwa, tNazwa.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));
        this.add(tKorzen, tKorzen.getConstraints(1, y++));

    }

    public void wczytajDane(Long id) {
        odswiezListy();

        if (id == null) {
            wyczyscPola();
            return;
        }
        daneKlanu = DatabaseDelegate.getClanEditData(id);

        tNazwa.setText(daneKlanu.getName());
        tDescription.setText(daneKlanu.getDescription());
        tKorzen.setSelectedItem(daneKlanu.getRoot());

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
            daneKlanu = new ClanEditData();

        if (tKorzen.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wska� protoplast� klanu", "B��d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        daneKlanu.setName(tNazwa.getText().trim());
        daneKlanu.setDescription(tDescription.getText().trim());
        daneKlanu.setRoot((ReferenceListElement) tKorzen.getSelectedItem());

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.saveEditedClan(daneKlanu);
        } else {
            DatabaseDelegate.saveNewClan(daneKlanu);
        }

        return true;
    }

    private void wyczyscPola() {
        tNazwa.setText("");
        tDescription.setText("");
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
        ClanEditData daneKlanu = DatabaseDelegate.getClanEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.idKorzenia = daneKlanu.getRoot().getId();
        diagramInfo.nazwa = daneKlanu.getName();
        diagramInfo.description = "Diagram klanu '" + daneKlanu.getName() + "'";
        return diagramInfo;
    }
}