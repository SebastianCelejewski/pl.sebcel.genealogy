package pl.sebcel.genealogy.gui.component.list;

import java.util.List;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.list.ClanListElement;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.gui.component.list.model.ClansListTableColumnModel;

public class ClanListComponent extends AbstractListComponent {

    public final static long serialVersionUID = 0l;

    private ClansListTableColumnModel tableColumnModel = new ClansListTableColumnModel();

    public ClanListComponent() {
        setComplexModel(tableColumnModel);
    }

    public void refresh() {
        List<ClanListElement> clans = DatabaseDelegate.getClansList();
        tableColumnModel.setData(clans);
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