package sebcel.genealogia.gui.lista;

import java.util.List;

import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.ElementListyZwiazkowStruct;

public class ListaZwiazkowKomponent extends ListaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private ListaZwiazkowTableColumnModel tableColumnModel = new ListaZwiazkowTableColumnModel();
	
	public ListaZwiazkowKomponent() {
		setComplexModel(tableColumnModel);
	}
	
	public void refresh() {
		System.out.println("[ListaZwiazkowKomponent][refresh]");
		List<ElementListyZwiazkowStruct> zwiazki = DatabaseDelegate.getListaZwiazkow();
		tableColumnModel.wpiszDane(zwiazki);
		table.invalidate();
		this.repaint();
	}
	
	public Long getSelectedId() {
		int row = table.getSelectedRow(); 
		return (Long) tableColumnModel.getValueAt(row, 0);
	}
	
	public DataExporter getExporter(String format) {
		throw new RuntimeException("Eksport zwi�zk�w nie jest jeszcze zaimplementowany.");
	}
	
}