package sebcel.genealogia.gui.edycjaosoby;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import sebcel.genealogia.gui.component.Drzewo;
import sebcel.genealogia.gui.component.Etykieta;
import sebcel.genealogia.gui.component.Lista;
import sebcel.genealogia.gui.component.ObszarTekstowy;
import sebcel.genealogia.gui.component.PoleTekstowe;
import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.DaneEdycjiOsobyStruct;
import sebcel.genealogia.struct.DiagramInfoStruct;
import sebcel.genealogia.struct.OsobaStruct;
import sebcel.genealogia.struct.RodzinaStruct;
import sebcel.genealogia.struct.ZwiazekStruct;

public class EdycjaOsobyKomponent extends EdycjaKomponent {
	
	public final static long serialVersionUID = 0l;
	
	private DaneEdycjiOsobyStruct daneOsoby; 
	
	private Etykieta lPlec = new Etykieta("P³eæ:");
	private Etykieta lImiona = new Etykieta("Imiona:");
	private Etykieta lNazwisko = new Etykieta("Nazwisko:");
	private Etykieta lDataUrodzenia = new Etykieta("Data urodzenia:");
	private Etykieta lMiejsceUrodzenia = new Etykieta("Miejsce urodzenia:");
	private Etykieta lDataSmierci = new Etykieta("Data œmierci:");
	private Etykieta lMiejsceSmierci = new Etykieta("Miejsce œmierci:");
	private Etykieta lDataPochowania = new Etykieta("Data pochowania:");
	private Etykieta lMiejscePochowania = new Etykieta("Miejsce pochowania:");
	private Etykieta lMiejsceZamiekszania = new Etykieta("Miejsce zamieszkania:");
	private Etykieta lWyksztalcenie = new Etykieta("Wykszta³cenie");
	private Etykieta lZawodyWykonywane = new Etykieta("Zawody wykonywane");
	private Etykieta lRodzice = new Etykieta("Rodzice");
	private Etykieta lOpis = new Etykieta("Opis");
	private Etykieta lRodzina = new Etykieta("Rodzina");
	
	private Lista tPlec = new Lista();
	private PoleTekstowe tImiona = new PoleTekstowe();
	private PoleTekstowe tNazwisko = new PoleTekstowe();
	private PoleTekstowe tDataUrodzenia = new PoleTekstowe();
	private PoleTekstowe tMiejsceUrodzenia = new PoleTekstowe();
	private PoleTekstowe tDataSmierci = new PoleTekstowe();
	private PoleTekstowe tMiejsceSmierci = new PoleTekstowe();
	private PoleTekstowe tDataPochowania = new PoleTekstowe();
	private PoleTekstowe tMiejscePochowania = new PoleTekstowe();
	private PoleTekstowe tMiejsceZamieszkania = new PoleTekstowe();
	private PoleTekstowe tWyksztalcenie = new PoleTekstowe();
	private PoleTekstowe tZawodyWykonywane = new PoleTekstowe();
	private ObszarTekstowy tOpis = new ObszarTekstowy();
	private Lista tRodzice = new Lista();
	private Drzewo tRodzina = new Drzewo();
	
	public EdycjaOsobyKomponent() {
		this.setLayout(new GridBagLayout());
		
		int y=0;
		this.add(lPlec, lPlec.getConstraints(0,y++));
		this.add(lImiona, lImiona.getConstraints(0,y++));
		this.add(lNazwisko, lNazwisko.getConstraints(0,y++));
		this.add(lDataUrodzenia, lDataUrodzenia.getConstraints(0,y++));
		this.add(lMiejsceUrodzenia, lMiejsceUrodzenia.getConstraints(0,y++));
		this.add(lDataSmierci, lDataUrodzenia.getConstraints(0,y++));
		this.add(lMiejsceSmierci, lMiejsceSmierci.getConstraints(0,y++));
		this.add(lDataPochowania, lDataUrodzenia.getConstraints(0,y++));
		this.add(lMiejscePochowania, lMiejscePochowania.getConstraints(0,y++));
		this.add(lMiejsceZamiekszania, lMiejsceZamiekszania.getConstraints(0,y++));
		this.add(lWyksztalcenie, lWyksztalcenie.getConstraints(0,y++));
		this.add(lZawodyWykonywane, lZawodyWykonywane.getConstraints(0,y++));
		this.add(lRodzice, lRodzice.getConstraints(0,y++));
		this.add(lOpis, lOpis.getConstraints(0,y++));
		this.add(lRodzina, lRodzina.getConstraints(0,y++));

		y=0;
		this.add(tPlec, tPlec.getConstraints(1,y++));
		this.add(tImiona, tImiona.getConstraints(1,y++));
		this.add(tNazwisko, tNazwisko.getConstraints(1,y++));
		this.add(tDataUrodzenia, tDataUrodzenia.getConstraints(1,y++));
		this.add(tMiejsceUrodzenia, tMiejsceUrodzenia.getConstraints(1,y++));
		this.add(tDataSmierci, tDataSmierci.getConstraints(1,y++));
		this.add(tMiejsceSmierci, tMiejsceSmierci.getConstraints(1,y++));
		this.add(tDataPochowania, tDataPochowania.getConstraints(1,y++));
		this.add(tMiejscePochowania, tMiejscePochowania.getConstraints(1,y++));
		this.add(tMiejsceZamieszkania, tMiejsceZamieszkania.getConstraints(1,y++));
		this.add(tWyksztalcenie, tWyksztalcenie.getConstraints(1,y++));
		this.add(tZawodyWykonywane, tZawodyWykonywane.getConstraints(1,y++));
		this.add(tRodzice, tRodzice.getConstraints(1,y++));
		this.add(tOpis, tOpis.getConstraints(1,y++));
		this.add(tRodzina, tRodzina.getConstraints(1,y++));
		
		tPlec.removeAllItems();
		tPlec.addItem("<Wybierz>");
		tPlec.addItem("Mê¿czyzna");
		tPlec.addItem("Kobieta");
	}
	
	public void wczytajDane(Long idOsoby)  {
		odswiezListy();
		
		if (idOsoby==null) {
			wyczyscPola();
			return;
		}
		daneOsoby = DatabaseDelegate.getDaneDoEdycjiOsoby(idOsoby);
		
		if (daneOsoby.plec==null) {
			tPlec.setSelectedIndex(0);
		} else if (daneOsoby.plec.equalsIgnoreCase("mezczyzna")) {
			tPlec.setSelectedIndex(1);
		} else if (daneOsoby.plec.equalsIgnoreCase("kobieta")) {
			tPlec.setSelectedIndex(2);
		} else {
			tPlec.setSelectedIndex(0);
		}
		tImiona.setText(daneOsoby.imiona);
		tNazwisko.setText(daneOsoby.nazwisko);
		tDataUrodzenia.setText(daneOsoby.dataUrodzenia);
		tMiejsceUrodzenia.setText(daneOsoby.miejsceUrodzenia);
		tDataSmierci.setText(daneOsoby.dataSmierci);
		tMiejsceSmierci.setText(daneOsoby.miejsceSmierci);
		tDataPochowania.setText(daneOsoby.dataPochowania);
		tMiejscePochowania.setText(daneOsoby.miejscePochowania);
		tWyksztalcenie.setText(daneOsoby.wyksztalcenie);
		tZawodyWykonywane.setText(daneOsoby.zawodyWykonywane);
		tMiejsceZamieszkania.setText(daneOsoby.miejsceZamieszkania);
		tOpis.setText(daneOsoby.opis);
		if (daneOsoby.rodzice!=null) {
			tRodzice.setSelectedItem(daneOsoby.rodzice);
		} else {
			tRodzice.setSelectedIndex(0);
		}
		
		List<RodzinaStruct> rodziny = daneOsoby.rodziny;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		if (rodziny!=null && rodziny.size()>0) {
			for (RodzinaStruct rodzina : rodziny) {
				DefaultMutableTreeNode nodeRodzina = new DefaultMutableTreeNode(rodzina.malzonek);
				root.add(nodeRodzina);
				List<OsobaStruct> dzieci = rodzina.idDzieci;
				if (dzieci!=null && dzieci.size()>0) {
					for (OsobaStruct dziecko : dzieci) {
						DefaultMutableTreeNode nodeDziecko = new DefaultMutableTreeNode(dziecko);
						nodeRodzina.add(nodeDziecko);
					}
				}
			}
		} 
		TreeModel treeModel = new DefaultTreeModel(root);
		tRodzina.setModel(treeModel);
		
	}
	
	private void odswiezListy() {
		List<ZwiazekStruct> zwiazki = DatabaseDelegate.getZwiazki();
		tRodzice.removeAllItems();
		tRodzice.addItem(new String("<Brak>"));
		for (ZwiazekStruct zwiazek : zwiazki) {
			tRodzice.addItem(zwiazek);
		}
	}
	
	public boolean zapiszDane() {
		if (trybPracy==TrybPracy.DODAWANIE) daneOsoby = new DaneEdycjiOsobyStruct();
		
		if (tPlec.getSelectedIndex()==0) {
			JOptionPane.showMessageDialog(this, "Wybierz p³eæ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (tPlec.getSelectedIndex()==1) {
			daneOsoby.plec = "mezczyzna";
		} else {
			daneOsoby.plec = "kobieta";
		}
		daneOsoby.imiona = tImiona.getText().trim();
		daneOsoby.nazwisko = tNazwisko.getText().trim();
		daneOsoby.dataUrodzenia = tDataUrodzenia.getText().trim();
		daneOsoby.miejsceUrodzenia = tMiejsceUrodzenia.getText().trim();
		daneOsoby.dataSmierci = tDataSmierci.getText().trim();
		daneOsoby.miejsceSmierci = tMiejsceSmierci.getText().trim();
		daneOsoby.dataPochowania = tDataPochowania.getText().trim();
		daneOsoby.miejscePochowania = tMiejscePochowania.getText().trim();
		daneOsoby.wyksztalcenie = tWyksztalcenie.getText().trim();
		daneOsoby.zawodyWykonywane = tZawodyWykonywane.getText().trim();
		daneOsoby.miejsceZamieszkania = tMiejsceZamieszkania.getText().trim();
		daneOsoby.opis = tOpis.getText().trim();
		if (tRodzice.getSelectedIndex()==0) {
			daneOsoby.rodzice = null;
		} else {
			daneOsoby.rodzice = (ZwiazekStruct) tRodzice.getSelectedItem();
		}
		
		if (trybPracy==TrybPracy.EDYCJA) {
			DatabaseDelegate.zapiszDanePoEdycjiOsoby(daneOsoby);
		} else {
			DatabaseDelegate.zapiszNowaOsobe(daneOsoby);
		}
		
		return true;
	}
	
	private void wyczyscPola() {
		tImiona.setText("");
		tNazwisko.setText("");
		tDataUrodzenia.setText("");
		tMiejsceUrodzenia.setText("");
		tDataSmierci.setText("");
		tMiejsceSmierci.setText("");
		tDataPochowania.setText("");
		tMiejscePochowania.setText("");
		tMiejsceZamieszkania.setText("");
		tWyksztalcenie.setText("");
		tZawodyWykonywane.setText("");
		tOpis.setText("");
		tRodzice.setSelectedIndex(0);
		tRodzina.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
	}
	
	@Override
	public void usunObiekt(Long id) {
		int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun¹æ tê osobê?","Usuwanie osoby", JOptionPane.YES_NO_OPTION);
		if (result==JOptionPane.YES_OPTION) {
			DatabaseDelegate.usunOsobe(id);
		}
	}
	
	@Override
	public String getTitle() {
		if (trybPracy==TrybPracy.DODAWANIE) {
			return "Dodawanie nowej osoby";
		} else {
			return "Edycja danych osoby";
		}
	}

	@Override
	public DiagramInfoStruct getDiagramInfo(Long id) {
		DaneEdycjiOsobyStruct daneOsoby = DatabaseDelegate.getDaneDoEdycjiOsoby(id);
		DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
		diagramInfo.idKorzenia = daneOsoby.id;
		diagramInfo.nazwa = daneOsoby.imiona+" "+daneOsoby.nazwisko+" ("+daneOsoby.id+")";
		diagramInfo.opis = "Diagram osoby '"+daneOsoby.imiona+" "+daneOsoby.nazwisko+"'";
		return diagramInfo;
	}
	
}