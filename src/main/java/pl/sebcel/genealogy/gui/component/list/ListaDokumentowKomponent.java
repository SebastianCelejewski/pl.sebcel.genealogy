package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.ElementListyDokumentowStruct;

public class ListaDokumentowKomponent extends ListaKomponent {

    public final static long serialVersionUID = 0l;

    private ListaDokumentowTableColumnModel tableColumnModel = new ListaDokumentowTableColumnModel();

    public ListaDokumentowKomponent() {
        setComplexModel(tableColumnModel);
    }

    public void refresh() {
        List<ElementListyDokumentowStruct> dokumenty = DatabaseDelegate.getDocumentsList();
        tableColumnModel.wpiszDane(dokumenty);
        table.invalidate();
    }

    public Long getSelectedId() {
        int row = table.getSelectedRow();
        return (Long) tableColumnModel.getValueAt(row, 0);
    }

    public DataExporter getExporter(String format) {
        if (format.equals("xml")) {
            return new DefaultExporter(DatabaseDelegate.getDocumentListXML());
        }
        if (format.equals("txt")) {
            return new DefaultExporter(DatabaseDelegate.getDocumentListTXT());
        }
        throw new IllegalArgumentException("Illegal export format: " + format + ".");
    }

}