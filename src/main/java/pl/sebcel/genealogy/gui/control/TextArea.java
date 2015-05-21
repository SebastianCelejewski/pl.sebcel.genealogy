package pl.sebcel.genealogy.gui.control;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextArea extends JScrollPane {

    public final static long serialVersionUID = 0l;

    private JTextArea textArea;

    public TextArea() {
        super();
        this.textArea = new JTextArea(80, 25);
        this.setMinimumSize(new Dimension(200, 50));
        this.setPreferredSize(new Dimension(400, 150));
        this.setViewportView(textArea);
    }

    public GridBagConstraints getConstraints(int x, int y) {
        return new GridBagConstraints(x, y, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public String getText() {
        return textArea.getText();
    }
}