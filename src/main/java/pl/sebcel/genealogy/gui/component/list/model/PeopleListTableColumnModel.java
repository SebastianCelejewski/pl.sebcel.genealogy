package pl.sebcel.genealogy.gui.component.list.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import pl.sebcel.genealogy.dto.list.PeopleListElement;

public class PeopleListTableColumnModel extends DefaultTableColumnModel implements ComplexTableModel {

    public static final long serialVersionUID = 0L;

    private List<PeopleListElement> data = new ArrayList<PeopleListElement>();
    private String[] columns = new String[] { "Id", "Imiona i nazwisko", "Opis" };

    public PeopleListTableColumnModel() {
        super();
        TableColumn columnId = new TableColumn(0, 20);
        TableColumn columnName = new TableColumn(1, 280);
        TableColumn columnDescription = new TableColumn(2, 400);

        columnId.setHeaderValue(columns[0]);
        columnName.setHeaderValue(columns[1]);
        columnDescription.setHeaderValue(columns[2]);

        addColumn(columnId);
        addColumn(columnName);
        addColumn(columnDescription);
    }

    public void addTableModelListener(TableModelListener l) {
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return data.get(rowIndex).getId();
        }
        if (columnIndex == 1) {
            return data.get(rowIndex).getName();
        }
        if (columnIndex == 2) {
            return data.get(rowIndex).getDescription();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener l) {
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void setData(List<PeopleListElement> peopleList) {
        this.data = peopleList;
    }
}