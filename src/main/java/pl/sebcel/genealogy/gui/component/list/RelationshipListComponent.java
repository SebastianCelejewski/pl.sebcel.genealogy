package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.list.RelationshipsListElement;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.gui.component.list.model.RelationshipsListTableColumnModel;

public class RelationshipListComponent extends AbstractListComponent {
	
	public final static long serialVersionUID = 0l;
	
	private RelationshipsListTableColumnModel tableColumnModel = new RelationshipsListTableColumnModel();
	
	public RelationshipListComponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		System.out.println("[ListaZwiazkowKomponent][refresh]");
		List<RelationshipsListElement> relationships = DatabaseDelegate.getRelationshipsList();
		tableColumnModel.wpiszDane(relationships);
		table.invalidate();
		this.repaint();
	}
	
	public Long getSelectedId() {
		int row = table.getSelectedRow(); 
		return (Long) tableColumnModel.getValueAt(row, 0);
	}
	
	public DataExporter getExporter(String format) {
		throw new RuntimeException("Eksport związków nie jest jeszcze zaimplementowany.");
	}
}