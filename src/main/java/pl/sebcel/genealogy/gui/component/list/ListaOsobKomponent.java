package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.ElementListyOsobStruct;

public class ListaOsobKomponent extends ListaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaOsobTableColumnModel tableColumnModel = new ListaOsobTableColumnModel();
	
	public ListaOsobKomponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		List<ElementListyOsobStruct> ludzie = DatabaseDelegate.getPeopleList();
		tableColumnModel.wpiszDane(ludzie);
		table.invalidate();
	}
	
	public Long getSelectedId() {
		int row = table.getSelectedRow(); 
		return (Long) tableColumnModel.getValueAt(row, 0);
	}
	
	public DataExporter getExporter(String format) {
		if (format.equals("xml")) {
			return new DefaultExporter(DatabaseDelegate.getPeopleListXML());
		}
		if (format.equals("txt")) {
			return new DefaultExporter(DatabaseDelegate.getPeopleListTXT());
		}
		throw new IllegalArgumentException("Illegal export format: "+format+".");
	}
	
}