package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.list.RelationshipsListElement;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.gui.component.list.model.ListaZwiazkowTableColumnModel;

public class RelationshipListComponent extends AbstractListComponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaZwiazkowTableColumnModel tableColumnModel = new ListaZwiazkowTableColumnModel();
	
	public RelationshipListComponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		System.out.println("[ListaZwiazkowKomponent][refresh]");
		List<RelationshipsListElement> zwiazki = DatabaseDelegate.getRelationshipsList();
		tableColumnModel.wpiszDane(zwiazki);
		table.invalidate();
		this.repaint();
	}
	
	public Long getSelectedId() {
		int row = table.getSelectedRow(); 
		return (Long) tableColumnModel.getValueAt(row, 0);
	}
	
	public DataExporter getExporter(String format) {
		throw new RuntimeException("Eksport zwi¹zków nie jest jeszcze zaimplementowany.");
	}
	
}