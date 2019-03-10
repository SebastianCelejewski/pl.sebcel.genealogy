package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.list.PeopleListElement;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.export.DefaultExporter;
import pl.sebcel.genealogy.gui.component.list.model.PeopleListTableColumnModel;

public class PeopleListComponent extends AbstractListComponent {
	
	public final static long serialVersionUID = 0l;
	
	private PeopleListTableColumnModel tableColumnModel = new PeopleListTableColumnModel();
	
	public PeopleListComponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		List<PeopleListElement> people = DatabaseDelegate.getPeopleList();
		tableColumnModel.setData(people);
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
		if (format.equals("csv")) {
			return new DefaultExporter(DatabaseDelegate.getPeopleListCSV());
		}
		throw new IllegalArgumentException("Illegal export format: "+format+".");
	}
	
}