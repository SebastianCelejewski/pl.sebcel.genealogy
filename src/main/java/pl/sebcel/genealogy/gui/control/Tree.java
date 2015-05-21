package pl.sebcel.genealogy.gui.control;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

public class Tree extends JScrollPane {

    public final static long serialVersionUID = 0l;
    private JTree data = new JTree();

    public Tree() {
        super();
        this.setMinimumSize(new Dimension(200, 50));
        this.setPreferredSize(new Dimension(400, 240));
        this.setViewportView(data);
        data.setRootVisible(false);
    }

    public void setModel(TreeModel treeModel) {
        data.setModel(treeModel);
        for (int i = 0; i < data.getRowCount(); i++) {
            data.expandRow(i);
        }
    }

    public GridBagConstraints getConstraints(int x, int y) {
        return new GridBagConstraints(x, y, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1);
    }
}