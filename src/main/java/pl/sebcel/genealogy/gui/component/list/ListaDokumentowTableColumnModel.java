package pl.sebcel.genealogy.gui.component.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import pl.sebcel.genealogy.dto.ElementListyDokumentowStruct;

class ListaDokumentowTableColumnModel extends DefaultTableColumnModel implements ComplexTableModel {

    public static final long serialVersionUID = 0L;

    private List<ElementListyDokumentowStruct> dane = new ArrayList<ElementListyDokumentowStruct>();
    private String[] kolumny = new String[] { "Id", "Tytu³", "Symbol" };

    public ListaDokumentowTableColumnModel() {
        super();
        TableColumn columnId = new TableColumn(0, 20);
        TableColumn columnTytul = new TableColumn(1, 280);
        TableColumn columnSymbol = new TableColumn(2, 400);

        columnId.setHeaderValue(kolumny[0]);
        columnTytul.setHeaderValue(kolumny[1]);
        columnSymbol.setHeaderValue(kolumny[2]);

        addColumn(columnId);
        addColumn(columnTytul);
        addColumn(columnSymbol);
    }

    public void addTableModelListener(TableModelListener l) {
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return kolumny.length;
    }

    public String getColumnName(int columnIndex) {
        return kolumny[columnIndex];
    }

    public int getRowCount() {
        return dane.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return dane.get(rowIndex).getId();
        if (columnIndex == 1)
            return dane.get(rowIndex).getTytul();
        if (columnIndex == 2)
            return dane.get(rowIndex).getSymbol();
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener l) {
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void wpiszDane(List<ElementListyDokumentowStruct> listaDokumentow) {
        this.dane = listaDokumentow;
    }
}