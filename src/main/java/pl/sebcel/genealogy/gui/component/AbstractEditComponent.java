package pl.sebcel.genealogy.gui.component;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import pl.sebcel.genealogy.dto.DiagramInfoStruct;

public abstract class AbstractEditComponent extends JPanel {
	
	public final static long serialVersionUID = 0l;
	
	public enum EditMode {CREATE_NEW, EDIT_EXISTING};
	
	protected EditMode editMode = EditMode.EDIT_EXISTING;
	
	public AbstractEditComponent() {
		this.setLayout(new GridBagLayout());
	}
	
	public void setEditMode(EditMode editMode) {
		this.editMode = editMode;
	}
	public EditMode getEditMode() {
		return this.editMode; 
	}

	public abstract void loadData(Long id);
	public abstract boolean saveData();
	public abstract void deleteElement(Long id);
	public abstract String getTitle();
	public abstract DiagramInfoStruct getDiagramInfo(Long id);
}