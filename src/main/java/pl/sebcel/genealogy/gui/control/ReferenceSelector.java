package pl.sebcel.genealogy.gui.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.gui.component.list.model.MultiValueReferenceModel;

public class ReferenceSelector extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JList list = new JList();
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel buttonPanel = new JPanel();
    private JButton buttonConfirm = new JButton("Wybierz");
    private JButton buttonCancel = new JButton("Anuluj");
    private MultiValueReferenceModel listModel = new MultiValueReferenceModel();
    private ReferenceListElement selectedItem = null;

    public ReferenceSelector(List<ReferenceListElement> options) {
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.scrollPane.setViewportView(list);
        this.list.setModel(listModel);
        this.listModel.setData(options);
        this.buttonPanel.add(buttonConfirm);
        this.buttonPanel.add(buttonCancel);
        this.buttonConfirm.addActionListener(this);
        this.buttonCancel.addActionListener(this);
    }

    public ReferenceListElement getSelectedItem() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        this.setBounds(width / 2, height / 2, width, height);
        this.setModal(true);
        this.setVisible(true);

        return selectedItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonConfirm) {
            confirm();
        }
        if (e.getSource() == buttonCancel) {
            cancel();
        }
    }

    private void confirm() {
        if (!list.isSelectionEmpty()) {
            selectedItem = (ReferenceListElement) listModel.getElementObjectAt(list.getSelectedIndex());
        } else {
            selectedItem = null;
        }
        this.setVisible(false);
    }

    private void cancel() {
        selectedItem = null;
        this.setVisible(false);
    }
}