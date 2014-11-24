package sebcel.genealogia.gui.edycjaosoby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import sebcel.genealogia.gui.component.UpdateListener;
import sebcel.genealogia.gui.edycjaosoby.EdycjaKomponent.TrybPracy;
import sebcel.genealogia.struct.DiagramInfoStruct;

public class EdycjaKontener extends JDialog {
	
	public final static long serialVersionUID = 0l;
	
	private EdycjaKomponent edycjaKomponent;
	private JPanel panelPrzyciskow = new JPanel();
	
	private JButton przyciskAkceptuj = new JButton("Zatwierd�");
	private JButton przyciskAnuluj = new JButton("Anuluj");
	private UpdateListener updateListener;
	
	public EdycjaKontener(EdycjaKomponent edycjaKomponent) {
		this.edycjaKomponent = edycjaKomponent;
		this.setSize(new Dimension(600,600));
		this.setLayout(new BorderLayout());
		this.add(edycjaKomponent, BorderLayout.CENTER);
		this.add(panelPrzyciskow, BorderLayout.SOUTH);
		
		panelPrzyciskow.add(przyciskAkceptuj);
		panelPrzyciskow.add(przyciskAnuluj);

		przyciskAkceptuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zatwierdz();
			}
		});
		
		przyciskAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anuluj();
			}
		});
	}
	
	public void setUpdateListener(UpdateListener updateListener) {
		this.updateListener = updateListener;
	}
	
	public void wczytajDane(Long idOsoby) {
		edycjaKomponent.wczytajDane(idOsoby);
	}
	
	private void zatwierdz() {
		if (edycjaKomponent.zapiszDane()) {
			updateListener.update();
			zamknij();
		}
	}
	
	private void anuluj() {
		zamknij();
	}
	
	private void zamknij() {
		this.setVisible(false);
		updateListener.update();
	}
	
	public void setTrybPracy(TrybPracy trybPracy) {
		edycjaKomponent.setTrybPracy(trybPracy);
		this.setTitle(edycjaKomponent.getTitle());
	}
	
	public void usunObiekt(Long id) {
		edycjaKomponent.usunObiekt(id);
		updateListener.update();
	}
	
	public DiagramInfoStruct getDiagramInfo(Long id) {
		return edycjaKomponent.getDiagramInfo(id);
	}
}