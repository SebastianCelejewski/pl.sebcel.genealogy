package pl.sebcel.genealogy.gui.control;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

public class Label extends JLabel {
	
	public final static long serialVersionUID = 0l;
	
	public Label(String nazwa) {
		super(nazwa);
		this.setPreferredSize(new Dimension(120,21));
		this.setHorizontalAlignment(JLabel.RIGHT);
	}
	
	public GridBagConstraints getConstraints(int x, int y) {
		return new GridBagConstraints(x,y,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(1,1,1,1),1,1);
	}
}