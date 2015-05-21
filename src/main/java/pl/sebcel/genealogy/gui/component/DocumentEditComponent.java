package pl.sebcel.genealogy.gui.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.edit.DocumentEditData;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Label;
import pl.sebcel.genealogy.gui.control.MultiValueReference;
import pl.sebcel.genealogy.gui.control.TextArea;
import pl.sebcel.genealogy.gui.control.TextField;

public class DocumentEditComponent extends AbstractEditComponent {

    public final static long serialVersionUID = 0l;

    private DocumentEditData daneDokumentu;

    private Label lTytul = new Label("Tytu³:");
    private Label lSymbol = new Label("Symbol:");
    private Label lDescription = new Label("Description:");
    private Label lOsoby = new Label("Osoby: ");

    private TextField tTytul = new TextField();
    private TextField tSymbol = new TextField();
    private TextArea tDescription = new TextArea();
    private MultiValueReference tOsoby = new MultiValueReference();

    public DocumentEditComponent() {

        int y = 0;
        this.add(lTytul, lTytul.getConstraints(0, y++));
        this.add(lSymbol, lSymbol.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));
        this.add(lOsoby, lOsoby.getConstraints(0, y++));

        y = 0;
        this.add(tTytul, tTytul.getConstraints(1, y++));
        this.add(tSymbol, tSymbol.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));
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
        tDescription.setText(daneDokumentu.getDescription());
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
        daneDokumentu.setDescription(tDescription.getText().trim());
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
        tDescription.setText("");
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