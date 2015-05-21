package pl.sebcel.genealogy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import pl.sebcel.genealogy.gui.component.DocumentEditComponent;
import pl.sebcel.genealogy.gui.component.ClanEditComponent;
import pl.sebcel.genealogy.gui.component.EditConainer;
import pl.sebcel.genealogy.gui.component.PersonEditComponent;
import pl.sebcel.genealogy.gui.component.RelationshipEditComponent;
import pl.sebcel.genealogy.gui.component.list.DocumentsListComponent;
import pl.sebcel.genealogy.gui.component.list.ClanListComponent;
import pl.sebcel.genealogy.gui.component.list.ListContainer;
import pl.sebcel.genealogy.gui.component.list.PeopleListComponent;
import pl.sebcel.genealogy.gui.component.list.RelationshipListComponent;

public class MainFrame extends JFrame {

    private EditConainer personEditContainer = new EditConainer(new PersonEditComponent());
    private EditConainer relationshipEditContainer = new EditConainer(new RelationshipEditComponent());
    private EditConainer clanEditContainer = new EditConainer(new ClanEditComponent());
    private EditConainer documentEditContainer = new EditConainer(new DocumentEditComponent());

    private ListContainer peopleList = new ListContainer(new PeopleListComponent(), personEditContainer);
    private ListContainer relationshipsList = new ListContainer(new RelationshipListComponent(), relationshipEditContainer);
    private ListContainer clansList = new ListContainer(new ClanListComponent(), clanEditContainer);
    private ListContainer documentsList = new ListContainer(new DocumentsListComponent(), documentEditContainer);
    private JTabbedPane tabbedPane = new JTabbedPane();

    public final static long serialVersionUID = 0l;

    public MainFrame() {
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setTitle("Genealogia 1.2.6");

        tabbedPane.addTab("Lista osób", peopleList);
        tabbedPane.addTab("Lista zwi¹zków", relationshipsList);
        tabbedPane.addTab("Lista klanów", clansList);
        tabbedPane.addTab("Lista dokumentów", documentsList);

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenDimension.width / 2;
        int height = screenDimension.height / 2;
        this.setBounds(width / 2, height / 2, width, height);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
    }
}