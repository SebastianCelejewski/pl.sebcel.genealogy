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

    private DocumentEditData documentData;

    private Label lTitle = new Label("Tytuł:");
    private Label lSymbol = new Label("Symbol:");
    private Label lDescription = new Label("Description:");
    private Label lRelatedPeople = new Label("Osoby: ");

    private TextField tTitle = new TextField();
    private TextField tSymbol = new TextField();
    private TextArea tDescription = new TextArea();
    private MultiValueReference tRelatedPeople = new MultiValueReference();

    public DocumentEditComponent() {

        int y = 0;
        this.add(lTitle, lTitle.getConstraints(0, y++));
        this.add(lSymbol, lSymbol.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));
        this.add(lRelatedPeople, lRelatedPeople.getConstraints(0, y++));

        y = 0;
        this.add(tTitle, tTitle.getConstraints(1, y++));
        this.add(tSymbol, tSymbol.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));
        this.add(tRelatedPeople, tRelatedPeople.getConstraints(1, y++));
    }

    public void loadData(Long id) {
        refreshLists();

        if (id == null) {
            clearAllFields();
            return;
        }
        documentData = DatabaseDelegate.getDocumentEditData(id);

        tTitle.setText(documentData.getTitle());
        tSymbol.setText(documentData.getSymbol());
        tDescription.setText(documentData.getDescription());
        tRelatedPeople.setSelectedItems(new ArrayList<ReferenceListElement>(documentData.getRelatedPeople()));
    }

    private void refreshLists() {
        List<ReferenceListElement> allPeople = DatabaseDelegate.getPeople();
        tRelatedPeople.setAllItems(allPeople);
    }

    public boolean saveData() {
        if (editMode == EditMode.CREATE_NEW) {
            documentData = new DocumentEditData();
        }

        documentData.setTitle(tTitle.getText().trim());
        documentData.setSymbol(tSymbol.getText().trim());
        documentData.setDescription(tDescription.getText().trim());
        documentData.setRelatedPeople(new HashSet<ReferenceListElement>(tRelatedPeople.getSelectedItems()));

        if (editMode == EditMode.EDIT_EXISTING) {
            DatabaseDelegate.saveEditedDocument(documentData);
        } else {
            DatabaseDelegate.saveNewDocument(documentData);
        }

        return true;
    }

    private void clearAllFields() {
        tTitle.setText("");
        tSymbol.setText("");
        tDescription.setText("");
        tRelatedPeople.setSelectedItems(new ArrayList<ReferenceListElement>());
    }

    @Override
    public void deleteElement(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usunąć ten dokument?", "Usuwanie dokumentu", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deleteDocument(id);
        }
    }

    @Override
    public String getTitle() {
        if (editMode == EditMode.CREATE_NEW) {
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