package sebcel.genealogia.gui.component;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

public class Drzewo extends JScrollPane {
	
	public final static long serialVersionUID = 0l;
	private JTree dane = new JTree();
	
	public Drzewo() {
		super();
		this.setMinimumSize(new Dimension(200,50));
		this.setPreferredSize(new Dimension(400,240));
		this.setViewportView(dane);
		dane.setRootVisible(false);
	}
	
	public void setModel(TreeModel treeModel) {
		dane.setModel(treeModel);
		for (int i=0; i<dane.getRowCount(); i++) {
			dane.expandRow(i);
		}
	}
	public GridBagConstraints getConstraints(int x, int y) {
		return new GridBagConstraints(x,y,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(1,1,1,1),1,1);
	}
}