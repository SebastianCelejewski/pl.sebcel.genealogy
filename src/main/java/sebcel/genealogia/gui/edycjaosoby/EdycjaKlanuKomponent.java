package sebcel.genealogia.gui.edycjaosoby;

import java.util.List;

import javax.swing.JOptionPane;

import sebcel.genealogia.gui.component.Etykieta;
import sebcel.genealogia.gui.component.Lista;
import sebcel.genealogia.gui.component.PoleTekstowe;
import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.DaneEdycjiKlanuStruct;
import sebcel.genealogia.struct.DiagramInfoStruct;
import sebcel.genealogia.struct.OsobaStruct;

public class EdycjaKlanuKomponent extends EdycjaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private DaneEdycjiKlanuStruct daneKlanu; 
	
	private Etykieta lNazwa = new Etykieta("Nazwa:");
	private Etykieta lOpis = new Etykieta("Opis:");
	private Etykieta lKorzen = new Etykieta("Protoplasta:");
	
	private PoleTekstowe tNazwa = new PoleTekstowe();
	private PoleTekstowe tOpis = new PoleTekstowe();
	private Lista tKorzen = new Lista();
	
	public EdycjaKlanuKomponent() {
		
		int y=0;
		this.add(lNazwa, lNazwa.getConstraints(0,y++));
		this.add(lOpis, lOpis.getConstraints(0,y++));
		this.add(lKorzen, lKorzen.getConstraints(0,y++));

		y=0;
		this.add(tNazwa, tNazwa.getConstraints(1,y++));
		this.add(tOpis, tOpis.getConstraints(1,y++));
		this.add(tKorzen, tKorzen.getConstraints(1,y++));
		
	}
	
	public void wczytajDane(Long id)  {
		odswiezListy();
		
		if (id==null) {
			wyczyscPola();
			return;
		}
		daneKlanu = DatabaseDelegate.getDaneDoEdycjiKlanu(id);
		
		tNazwa.setText(daneKlanu.nazwa);
		tOpis.setText(daneKlanu.opis);
		tKorzen.setSelectedItem(daneKlanu.korzen);

	}
	
	private void odswiezListy() {
		List<OsobaStruct> osoby = DatabaseDelegate.getOsoby();
		tKorzen.removeAllItems();
		tKorzen.addItem(new String("<Wybierz>"));
		for (OsobaStruct osoba : osoby) {
			tKorzen.addItem(osoba);
		}
	}
	
	public boolean zapiszDane() {
		if (trybPracy==TrybPracy.DODAWANIE) daneKlanu = new DaneEdycjiKlanuStruct();
		
		if (tKorzen.getSelectedIndex()==0) {
			JOptionPane.showMessageDialog(this, "Wska¿ protoplastê klanu","B³¹d", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		daneKlanu.nazwa = tNazwa.getText().trim();
		daneKlanu.opis = tOpis.getText().trim();
		daneKlanu.korzen = (OsobaStruct) tKorzen.getSelectedItem();
		
		if (trybPracy==TrybPracy.EDYCJA) {
			DatabaseDelegate.zapiszDanePoEdycjiKlanu(daneKlanu);
		} else {
			DatabaseDelegate.zapiszNowyKlan(daneKlanu);
		}
		
		return true;
	}
	
	private void wyczyscPola() {
		tNazwa.setText("");
		tOpis.setText("");
		tKorzen.setSelectedItem(0);
	}
	
	@Override
	public void usunObiekt(Long id) {
		int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ ten klan?","Usuwanie klanu", JOptionPane.YES_NO_OPTION);
		if (result==JOptionPane.YES_OPTION) {
			DatabaseDelegate.usunKlan(id);
		}
	}

	@Override
	public String getTitle() {
		if (trybPracy==TrybPracy.DODAWANIE) {
			return "Dodawanie nowego klanu";
		} else {
			return "Edycja danych klanu";
		}
	}

	@Override
	public DiagramInfoStruct getDiagramInfo(Long id) {
		DaneEdycjiKlanuStruct daneKlanu = DatabaseDelegate.getDaneDoEdycjiKlanu(id);
		DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
		diagramInfo.idKorzenia = daneKlanu.korzen.id;
		diagramInfo.nazwa = daneKlanu.nazwa;
		diagramInfo.opis = "Diagram klanu '"+daneKlanu.nazwa+"'";
		return diagramInfo;
	}
	
	
	
}