package sebcel.genealogia.lib;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import sebcel.genealogia.entity.Klan;
import sebcel.genealogia.entity.Osoba;
import sebcel.genealogia.entity.Zwiazek;
import sebcel.genealogia.struct.DaneEdycjiKlanuStruct;
import sebcel.genealogia.struct.DaneEdycjiOsobyStruct;
import sebcel.genealogia.struct.DaneEdycjiZwiazkuStruct;
import sebcel.genealogia.struct.DrzewoOsobaStruct;
import sebcel.genealogia.struct.DrzewoRodzinaStruct;
import sebcel.genealogia.struct.ElementListyKlanowStruct;
import sebcel.genealogia.struct.ElementListyOsobStruct;
import sebcel.genealogia.struct.ElementListyZwiazkowStruct;
import sebcel.genealogia.struct.OsobaStruct;
import sebcel.genealogia.struct.RodzinaStruct;
import sebcel.genealogia.struct.ZwiazekStruct;

public class DatabaseDelegate {
	
	public static List<ElementListyOsobStruct> getListaOsob() {
		System.out.println("[DatabaseDelegate][getListaOsob]");
		Collection<Osoba> osoby = DatabaseLib.getOsoby();
		List<ElementListyOsobStruct> listaOsob = new ArrayList<ElementListyOsobStruct>();
		if (osoby!=null && osoby.size()>0) {
			for (Osoba osoba : osoby) {
				ElementListyOsobStruct elementListy = new ElementListyOsobStruct();
				elementListy.id = osoba.getId();
				elementListy.nazwa = osoba.toString();
				elementListy.opis = DatabaseUtil.getUrInfo(osoba)+" "+DatabaseUtil.getSmInfo(osoba);
				listaOsob.add(elementListy);
			}
		}
		return listaOsob;
	}
	
	public static String getXMLListaOsob() {
		System.out.println("[DatabaseDelegate][getXMLListaOsob]");
		StringBuffer xmlData = new StringBuffer();
		xmlData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xmlData.append("<osoby>\n");
		Collection<Osoba> osoby = DatabaseLib.getOsoby();
		if (osoby!=null && osoby.size()>0) {
			for (Osoba osoba : osoby) {
				xmlData.append(getXMLOsoba(osoba));
			}
		}
		xmlData.append("</osoby>\n");
		return xmlData.toString();
	}
	
	public static String getTxtListaOsob() {
		System.out.println("[DatabaseDelegate][getTXTListaOsob]");
		StringBuffer xmlData = new StringBuffer();
		xmlData.append("id;imiona;nazwisko;data-urodzenia;miejsce-urodzenia;data-smierci;miejsce-smierci;data-pochowania;miejsce-pochowania;zamieszkanie;wyksztalcenie;zawody;rodzice;zwiazki\n");
		Collection<Osoba> osoby = DatabaseLib.getOsoby();
		if (osoby!=null && osoby.size()>0) {
			for (Osoba osoba : osoby) {
				xmlData.append(getTXTOsoba(osoba)+"\n");
			}
		}
		return xmlData.toString();
	}
	
	public static String getXMLOsoba(Osoba osoba) {
		System.out.println("[DatabaseDelegate][getXMLOsoba]");
		StringBuffer xmlData = new StringBuffer();
		xmlData.append("<osoba>\n");
		xmlData.append("  "+getXMLField("id", osoba.getId()));
		xmlData.append("  "+getXMLField("imiona", osoba.getImiona()));
		xmlData.append("  "+getXMLField("nazwisko", osoba.getNazwisko()));
		if (osoba.getZwiazekRodzicow()!=null) {
			xmlData.append("  "+getXMLField("rodzice", osoba.getZwiazekRodzicow().getId()));
		}

		xmlData.append("  <dane-urodzenia>\n");
		xmlData.append("    "+getXMLField("data", osoba.getDataUrodzenia()));
		xmlData.append("    "+getXMLField("miejsce", osoba.getMiejsceUrodzenia()));
		xmlData.append("  </dane-urodzenia>\n");
		
		xmlData.append("  <dane-smierci>\n");
		xmlData.append("    "+getXMLField("data", osoba.getDataSmierci()));
		xmlData.append("    "+getXMLField("miejsce", osoba.getMiejsceSmierci()));
		xmlData.append("  </dane-smierci>\n");
		
		xmlData.append("  <dane-pochowania>\n");
		xmlData.append("    "+getXMLField("data", osoba.getDataPochowania()));
		xmlData.append("    "+getXMLField("miejsce", osoba.getMiejscePochowania()));
		xmlData.append("  </dane-pochowania>\n");

		xmlData.append("  "+getXMLField("zamieszkanie", osoba.getMiejsceZamieszkania()));
		xmlData.append("  "+getXMLField("wyksztalcenie", osoba.getWyksztalcenie()));
		xmlData.append("  "+getXMLField("zawody", osoba.getZawodyWykonywane()));
		
		xmlData.append("  <zwiazki>\n");
		Collection<Zwiazek> zwiazki = null;
		if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
			zwiazki = osoba.getZwiazkiMezowskie();
		} else {
			zwiazki = osoba.getZwiazkiZonowskie();
		}
		
		if (zwiazki!=null && zwiazki.size()>0) {
			for (Zwiazek zwiazek : zwiazki) {
				xmlData.append("    "+getXMLField("zwiazek-id", zwiazek.getId()));
			}
		}
		
		xmlData.append("  </zwiazki>\n");
		xmlData.append("</osoba>\n");
		return xmlData.toString();
	}
	
	public static String getTXTOsoba(Osoba osoba) {
		System.out.println("[DatabaseDelegate][getTXTOsoba]");
		StringBuffer xmlData = new StringBuffer();
		xmlData.append(getTXTField(osoba.getId()));
		xmlData.append(getTXTField(osoba.getImiona()));
		xmlData.append(getTXTField(osoba.getNazwisko()));
		xmlData.append(getTXTField(osoba.getDataUrodzenia()));
		xmlData.append(getTXTField(osoba.getMiejsceUrodzenia()));
		xmlData.append(getTXTField(osoba.getDataSmierci()));
		xmlData.append(getTXTField(osoba.getMiejsceSmierci()));
		xmlData.append(getTXTField(osoba.getDataPochowania()));
		xmlData.append(getTXTField(osoba.getMiejscePochowania()));
		xmlData.append(getTXTField(osoba.getMiejsceZamieszkania()));
		xmlData.append(getTXTField(osoba.getWyksztalcenie()));
		xmlData.append(getTXTField(osoba.getZawodyWykonywane()));

		Collection<Zwiazek> zwiazki = null;
		StringBuffer zwiazkiStr = new StringBuffer();
		if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
			zwiazki = osoba.getZwiazkiMezowskie();
		} else {
			zwiazki = osoba.getZwiazkiZonowskie();
		}
		
		if (zwiazki!=null && zwiazki.size()>0) {
			for (Zwiazek zwiazek : zwiazki) {
				Long idMalzonka = null;
				if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
					idMalzonka = zwiazek.getKobieta().getId();
				} else {
					idMalzonka = zwiazek.getMezczyzna().getId();
				}
				String zwiazekStr = ""+zwiazek.getId()+":("+idMalzonka;
				
				Collection<Osoba> dzieci = zwiazek.getDzieci();
				if (dzieci!=null && dzieci.size()>0) {
					String dzieciStr = "";
					for (Osoba dziecko : dzieci) {
						if (dzieciStr.length()>0) {
							dzieciStr+=", ";
						}
						dzieciStr+=dziecko.getId();
					}
					zwiazekStr+="; "+dzieciStr;
				}
				zwiazekStr+=")";
				
				if (zwiazkiStr.length()>0) {
					zwiazkiStr.append("; ");
				}
				zwiazkiStr.append(zwiazekStr);
			}
		}
		xmlData.append(zwiazkiStr);
		
		return xmlData.toString();
	}
	
	private static String getXMLField(String name, Object value) {
		if (value!=null) {
			return "<"+name+">"+value.toString()+"</"+name+">\n";
		} else {
			return "";
		}
	}
	
	private static String getTXTField(Object value) {
		if (value==null) return ";";
		if (value instanceof String) return "\""+value+"\";";
		return value.toString()+";";
	}
	
	public static List<ElementListyZwiazkowStruct> getListaZwiazkow() {
		System.out.println("[DatabaseDelegate][getListaZwiazkow]");
		Collection<Zwiazek> zwiazki = DatabaseLib.getZwiazki(null, null);
		List<ElementListyZwiazkowStruct> listaZwiazkow = new ArrayList<ElementListyZwiazkowStruct>();
		if (zwiazki!=null && zwiazki.size()>0) {
			for (Zwiazek zwiazek : zwiazki) {
				ElementListyZwiazkowStruct elementListy = new ElementListyZwiazkowStruct();
				elementListy.id = zwiazek.getId();
				elementListy.mezczyzna = zwiazek.getMezczyzna().toString();
				elementListy.kobieta = zwiazek.getKobieta().toString();
				elementListy.opis = DatabaseUtil.getSlInfo(zwiazek)+" "+DatabaseUtil.getRzwInfo(zwiazek);
				listaZwiazkow.add(elementListy);
			}
		}
		return listaZwiazkow;
	}
	
	public static List<ElementListyKlanowStruct> getListaKlanow() {
		System.out.println("[DatabaseDelegate][getListaKlanow]");
		Collection<Klan> klany = DatabaseLib.getKlany();
		List<ElementListyKlanowStruct> listaKlanow = new ArrayList<ElementListyKlanowStruct>();
		if (klany!=null && klany.size()>0) {
			for (Klan klan : klany) {
				ElementListyKlanowStruct elementListy = new ElementListyKlanowStruct();
				elementListy.id = klan.getId();
				elementListy.nazwa = klan.getNazwa();
				elementListy.opis = klan.getOpis();
				elementListy.korzen = klan.getKorzen().toString();
				listaKlanow.add(elementListy);
			}
		}
		return listaKlanow;
	}
	
	public static DrzewoOsobaStruct getDaneOsoby(Long idOsoby) {
		System.out.println("[DatabaseDelegate][getDaneOsoby]");
		Osoba osoba = DatabaseLib.getOsoba(idOsoby);
		DrzewoOsobaStruct osobaStruct = new DrzewoOsobaStruct();
		osobaStruct.id= osoba.getId();
		osobaStruct.nazwa = osoba.getImiona()+" "+osoba.getNazwisko();
		osobaStruct.daneUrodzenia = DatabaseUtil.getUrInfo(osoba);
		osobaStruct.daneSmierci = DatabaseUtil.getSmInfo(osoba);
		osobaStruct.danePochowania = "";
		osobaStruct.daneZamieszkania = DatabaseUtil.getZamInfo(osoba);
		osobaStruct.daneZawodu = DatabaseUtil.getZawInfo(osoba);
		
		Collection<Zwiazek> zwiazki = null;
		if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
			zwiazki = osoba.getZwiazkiMezowskie();
		} else {
			zwiazki = osoba.getZwiazkiZonowskie();
		}
		
		if (zwiazki!=null && zwiazki.size()>0) {
			List<DrzewoRodzinaStruct> idRodzin = new ArrayList<DrzewoRodzinaStruct>();
			for (Zwiazek zwiazek : zwiazki) {
				DrzewoRodzinaStruct rodzina = new DrzewoRodzinaStruct();
				rodzina.idZwiazku = zwiazek.getId();
				if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
					rodzina.idMalzonka = zwiazek.getKobieta().getId();
				} else {
					rodzina.idMalzonka = zwiazek.getMezczyzna().getId();
				}
				rodzina.daneSpotkania = DatabaseUtil.getPozInfo(zwiazek);
				rodzina.daneSlubu = DatabaseUtil.getSlInfo(zwiazek);
				rodzina.daneRozstania = DatabaseUtil.getRozInfo(zwiazek);
				rodzina.daneRozwodu = DatabaseUtil.getRzwInfo(zwiazek);
				List<Osoba> dzieci = sortujPoDacieUrodzenia(zwiazek.getDzieci());
				if (dzieci!=null && dzieci.size()>0) {
					List<Long> idDzieci = new ArrayList<Long>();
					for (Osoba dziecko : dzieci) {
						idDzieci.add(dziecko.getId());
					}
					rodzina.idDzieci=idDzieci;
				}
				idRodzin.add(rodzina);
				zwiazek = null;
			}
			osobaStruct.rodziny = idRodzin;
		}
		return osobaStruct;
	}
	
	private static List<Osoba> sortujPoDacieUrodzenia(Collection<Osoba> osoby)
	{
		List<Osoba> rezultat = new ArrayList<Osoba>();
		rezultat.addAll(osoby);
		Collections.sort(rezultat, new Comparator<Osoba>() {

			public int compare(Osoba o1, Osoba o2) {
				String ds1 = o1.getDataUrodzenia();
				String ds2 = o2.getDataUrodzenia();
				if (ds1 == null && ds2 == null)
				{
					return 0;
				}
				if (ds1 == null)
				{
					return 1;
				}
				if (ds2 == null)
				{
					return -1;
				}
				
				String d1 = data(ds1);
				String d2 = data(ds2);
				return d1.compareTo(d2);
			}
		});
		return rezultat;
	}
	
	private final static DateFormat inputDF = new SimpleDateFormat("dd-MM-yyyy");
	private final static DateFormat outputDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private static String data(String input)
	{
		try
		{
			return outputDF.format(inputDF.parse(input));
		} catch (ParseException ex)
		{
			return input;
		}
	}
	
	public static DaneEdycjiOsobyStruct getDaneDoEdycjiOsoby(Long idOsoby) {
		System.out.println("[DatabaseDelegate][getDaneDoEdycjiOsoby]");
		Osoba osoba = DatabaseLib.getOsoba(idOsoby);
		DaneEdycjiOsobyStruct daneOsoby = new DaneEdycjiOsobyStruct();
		
		daneOsoby.id = osoba.getId();
		daneOsoby.imiona = osoba.getImiona();
		daneOsoby.nazwisko = osoba.getNazwisko();
		daneOsoby.dataUrodzenia = osoba.getDataUrodzenia();
		daneOsoby.miejsceUrodzenia = osoba.getMiejsceUrodzenia();
		daneOsoby.dataSmierci = osoba.getDataSmierci();
		daneOsoby.miejsceSmierci = osoba.getMiejsceSmierci();
		daneOsoby.dataPochowania = osoba.getDataPochowania();
		daneOsoby.miejscePochowania = osoba.getMiejscePochowania();
		daneOsoby.plec = osoba.getPlec();
		daneOsoby.wyksztalcenie = osoba.getWyksztalcenie();
		daneOsoby.zawodyWykonywane = osoba.getZawodyWykonywane();
		daneOsoby.opis = osoba.getOpis();
		daneOsoby.rodzice = null;
		daneOsoby.miejsceZamieszkania = osoba.getMiejsceZamieszkania();
		Zwiazek rodzice = osoba.getZwiazekRodzicow();
		if (rodzice!=null) {
			daneOsoby.rodzice=toStruct(rodzice);
		}
		
		daneOsoby.rodziny = new Vector<RodzinaStruct>();
		Collection<Zwiazek> rodziny = null;
		if (daneOsoby.plec!=null && daneOsoby.plec.equalsIgnoreCase("Mezczyzna")) {
			rodziny = osoba.getZwiazkiMezowskie();
		} else {
			rodziny = osoba.getZwiazkiZonowskie();
		}
		if (rodziny!=null) {
			for (Zwiazek zwiazek : rodziny) {
				Osoba malzonek = null;
				if (daneOsoby.plec!=null && daneOsoby.plec.equalsIgnoreCase("Mezczyzna")) {
					malzonek = zwiazek.getKobieta();
				} else {
					malzonek = zwiazek.getMezczyzna();
				}
				RodzinaStruct rodzina = new RodzinaStruct();
				if (malzonek!=null) rodzina.malzonek = new OsobaStruct(malzonek.getId(), malzonek.toString());
				rodzina.idDzieci = new Vector<OsobaStruct>();
				if (zwiazek.getDzieci()!=null && zwiazek.getDzieci().size()>0) {
					for (Osoba dziecko : zwiazek.getDzieci()) {
						rodzina.idDzieci.add(new OsobaStruct(dziecko.getId(), dziecko.toString()));
					}
				}
				daneOsoby.rodziny.add(rodzina);
			}
		}
		return daneOsoby;
	}
	
	public static DaneEdycjiZwiazkuStruct getDaneDoEdycjiZwiazku(Long idZwiazku) {
		System.out.println("[DatabaseDelegate][getDaneDoEdycjiZwiazku]");
		Zwiazek zwiazek = DatabaseLib.getZwiazek(idZwiazku);
		
		DaneEdycjiZwiazkuStruct daneZwiazku = new DaneEdycjiZwiazkuStruct();
		daneZwiazku.id = zwiazek.getId();
		daneZwiazku.mezczyzna = toStruct(zwiazek.getMezczyzna());
		daneZwiazku.kobieta = toStruct(zwiazek.getKobieta());
		daneZwiazku.dataPoznania = zwiazek.getDataPoznania();
		daneZwiazku.miejscePoznania = zwiazek.getMiejscePoznania();
		daneZwiazku.dataSlubu = zwiazek.getDataSlubu();
		daneZwiazku.miejsceSlubu = zwiazek.getMiejsceSlubu();
		daneZwiazku.dataRozstania = zwiazek.getDataRozstania();
		daneZwiazku.miejsceRozstania = zwiazek.getMiejsceRozstania();
		daneZwiazku.dataRozwodu = zwiazek.getDataRozwodu();
		daneZwiazku.miejsceRozwodu = zwiazek.getMiejsceRozwodu();
		daneZwiazku.opis = zwiazek.getOpis();
		
		return daneZwiazku;
	}
	
	public static DaneEdycjiKlanuStruct getDaneDoEdycjiKlanu(Long idKlanu) {
		System.out.println("[DatabaseDelegate][getDaneDoEdycjiKlanu]");
		Klan klan = DatabaseLib.getKlan(idKlanu);
		
		DaneEdycjiKlanuStruct daneKlanu = new DaneEdycjiKlanuStruct();
		daneKlanu.id = klan.getId();
		daneKlanu.nazwa = klan.getNazwa();
		daneKlanu.opis = klan.getOpis();
		daneKlanu.korzen = toStruct(klan.getKorzen());
		
		return daneKlanu;
	}
	
	public static void zapiszDanePoEdycjiOsoby(DaneEdycjiOsobyStruct daneOsoby) {
		System.out.println("[DatabaseDelegate][zapiszDanePoEdycjiOsoby]");
		Osoba osoba = DatabaseLib.getOsoba(daneOsoby.id);
		
		osoba.setPlec(daneOsoby.plec);
		osoba.setImiona(daneOsoby.imiona);
		osoba.setNazwisko(daneOsoby.nazwisko);
		osoba.setDataUrodzenia(daneOsoby.dataUrodzenia);
		osoba.setMiejsceUrodzenia(daneOsoby.miejsceUrodzenia);
		osoba.setDataSmierci(daneOsoby.dataSmierci);
		osoba.setMiejsceSmierci(daneOsoby.miejsceSmierci);
		osoba.setDataPochowania(daneOsoby.dataPochowania);
		osoba.setMiejscePochowania(daneOsoby.miejscePochowania);
		osoba.setWyksztalcenie(daneOsoby.wyksztalcenie);
		osoba.setZawodyWykonywane(daneOsoby.zawodyWykonywane);
		osoba.setOpis(daneOsoby.opis);
		osoba.setMiejsceZamieszkania(daneOsoby.miejsceZamieszkania);

		if (daneOsoby.rodzice!=null) {
			Long idZwiazkuRodzicow = daneOsoby.rodzice.id;
			Zwiazek zwiazekRodzicow = DatabaseLib.getZwiazek(idZwiazkuRodzicow);
			osoba.setZwiazekRodzicow(zwiazekRodzicow);
		}
		
		DatabaseLib.save(osoba);
	}
	
	public static void zapiszDanePoEdycjiZwiazku(DaneEdycjiZwiazkuStruct daneZwiazku) {
		System.out.println("[DatabaseDelegate][zapiszDanePoEdycjiZwiazku]");
		Zwiazek zwiazek = DatabaseLib.getZwiazek(daneZwiazku.id);
		
		if (daneZwiazku.mezczyzna!=null) {
			Long idMezczyzny = daneZwiazku.mezczyzna.id;
			Osoba mezczyzna = DatabaseLib.getOsoba(idMezczyzny);
			zwiazek.setMezczyzna(mezczyzna);
		}
		if (daneZwiazku.kobieta!=null) {
			Long idKobiety = daneZwiazku.kobieta.id;
			Osoba kobieta = DatabaseLib.getOsoba(idKobiety);
			zwiazek.setKobieta(kobieta);
		}
		
		zwiazek.setDataPoznania(daneZwiazku.dataPoznania);
		zwiazek.setMiejscePoznania(daneZwiazku.miejscePoznania);
		zwiazek.setDataSlubu(daneZwiazku.dataSlubu);
		zwiazek.setMiejsceSlubu(daneZwiazku.miejsceSlubu);
		zwiazek.setDataRozstania(daneZwiazku.dataRozstania);
		zwiazek.setMiejsceRozstania(daneZwiazku.miejsceRozstania);
		zwiazek.setDataRozwodu(daneZwiazku.dataRozwodu);
		zwiazek.setMiejsceRozwodu(daneZwiazku.miejsceRozwodu);
		zwiazek.setOpis(daneZwiazku.opis);
		
		DatabaseLib.save(zwiazek);
	}

	public static void zapiszDanePoEdycjiKlanu(DaneEdycjiKlanuStruct daneKlanu) {
		System.out.println("[DatabaseDelegate][zapiszDanePoEdycjiKlanu]");
		Klan klan = DatabaseLib.getKlan(daneKlanu.id);
		
		klan.setNazwa(daneKlanu.nazwa);
		klan.setOpis(daneKlanu.opis);
		
		if (daneKlanu.korzen!=null) {
			Long idKorzenia = daneKlanu.korzen.id;
			Osoba osoba = DatabaseLib.getOsoba(idKorzenia);
			klan.setKorzen(osoba);
		}

		DatabaseLib.save(klan);
	}

	public static void zapiszNowaOsobe(DaneEdycjiOsobyStruct daneOsoby) {
		System.out.println("[DatabaseDelegate][zapiszNowaOsobe]");
		Osoba osoba = new Osoba();
		
		osoba.setPlec(daneOsoby.plec);
		osoba.setImiona(daneOsoby.imiona);
		osoba.setNazwisko(daneOsoby.nazwisko);
		osoba.setDataUrodzenia(daneOsoby.dataUrodzenia);
		osoba.setMiejsceUrodzenia(daneOsoby.miejsceUrodzenia);
		osoba.setDataSmierci(daneOsoby.dataSmierci);
		osoba.setMiejsceSmierci(daneOsoby.miejsceSmierci);
		osoba.setDataPochowania(daneOsoby.dataPochowania);
		osoba.setMiejscePochowania(daneOsoby.miejscePochowania);
		osoba.setWyksztalcenie(daneOsoby.wyksztalcenie);
		osoba.setZawodyWykonywane(daneOsoby.zawodyWykonywane);

		if (daneOsoby.rodzice!=null) {
			Long idZwiazkuRodzicow = daneOsoby.rodzice.id;
			Zwiazek zwiazekRodzicow = DatabaseLib.getZwiazek(idZwiazkuRodzicow);
			osoba.setZwiazekRodzicow(zwiazekRodzicow);
		}
		
		DatabaseLib.save(osoba);
	}
	
	public static void zapiszNowyZwiazek(DaneEdycjiZwiazkuStruct daneZwiazku) {
		System.out.println("[DatabaseDelegate][zapiszNowyZwiazek]");
		Zwiazek zwiazek = new Zwiazek();
		
		if (daneZwiazku.mezczyzna!=null) {
			Long idMezczyzny = daneZwiazku.mezczyzna.id;
			Osoba mezczyzna = DatabaseLib.getOsoba(idMezczyzny);
			zwiazek.setMezczyzna(mezczyzna);
		}
		if (daneZwiazku.kobieta!=null) {
			Long idKobiety = daneZwiazku.kobieta.id;
			Osoba kobieta = DatabaseLib.getOsoba(idKobiety);
			zwiazek.setKobieta(kobieta);
		}
		
		zwiazek.setDataPoznania(daneZwiazku.dataPoznania);
		zwiazek.setMiejscePoznania(daneZwiazku.miejscePoznania);
		zwiazek.setDataSlubu(daneZwiazku.dataSlubu);
		zwiazek.setMiejsceSlubu(daneZwiazku.miejsceSlubu);
		zwiazek.setDataRozstania(daneZwiazku.dataRozstania);
		zwiazek.setMiejsceRozstania(daneZwiazku.miejsceRozstania);
		zwiazek.setDataRozwodu(daneZwiazku.dataRozwodu);
		zwiazek.setMiejsceRozwodu(daneZwiazku.miejsceRozwodu);
		zwiazek.setOpis(daneZwiazku.opis);
		
		DatabaseLib.save(zwiazek);
	}
	
	public static void zapiszNowyKlan(DaneEdycjiKlanuStruct daneKlanu) {
		System.out.println("[DatabaseDelegate][zapiszNowyKlan]");
		Klan klan = new Klan();
		
		klan.setNazwa(daneKlanu.nazwa);
		klan.setOpis(daneKlanu.opis);
		
		if (daneKlanu.korzen!=null) {
			Long idKorzenia = daneKlanu.korzen.id;
			Osoba osoba = DatabaseLib.getOsoba(idKorzenia);
			klan.setKorzen(osoba);
		}

		DatabaseLib.save(klan);
	}
	
	public static List<ZwiazekStruct> getZwiazki() {
		System.out.println("[DatabaseDelegate][getZwiazki]");
		Collection<Zwiazek> zwiazki = DatabaseLib.getZwiazki(null, null);
		List<ZwiazekStruct> rezultat = new ArrayList<ZwiazekStruct>();
		if (zwiazki!=null && zwiazki.size()>0) {
			for (Zwiazek zwiazek : zwiazki) {
				rezultat.add(toStruct(zwiazek));
			}
		}
		return rezultat;
	}
	
	public static List<OsobaStruct> getMezczyzni() {
		System.out.println("[DatabaseDelegate][getMezczyzni]");
		Collection<Osoba> osoby = DatabaseLib.getMezczyzni();
		return toStruct(osoby);
	}
	
	public static List<OsobaStruct> getKobiety() {
		System.out.println("[DatabaseDelegate][getKobiety]");
		Collection<Osoba> osoby = DatabaseLib.getKobiety();
		return toStruct(osoby);
	}
	
	public static List<OsobaStruct> getOsoby() {
		System.out.println("[DatabaseDelegate][getOsoby]");
		Collection<Osoba> osoby = DatabaseLib.getOsoby();
		return toStruct(osoby);
	}
	
	private static ZwiazekStruct toStruct(Zwiazek zwiazek) {
		ZwiazekStruct struct = new ZwiazekStruct();
		struct.id = zwiazek.getId();
		struct.mezczyzna=zwiazek.getMezczyzna().toString();
		struct.kobieta = zwiazek.getKobieta().toString();
		return struct;
	}
	
	private static OsobaStruct toStruct(Osoba osoba) {
		OsobaStruct struct = new OsobaStruct(osoba.getId(), osoba.toString());
		return struct;
	}
	
	private static List<OsobaStruct> toStruct(Collection<Osoba> osoby) {
		List<OsobaStruct> rezultat = new ArrayList<OsobaStruct>();
		if (osoby!=null && osoby.size()>0) {
			for (Osoba osoba : osoby) {
				rezultat.add(toStruct(osoba));
			}
		}
		return rezultat;
	}
	
	public static void usunOsobe(Long idOsoby) {
		Osoba osoba = DatabaseLib.getOsoba(idOsoby);
		DatabaseLib.delete(osoba);
	}
	
	public static void usunZwiazek(Long idZwiazku) {
		Zwiazek zwiazek = DatabaseLib.getZwiazek(idZwiazku);
		DatabaseLib.delete(zwiazek);
	}
	
	public static void usunKlan(Long idKlanu) {
		Klan klan = DatabaseLib.getKlan(idKlanu);
		DatabaseLib.delete(klan);
	}
}