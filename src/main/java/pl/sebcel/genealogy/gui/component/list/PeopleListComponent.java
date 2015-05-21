package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.list.PeopleListElement;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.export.DefaultExporter;
import pl.sebcel.genealogy.gui.component.list.model.ListaOsobTableColumnModel;

public class PeopleListComponent extends AbstractListComponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaOsobTableColumnModel tableColumnModel = new ListaOsobTableColumnModel();
	
	public PeopleListComponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		List<PeopleListElement> ludzie = DatabaseDelegate.getPeopleList();
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