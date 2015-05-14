package pl.sebcel.genealogy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import pl.sebcel.genealogy.gui.component.EdycjaDokumentuKomponent;
import pl.sebcel.genealogy.gui.component.EdycjaKlanuKomponent;
import pl.sebcel.genealogy.gui.component.EdycjaKontener;
import pl.sebcel.genealogy.gui.component.EdycjaOsobyKomponent;
import pl.sebcel.genealogy.gui.component.EdycjaZwiazkuKomponent;
import pl.sebcel.genealogy.gui.component.list.ListaDokumentowKomponent;
import pl.sebcel.genealogy.gui.component.list.ListaKlanowKomponent;
import pl.sebcel.genealogy.gui.component.list.ListaKontener;
import pl.sebcel.genealogy.gui.component.list.ListaOsobKomponent;
import pl.sebcel.genealogy.gui.component.list.ListaZwiazkowKomponent;

public class MainFrame extends JFrame {

    private EdycjaKontener edycjaOsob = new EdycjaKontener(new EdycjaOsobyKomponent());
    private EdycjaKontener edycjaZwiazkow = new EdycjaKontener(new EdycjaZwiazkuKomponent());
    private EdycjaKontener edycjaKlanow = new EdycjaKontener(new EdycjaKlanuKomponent());
    private EdycjaKontener edycjaDokumentow = new EdycjaKontener(new EdycjaDokumentuKomponent());

    private ListaKontener listaOsob = new ListaKontener(new ListaOsobKomponent(), edycjaOsob);
    private ListaKontener listaZwiazkow = new ListaKontener(new ListaZwiazkowKomponent(), edycjaZwiazkow);
    private ListaKontener listaKlanow = new ListaKontener(new ListaKlanowKomponent(), edycjaKlanow);
    private ListaKontener listaDokumentow = new ListaKontener(new ListaDokumentowKomponent(), edycjaDokumentow);
    private JTabbedPane tabbedPane = new JTabbedPane();

    public final static long serialVersionUID = 0l;

    public MainFrame() {
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setTitle("Genealogia 1.2.6");

        tabbedPane.addTab("Lista osób", listaOsob);
        tabbedPane.addTab("Lista zwi¹zków", listaZwiazkow);
        tabbedPane.addTab("Lista klanów", listaKlanow);
        tabbedPane.addTab("Lista dokumentów", listaDokumentow);

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