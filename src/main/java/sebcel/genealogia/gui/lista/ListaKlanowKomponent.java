package sebcel.genealogia.gui.lista;

import java.util.List;

import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.ElementListyKlanowStruct;

public class ListaKlanowKomponent extends ListaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaKlanowTableColumnModel tableColumnModel = new ListaKlanowTableColumnModel();
	
	public ListaKlanowKomponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		List<ElementListyKlanowStruct> klany = DatabaseDelegate.getListaKlanow();
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