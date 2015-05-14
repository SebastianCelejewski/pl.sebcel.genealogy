package pl.sebcel.genealogy.gui.component;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import pl.sebcel.genealogy.dto.DiagramInfoStruct;

public abstract class EdycjaKomponent extends JPanel {
	
	public final static long serialVersionUID = 0l;
	
	public enum TrybPracy {DODAWANIE, EDYCJA};
	
	protected TrybPracy trybPracy = TrybPracy.EDYCJA;
	
	public EdycjaKomponent() {
		this.setLayout(new GridBagLayout());
	}
	
	public void setTrybPracy(TrybPracy trybPracy) {
		this.trybPracy = trybPracy;
	}
	public TrybPracy getTrybPracy() {
		return this.trybPracy; 
	}

	public abstract void wczytajDane(Long id);
	public abstract boolean zapiszDane();
	public abstract void usunObiekt(Long id);
	public abstract String getTitle();
	public abstract DiagramInfoStruct getDiagramInfo(Long id);
}