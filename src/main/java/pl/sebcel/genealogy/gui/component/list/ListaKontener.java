package pl.sebcel.genealogy.gui.component.list;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.gui.GuiRysownik;
import pl.sebcel.genealogy.gui.component.EdycjaKontener;
import pl.sebcel.genealogy.gui.component.EdycjaKomponent.TrybPracy;
import pl.sebcel.genealogy.gui.control.UpdateListener;

public class ListaKontener extends JPanel implements UpdateListener, ActionListener {
	
	public final static long serialVersionUID = 0l;
	
	private ListaKomponent listaKomponent;
	private EdycjaKontener edycjaKontener;
	private JPanel panelKlawiszy = new JPanel();
	
	private JButton klawiszNowy = new JButton("Nowy");
	private JButton klawiszEdycja = new JButton("Edycja");
	private JButton klawiszUsun = new JButton("Usuñ");
	private JButton klawiszDrzewo = new JButton("Drzewo");
	private JButton klawiszZapisz = new JButton("Zapisz");
	
	public ListaKontener(ListaKomponent listaKomponent, EdycjaKontener edycjaKontener) {
		this.listaKomponent = listaKomponent;
		this.edycjaKontener = edycjaKontener;
		
		this.setLayout(new BorderLayout());
		this.add(listaKomponent, BorderLayout.CENTER);
		this.add(panelKlawiszy, BorderLayout.SOUTH);
		
		panelKlawiszy.add(klawiszNowy);
		panelKlawiszy.add(klawiszEdycja);
		panelKlawiszy.add(klawiszUsun);
		panelKlawiszy.add(klawiszDrzewo);
		panelKlawiszy.add(klawiszZapisz);
		
		klawiszNowy.addActionListener(this);
		klawiszEdycja.addActionListener(this);
		klawiszUsun.addActionListener(this);
		klawiszDrzewo.addActionListener(this);
		klawiszZapisz.addActionListener(this);
		
		edycjaKontener.setUpdateListener(this);
	}
	
	public void update() {
		listaKomponent.refresh();
		this.repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(klawiszNowy)) nowy();
		if (e.getSource().equals(klawiszEdycja)) edytuj(listaKomponent.getSelectedId());
		if (e.getSource().equals(klawiszUsun)) usun(listaKomponent.getSelectedId());
		if (e.getSource().equals(klawiszDrzewo)) rysuj(listaKomponent.getSelectedId());
		if (e.getSource().equals(klawiszZapisz)) zapisz(listaKomponent);
	}
	
	private void nowy() {
		edycjaKontener.setTrybPracy(TrybPracy.DODAWANIE);
		edycjaKontener.wczytajDane(null);
		edycjaKontener.setModal(true);
		edycjaKontener.setVisible(true);
	}
	
	private void edytuj(Long id) {
		edycjaKontener.setTrybPracy(TrybPracy.EDYCJA);
		edycjaKontener.wczytajDane(id);
		edycjaKontener.setModal(true);
		edycjaKontener.setVisible(true);
	}
	
	private void usun(Long id) {
		edycjaKontener.usunObiekt(id);
	}
	
	private void rysuj(Long id) {
		DiagramInfoStruct diagramInfo = edycjaKontener.getDiagramInfo(id);
		new GuiRysownik().rysuj(diagramInfo);
	}
	
	private void zapisz(ListaKomponent listaKomponent) {
		JFileChooser fileChooser = new JFileChooser();
		CustomFileFilter fileFilter = new CustomFileFilter("Export file formats", "txt", "xml");
		fileChooser.setFileFilter(fileFilter);
		
		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();
				DataExporter dataExporter = listaKomponent.getExporter(CustomFileFilter.getExtension(fileChooser.getSelectedFile()));
				dataExporter.exportData(file);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Wyst¹pi³ b³¹d:\n"+ex.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}