package pl.sebcel.genealogy.gui.component.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import pl.sebcel.genealogy.dto.ElementListyZwiazkowStruct;

class ListaZwiazkowTableColumnModel extends DefaultTableColumnModel implements ComplexTableModel {
	
	public static final long serialVersionUID = 0L;
	
	private List<ElementListyZwiazkowStruct> dane = new ArrayList<ElementListyZwiazkowStruct>();
	private String[] kolumny = new String[] {"Id","Mê¿czyzna","Kobieta","Opis"};

	public ListaZwiazkowTableColumnModel() {
		super();
		TableColumn columnId = new TableColumn(0,20);
		TableColumn columnMezczyzna = new TableColumn(1,200); 
		TableColumn columnKobieta = new TableColumn(2,200); 
		TableColumn columnOpis = new TableColumn(3,220);
		
		columnId.setHeaderValue(kolumny[0]);
		columnMezczyzna.setHeaderValue(kolumny[1]);
		columnKobieta.setHeaderValue(kolumny[2]);
		columnOpis.setHeaderValue(kolumny[3]);
		
		addColumn(columnId);
		addColumn(columnMezczyzna);
		addColumn(columnKobieta);
		addColumn(columnOpis);
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
		if (columnIndex==0) return dane.get(rowIndex).id;
		if (columnIndex==1) return dane.get(rowIndex).mezczyzna;
		if (columnIndex==2) return dane.get(rowIndex).kobieta;
		if (columnIndex==3) return dane.get(rowIndex).opis;
		return "";
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	public void wpiszDane(List<ElementListyZwiazkowStruct> listaZwiazkow) {
		this.dane = listaZwiazkow;
	}
}