package pl.sebcel.genealogy.gui.control;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComboBox;

public class SingleValueReference extends JComboBox {
	
	public final static long serialVersionUID = 0l;
	
	public SingleValueReference() {
		super();
		this.setMinimumSize(new Dimension(200,21));
		this.setPreferredSize(new Dimension(400,21));
	}
	
	public GridBagConstraints getConstraints(int x, int y) {
		return new GridBagConstraints(x,y,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(1,1,1,1),1,1);
	}
}