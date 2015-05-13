package sebcel.genealogia.gui.lista;

import java.util.List;

import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.ElementListyDokumentowStruct;

public class ListaDokumentowKomponent extends ListaKomponent {

    public final static long serialVersionUID = 0l;

    private ListaDokumentowTableColumnModel tableColumnModel = new ListaDokumentowTableColumnModel();

    public ListaDokumentowKomponent() {
        setComplexModel(tableColumnModel);
    }

    public void refresh() {
        List<ElementListyDokumentowStruct> dokumenty = DatabaseDelegate.getListaDokumentow();
        tableColumnModel.wpiszDane(dokumenty);
        table.invalidate();
    }

    public Long getSelectedId() {
        int row = table.getSelectedRow();
        return (Long) tableColumnModel.getValueAt(row, 0);
    }

    public DataExporter getExporter(String format) {
        if (format.equals("xml")) {
            return new DefaultExporter(DatabaseDelegate.getXMLListaDokumentow());
        }
        if (format.equals("txt")) {
            return new DefaultExporter(DatabaseDelegate.getTXTListaDokumentow());
        }
        throw new IllegalArgumentException("Illegal export format: " + format + ".");
    }

}