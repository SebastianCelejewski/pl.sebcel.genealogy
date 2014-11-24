package sebcel.genealogia.gui.lista;

import java.util.List;

import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.ElementListyOsobStruct;

public class ListaOsobKomponent extends ListaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaOsobTableColumnModel tableColumnModel = new ListaOsobTableColumnModel();
	
	public ListaOsobKomponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		List<ElementListyOsobStruct> ludzie = DatabaseDelegate.getListaOsob();
		tableColumnModel.wpiszDane(ludzie);
		table.invalidate();
	}
	
	public Long getSelectedId() {
		int row = table.getSelectedRow(); 
		return (Long) tableColumnModel.getValueAt(row, 0);
	}
	
	public DataExporter getExporter(String format) {
		if (format.equals("xml")) {
			return new DefaultExporter(DatabaseDelegate.getXMLListaOsob());
		}
		if (format.equals("txt")) {
			return new DefaultExporter(DatabaseDelegate.getTxtListaOsob());
		}
		throw new IllegalArgumentException("Illegal export format: "+format+".");
	}
	
}