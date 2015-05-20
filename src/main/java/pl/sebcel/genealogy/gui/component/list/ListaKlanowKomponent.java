package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.ClanListElement;

public class ListaKlanowKomponent extends ListaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaKlanowTableColumnModel tableColumnModel = new ListaKlanowTableColumnModel();
	
	public ListaKlanowKomponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		List<ClanListElement> klany = DatabaseDelegate.getClansList();
		tableColumnModel.wpiszDane(klany);
		table.invalidate();
	}
	
	public Long getSelectedId() {
		int row = table.getSelectedRow(); 
		return (Long) tableColumnModel.getValueAt(row, 0);
	}
	
	public DataExporter getExporter(String format) {
		throw new RuntimeException("Eksport klanów nie jest zaimplementowany.");
	}

}