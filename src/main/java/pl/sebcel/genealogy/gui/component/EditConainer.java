package pl.sebcel.genealogy.gui.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.gui.component.AbstractEditComponent.EditMode;
import pl.sebcel.genealogy.gui.control.UpdateListener;

public class EditConainer extends JDialog {

    public final static long serialVersionUID = 0l;

    private AbstractEditComponent editComponent;
    private JPanel buttonsPanel = new JPanel();

    private JButton acceptButton = new JButton("Zatwierdź");
    private JButton cancelButton = new JButton("Anuluj");
    private UpdateListener updateListener;

    public EditConainer(AbstractEditComponent editComponent) {
        this.editComponent = editComponent;
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenDimension.width / 2;
        int height = 2 * screenDimension.height / 3;
        this.setBounds(width / 2, height / 3, width, height);
        this.setLayout(new BorderLayout());
        this.add(editComponent, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(acceptButton);
        buttonsPanel.add(cancelButton);

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirm();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void loadData(Long objectId) {
        editComponent.loadData(objectId);
    }

    private void confirm() {
        if (editComponent.saveData()) {
            updateListener.update();
            close();
        }
    }

    private void cancel() {
        close();
    }

    private void close() {
        this.setVisible(false);
        updateListener.update();
    }

    public void setEditMode(EditMode editMode) {
        editComponent.setEditMode(editMode);
        this.setTitle(editComponent.getTitle());
    }

    public void deleteObject(Long id) {
        editComponent.deleteElement(id);
        updateListener.update();
    }

    public DiagramInfoStruct getDiagramInfo(Long id) {
        return editComponent.getDiagramInfo(id);
    }
}