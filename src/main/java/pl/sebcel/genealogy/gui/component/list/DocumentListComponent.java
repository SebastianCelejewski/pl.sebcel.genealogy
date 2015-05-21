package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.list.DocumentListElement;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.export.DefaultExporter;
import pl.sebcel.genealogy.gui.component.list.model.ListaDokumentowTableColumnModel;

public class DocumentListComponent extends AbstractListComponent {

    public final static long serialVersionUID = 0l;

    private ListaDokumentowTableColumnModel tableColumnModel = new ListaDokumentowTableColumnModel();

    public DocumentListComponent() {
        setComplexModel(tableColumnModel);
    }

    public void refresh() {
        List<DocumentListElement> dokumenty = DatabaseDelegate.getDocumentsList();
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