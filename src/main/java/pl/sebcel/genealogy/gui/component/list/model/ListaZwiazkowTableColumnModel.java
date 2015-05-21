package pl.sebcel.genealogy.gui.component.list.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import pl.sebcel.genealogy.dto.list.RelationshipsListElement;

public class ListaZwiazkowTableColumnModel extends DefaultTableColumnModel implements ComplexTableModel {
	
	public static final long serialVersionUID = 0L;
	
	private List<RelationshipsListElement> dane = new ArrayList<RelationshipsListElement>();
	private String[] kolumny = new String[] {"Id","Mê¿czyzna","Female","Description"};

	public ListaZwiazkowTableColumnModel() {
		super();
		TableColumn columnId = new TableColumn(0,20);
		TableColumn columnMale = new TableColumn(1,200); 
		TableColumn columnFemale = new TableColumn(2,200); 
		TableColumn columnDescription = new TableColumn(3,220);
		
		columnId.setHeaderValue(kolumny[0]);
		columnMale.setHeaderValue(kolumny[1]);
		columnFemale.setHeaderValue(kolumny[2]);
		columnDescription.setHeaderValue(kolumny[3]);
		
		addColumn(columnId);
		addColumn(columnMale);
		addColumn(columnFemale);
		addColumn(columnDescription);
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
		if (columnIndex==0) return dane.get(rowIndex).getId();
		if (columnIndex==1) return dane.get(rowIndex).getMale();
		if (columnIndex==2) return dane.get(rowIndex).getFemale();
		if (columnIndex==3) return dane.get(rowIndex).getDescription();
		return "";
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	public void wpiszDane(List<RelationshipsListElement> listaZwiazkow) {
		this.dane = listaZwiazkow;
	}
}