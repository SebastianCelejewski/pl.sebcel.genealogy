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

    private ClanEditData clanData;

    private Label lName = new Label("Nazwa:");
    private Label lDescription = new Label("Description:");
    private Label lRoot = new Label("Protoplasta:");

    private TextField tName = new TextField();
    private TextField tDescription = new TextField();
    private SingleValueReference tRoot = new SingleValueReference();

    public ClanEditComponent() {

        int y = 0;
        this.add(lName, lName.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));
        this.add(lRoot, lRoot.getConstraints(0, y++));

        y = 0;
        this.add(tName, tName.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));
        this.add(tRoot, tRoot.getConstraints(1, y++));

    }

    public void loadData(Long id) {
        refreshLists();

        if (id == null) {
            clearAllFields();
            return;
        }
        clanData = DatabaseDelegate.getClanEditData(id);

        tName.setText(clanData.getName());
        tDescription.setText(clanData.getDescription());
        tRoot.setSelectedItem(clanData.getRoot());

    }

    private void refreshLists() {
        List<ReferenceListElement> people = DatabaseDelegate.getPeople();
        tRoot.removeAllItems();
        tRoot.addItem(new String("<Wybierz>"));
        for (ReferenceListElement person : people) {
            tRoot.addItem(person);
        }
    }

    public boolean saveData() {
        if (editMode == EditMode.CREATE_NEW)
            clanData = new ClanEditData();

        if (tRoot.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wska¿ protoplastê klanu", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        clanData.setName(tName.getText().trim());
        clanData.setDescription(tDescription.getText().trim());
        clanData.setRoot((ReferenceListElement) tRoot.getSelectedItem());

        if (editMode == EditMode.EDIT_EXISTING) {
            DatabaseDelegate.saveEditedClan(clanData);
        } else {
            DatabaseDelegate.saveNewClan(clanData);
        }

        return true;
    }

    private void clearAllFields() {
        tName.setText("");
        tDescription.setText("");
        tRoot.setSelectedItem(0);
    }

    @Override
    public void deleteElement(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ ten klan?", "Usuwanie klanu", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deleteClan(id);
        }
    }

    @Override
    public String getTitle() {
        if (editMode == EditMode.CREATE_NEW) {
            return "Dodawanie nowego klanu";
        } else {
            return "Edycja danych klanu";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        ClanEditData clanData = DatabaseDelegate.getClanEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.setRootId(clanData.getRoot().getId());
        diagramInfo.setName(clanData.getName());
        diagramInfo.setDescription("Diagram klanu '" + clanData.getName() + "'");
        return diagramInfo;
    }
}