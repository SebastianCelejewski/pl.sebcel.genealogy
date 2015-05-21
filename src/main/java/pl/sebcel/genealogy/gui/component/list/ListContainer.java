package pl.sebcel.genealogy.gui.component.list;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.export.DataExporter;
import pl.sebcel.genealogy.gui.component.EditConainer;
import pl.sebcel.genealogy.gui.component.AbstractEditComponent.EditMode;
import pl.sebcel.genealogy.gui.component.list.model.CustomFileFilter;
import pl.sebcel.genealogy.gui.control.UpdateListener;
import pl.sebcel.genealogy.gui.pedigree.PedigreeChartFrame;

public class ListContainer extends JPanel implements UpdateListener, ActionListener {
	
	public final static long serialVersionUID = 0l;
	
	private AbstractListComponent listComponent;
	private EditConainer editContainer;
	private JPanel buttonsPanel = new JPanel();
	
	private JButton buttonCreateNew = new JButton("Nowy");
	private JButton buttonEdit = new JButton("Edycja");
	private JButton buttonDelete = new JButton("Usuñ");
	private JButton buttonTree = new JButton("Drzewo");
	private JButton buttonExport = new JButton("Eksportuj");
	
	public ListContainer(AbstractListComponent listComponent, EditConainer editComponent) {
		this.listComponent = listComponent;
		this.editContainer = editComponent;
		
		this.setLayout(new BorderLayout());
		this.add(listComponent, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		buttonsPanel.add(buttonCreateNew);
		buttonsPanel.add(buttonEdit);
		buttonsPanel.add(buttonDelete);
		buttonsPanel.add(buttonTree);
		buttonsPanel.add(buttonExport);
		
		buttonCreateNew.addActionListener(this);
		buttonEdit.addActionListener(this);
		buttonDelete.addActionListener(this);
		buttonTree.addActionListener(this);
		buttonExport.addActionListener(this);
		
		editComponent.setUpdateListener(this);
	}
	
	public void update() {
		listComponent.refresh();
		this.repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(buttonCreateNew)) createNew();
		if (e.getSource().equals(buttonEdit)) edit(listComponent.getSelectedId());
		if (e.getSource().equals(buttonDelete)) delete(listComponent.getSelectedId());
		if (e.getSource().equals(buttonTree)) draw(listComponent.getSelectedId());
		if (e.getSource().equals(buttonExport)) export(listComponent);
	}
	
	private void createNew() {
		editContainer.setEditMode(EditMode.CREATE_NEW);
		editContainer.loadData(null);
		editContainer.setModal(true);
		editContainer.setVisible(true);
	}
	
	private void edit(Long id) {
		editContainer.setEditMode(EditMode.EDIT_EXISTING);
		editContainer.loadData(id);
		editContainer.setModal(true);
		editContainer.setVisible(true);
	}
	
	private void delete(Long id) {
		editContainer.deleteObject(id);
	}
	
	private void draw(Long id) {
		DiagramInfoStruct diagramInfo = editContainer.getDiagramInfo(id);
		new PedigreeChartFrame().drawPedigreeChart(diagramInfo);
	}
	
	private void export(AbstractListComponent listComponent) {
		JFileChooser fileChooser = new JFileChooser();
		CustomFileFilter fileFilter = new CustomFileFilter("Export file formats", "txt", "xml");
		fileChooser.setFileFilter(fileFilter);
		
		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();
				DataExporter dataExporter = listComponent.getExporter(CustomFileFilter.getExtension(fileChooser.getSelectedFile()));
				dataExporter.exportData(file);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Wyst¹pi³ b³¹d:\n"+ex.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}