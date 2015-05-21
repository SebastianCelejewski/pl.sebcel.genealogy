package pl.sebcel.genealogy.gui.component.list;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.gui.component.list.model.ComplexTableModel;

public abstract class AbstractListComponent extends JComponent {

	private static final long serialVersionUID = -3419790563496267315L;

	private JScrollPane scrollPane = new JScrollPane();

	protected JTable table = new JTable();

	public AbstractListComponent() {
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
	}
	
	protected void setComplexModel(ComplexTableModel complexTableModel) {
		table.setModel(complexTableModel);
		table.setColumnModel(complexTableModel);
		refresh();
	}

	public abstract Long getSelectedId();

	public abstract void refresh();

	public abstract DataExporter getExporter(String format);
}
