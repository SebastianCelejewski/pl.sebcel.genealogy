package sebcel.genealogia.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import sebcel.genealogia.gui.edycjaosoby.EdycjaDokumentuKomponent;
import sebcel.genealogia.gui.edycjaosoby.EdycjaKlanuKomponent;
import sebcel.genealogia.gui.edycjaosoby.EdycjaKontener;
import sebcel.genealogia.gui.edycjaosoby.EdycjaOsobyKomponent;
import sebcel.genealogia.gui.edycjaosoby.EdycjaZwiazkuKomponent;
import sebcel.genealogia.gui.lista.ListaDokumentowKomponent;
import sebcel.genealogia.gui.lista.ListaKlanowKomponent;
import sebcel.genealogia.gui.lista.ListaKontener;
import sebcel.genealogia.gui.lista.ListaOsobKomponent;
import sebcel.genealogia.gui.lista.ListaZwiazkowKomponent;

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
		this.setTitle("Genealogia 1.2.5");

		tabbedPane.addTab("Lista osób", listaOsob);
		tabbedPane.addTab("Lista zwi¹zków", listaZwiazkow);
		tabbedPane.addTab("Lista klanów", listaKlanow);
		tabbedPane.addTab("Lista dokumentów", listaDokumentow);
		
		this.setSize(800,600);
		this.setLocation(100,100);
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
	}
}