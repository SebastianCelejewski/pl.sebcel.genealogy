package pl.sebcel.genealogy.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import pl.sebcel.genealogy.dto.DaneEdycjiDokumentu;
import pl.sebcel.genealogy.dto.DaneEdycjiKlanuStruct;
import pl.sebcel.genealogy.dto.DaneEdycjiOsoby;
import pl.sebcel.genealogy.dto.DaneEdycjiZwiazkuStruct;
import pl.sebcel.genealogy.dto.DrzewoOsobaStruct;
import pl.sebcel.genealogy.dto.DrzewoRodzinaStruct;
import pl.sebcel.genealogy.dto.ElementListyDokumentowStruct;
import pl.sebcel.genealogy.dto.ElementListyKlanowStruct;
import pl.sebcel.genealogy.dto.ElementListyOsobStruct;
import pl.sebcel.genealogy.dto.ElementListyZwiazkowStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.dto.RodzinaStruct;
import pl.sebcel.genealogy.dto.ZwiazekStruct;
import pl.sebcel.genealogy.entity.Dokument;
import pl.sebcel.genealogy.entity.Klan;
import pl.sebcel.genealogy.entity.Osoba;
import pl.sebcel.genealogy.entity.Zwiazek;

public class DatabaseDelegate {

    public static List<ElementListyOsobStruct> getListaOsob() {
        System.out.println("[DatabaseDelegate][getListaOsob]");
        Collection<Osoba> osoby = DatabaseLib.getPeople();
        List<ElementListyOsobStruct> listaOsob = new ArrayList<ElementListyOsobStruct>();
        if (osoby != null && osoby.size() > 0) {
            for (Osoba osoba : osoby) {
                ElementListyOsobStruct elementListy = new ElementListyOsobStruct();
                elementListy.id = osoba.getId();
                elementListy.nazwa = osoba.toString();
                elementListy.opis = DatabaseUtil.getBirthInfo(osoba) + " " + DatabaseUtil.getDeathInfo(osoba);
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
        Collection<Osoba> osoby = DatabaseLib.getPeople();
        if (osoby != null && osoby.size() > 0) {
            for (Osoba osoba : osoby) {
                xmlData.append(getXMLOsoba(osoba));
            }
        }
        xmlData.append("</osoby>\n");
        return xmlData.toString();
    }

    public static String getXMLListaDokumentow() {
        System.out.println("[DatabaseDelegate][getXMLListaDokumentow]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlData.append("<dokumenty>\n");
        Collection<Dokument> dokumenty = DatabaseLib.getDocuments();
        if (dokumenty != null && dokumenty.size() > 0) {
            for (Dokument dokument : dokumenty) {
                xmlData.append(getXMLDokument(dokument));
            }
        }
        xmlData.append("</dokumenty>\n");
        return xmlData.toString();
    }

    public static String getTXTListaDokumentow() {
        System.out.println("[DatabaseDelegate][getTXTListaDokumentow]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("id;tytul;symbol;opis\n");
        Collection<Dokument> dokumenty = DatabaseLib.getDocuments();
        if (dokumenty != null && dokumenty.size() > 0) {
            for (Dokument dokument : dokumenty) {
                xmlData.append(getTXTDokument(dokument));
            }
        }
        return xmlData.toString();
    }

    public static String getTxtListaOsob() {
        System.out.println("[DatabaseDelegate][getTXTListaOsob]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("id;imiona;nazwisko;data-urodzenia;miejsce-urodzenia;data-smierci;miejsce-smierci;data-pochowania;miejsce-pochowania;zamieszkanie;wyksztalcenie;zawody;rodzice;zwiazki\n");
        Collection<Osoba> osoby = DatabaseLib.getPeople();
        if (osoby != null && osoby.size() > 0) {
            for (Osoba osoba : osoby) {
                xmlData.append(getTXTOsoba(osoba) + "\n");
            }
        }
        return xmlData.toString();
    }

    public static String getXMLDokument(Dokument dokument) {
        System.out.println("[DatabaseDelegate][getXMLDokument]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<dokument>\n");
        xmlData.append("  " + getXMLField("id", dokument.getId()));
        xmlData.append("  " + getXMLField("tytul", dokument.getTytul()));
        xmlData.append("  " + getXMLField("symbol", dokument.getSymbol()));
        xmlData.append("  " + getXMLField("opis", dokument.getOpis()));
        xmlData.append("</dokument>\n");
        return xmlData.toString();
    }

    public static String getTXTDokument(Dokument dokument) {
        System.out.println("[DatabaseDelegate][getTXTDokument]");
        return dokument.getId() + ";" + dokument.getTytul() + ";" + dokument.getSymbol() + ";" + dokument.getOpis() + "\n";
    }

    public static String getXMLOsoba(Osoba osoba) {
        System.out.println("[DatabaseDelegate][getXMLOsoba]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<osoba>\n");
        xmlData.append("  " + getXMLField("id", osoba.getId()));
        xmlData.append("  " + getXMLField("imiona", osoba.getImiona()));
        xmlData.append("  " + getXMLField("nazwisko", osoba.getNazwisko()));
        if (osoba.getZwiazekRodzicow() != null) {
            xmlData.append("  " + getXMLField("rodzice", osoba.getZwiazekRodzicow().getId()));
        }

        xmlData.append("  <dane-urodzenia>\n");
        xmlData.append("    " + getXMLField("data", osoba.getDataUrodzenia()));
        xmlData.append("    " + getXMLField("miejsce", osoba.getMiejsceUrodzenia()));
        xmlData.append("  </dane-urodzenia>\n");

        xmlData.append("  <dane-smierci>\n");
        xmlData.append("    " + getXMLField("data", osoba.getDataSmierci()));
        xmlData.append("    " + getXMLField("miejsce", osoba.getMiejsceSmierci()));
        xmlData.append("  </dane-smierci>\n");

        xmlData.append("  <dane-pochowania>\n");
        xmlData.append("    " + getXMLField("data", osoba.getDataPochowania()));
        xmlData.append("    " + getXMLField("miejsce", osoba.getMiejscePochowania()));
        xmlData.append("  </dane-pochowania>\n");

        xmlData.append("  " + getXMLField("zamieszkanie", osoba.getMiejsceZamieszkania()));
        xmlData.append("  " + getXMLField("wyksztalcenie", osoba.getWyksztalcenie()));
        xmlData.append("  " + getXMLField("zawody", osoba.getZawodyWykonywane()));

        xmlData.append("  <zwiazki>\n");
        Collection<Zwiazek> zwiazki = null;
        if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
            zwiazki = osoba.getZwiazkiMezowskie();
        } else {
            zwiazki = osoba.getZwiazkiZonowskie();
        }

        if (zwiazki != null && zwiazki.size() > 0) {
            for (Zwiazek zwiazek : zwiazki) {
                xmlData.append("    " + getXMLField("zwiazek-id", zwiazek.getId()));
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

        if (zwiazki != null && zwiazki.size() > 0) {
            for (Zwiazek zwiazek : zwiazki) {
                Long idMalzonka = null;
                if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
                    idMalzonka = zwiazek.getKobieta().getId();
                } else {
                    idMalzonka = zwiazek.getMezczyzna().getId();
                }
                String zwiazekStr = "" + zwiazek.getId() + ":(" + idMalzonka;

                Collection<Osoba> dzieci = zwiazek.getDzieci();
                if (dzieci != null && dzieci.size() > 0) {
                    String dzieciStr = "";
                    for (Osoba dziecko : dzieci) {
                        if (dzieciStr.length() > 0) {
                            dzieciStr += ", ";
                        }
                        dzieciStr += dziecko.getId();
                    }
                    zwiazekStr += "; " + dzieciStr;
                }
                zwiazekStr += ")";

                if (zwiazkiStr.length() > 0) {
                    zwiazkiStr.append("; ");
                }
                zwiazkiStr.append(zwiazekStr);
            }
        }
        xmlData.append(zwiazkiStr);

        return xmlData.toString();
    }

    private static String getXMLField(String name, Object value) {
        if (value != null) {
            return "<" + name + ">" + value.toString() + "</" + name + ">\n";
        } else {
            return "";
        }
    }

    private static String getTXTField(Object value) {
        if (value == null)
            return ";";
        if (value instanceof String)
            return "\"" + value + "\";";
        return value.toString() + ";";
    }

    public static List<ElementListyZwiazkowStruct> getListaZwiazkow() {
        System.out.println("[DatabaseDelegate][getListaZwiazkow]");
        Collection<Zwiazek> zwiazki = DatabaseLib.getRelationships(null, null);
        List<ElementListyZwiazkowStruct> listaZwiazkow = new ArrayList<ElementListyZwiazkowStruct>();
        if (zwiazki != null && zwiazki.size() > 0) {
            for (Zwiazek zwiazek : zwiazki) {
                ElementListyZwiazkowStruct elementListy = new ElementListyZwiazkowStruct();
                elementListy.id = zwiazek.getId();
                elementListy.mezczyzna = zwiazek.getMezczyzna().toString();
                elementListy.kobieta = zwiazek.getKobieta().toString();
                elementListy.opis = DatabaseUtil.getMarriageInfo(zwiazek) + " " + DatabaseUtil.getDivorceInfo(zwiazek);
                listaZwiazkow.add(elementListy);
            }
        }
        return listaZwiazkow;
    }

    public static List<ElementListyKlanowStruct> getListaKlanow() {
        System.out.println("[DatabaseDelegate][getListaKlanow]");
        Collection<Klan> klany = DatabaseLib.getClans();
        List<ElementListyKlanowStruct> listaKlanow = new ArrayList<ElementListyKlanowStruct>();
        if (klany != null && klany.size() > 0) {
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

    public static List<ElementListyDokumentowStruct> getListaDokumentow() {
        System.out.println("[DatabaseDelegate][getListaDokumentow]");
        Collection<Dokument> dokumenty = DatabaseLib.getDocuments();
        List<ElementListyDokumentowStruct> listaDokumentow = new ArrayList<ElementListyDokumentowStruct>();
        if (dokumenty != null && dokumenty.size() > 0) {
            for (Dokument dokument : dokumenty) {
                ElementListyDokumentowStruct elementListy = new ElementListyDokumentowStruct();
                elementListy.setId(dokument.getId());
                elementListy.setTytul(dokument.getTytul());
                elementListy.setSymbol(dokument.getSymbol());
                listaDokumentow.add(elementListy);
            }
        }
        return listaDokumentow;
    }

    public static DrzewoOsobaStruct getDaneOsoby(Long idOsoby) {
        System.out.println("[DatabaseDelegate][getDaneOsoby]");
        Osoba osoba = DatabaseLib.getPerson(idOsoby);
        DrzewoOsobaStruct osobaStruct = new DrzewoOsobaStruct();
        osobaStruct.id = osoba.getId();
        osobaStruct.nazwa = osoba.getImiona() + " " + osoba.getNazwisko();
        osobaStruct.daneUrodzenia = DatabaseUtil.getBirthInfo(osoba);
        osobaStruct.daneSmierci = DatabaseUtil.getDeathInfo(osoba);
        osobaStruct.danePochowania = "";
        osobaStruct.daneZamieszkania = DatabaseUtil.getResidenceInfo(osoba);
        osobaStruct.daneZawodu = DatabaseUtil.getOccupationInfo(osoba);

        Collection<Zwiazek> zwiazki = null;
        if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
            zwiazki = osoba.getZwiazkiMezowskie();
        } else {
            zwiazki = osoba.getZwiazkiZonowskie();
        }

        if (zwiazki != null && zwiazki.size() > 0) {
            List<DrzewoRodzinaStruct> idRodzin = new ArrayList<DrzewoRodzinaStruct>();
            for (Zwiazek zwiazek : zwiazki) {
                DrzewoRodzinaStruct rodzina = new DrzewoRodzinaStruct();
                rodzina.idZwiazku = zwiazek.getId();
                if (osoba.getPlec().equalsIgnoreCase("mezczyzna")) {
                    rodzina.idMalzonka = zwiazek.getKobieta().getId();
                } else {
                    rodzina.idMalzonka = zwiazek.getMezczyzna().getId();
                }
                rodzina.daneSpotkania = DatabaseUtil.getMeetInfo(zwiazek);
                rodzina.daneSlubu = DatabaseUtil.getMarriageInfo(zwiazek);
                rodzina.daneRozstania = DatabaseUtil.getSeparationInfo(zwiazek);
                rodzina.daneRozwodu = DatabaseUtil.getDivorceInfo(zwiazek);
                List<Osoba> dzieci = sortujPoDacieUrodzenia(zwiazek.getDzieci());
                if (dzieci != null && dzieci.size() > 0) {
                    List<Long> idDzieci = new ArrayList<Long>();
                    for (Osoba dziecko : dzieci) {
                        idDzieci.add(dziecko.getId());
                    }
                    rodzina.idDzieci = idDzieci;
                }
                idRodzin.add(rodzina);
                zwiazek = null;
            }
            osobaStruct.rodziny = idRodzin;
        }
        return osobaStruct;
    }

    private static List<Osoba> sortujPoDacieUrodzenia(Collection<Osoba> osoby) {
        List<Osoba> rezultat = new ArrayList<Osoba>();
        rezultat.addAll(osoby);
        Collections.sort(rezultat, new Comparator<Osoba>() {

            public int compare(Osoba o1, Osoba o2) {
                String ds1 = o1.getDataUrodzenia();
                String ds2 = o2.getDataUrodzenia();
                if (ds1 == null && ds2 == null) {
                    return 0;
                }
                if (ds1 == null) {
                    return 1;
                }
                if (ds2 == null) {
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

    private static String data(String input) {
        try {
            return outputDF.format(inputDF.parse(input));
        } catch (ParseException ex) {
            return input;
        }
    }

    public static DaneEdycjiOsoby getDaneDoEdycjiOsoby(Long idOsoby) {
        System.out.println("[DatabaseDelegate][getDaneDoEdycjiOsoby]");
        Osoba osoba = DatabaseLib.getPerson(idOsoby);
        DaneEdycjiOsoby daneOsoby = new DaneEdycjiOsoby();

        daneOsoby.setId(osoba.getId());
        daneOsoby.setImiona(osoba.getImiona());
        daneOsoby.setNazwisko(osoba.getNazwisko());
        daneOsoby.setDataUrodzenia(osoba.getDataUrodzenia());
        daneOsoby.setMiejsceUrodzenia(osoba.getMiejsceUrodzenia());
        daneOsoby.setDataSmierci(osoba.getDataSmierci());
        daneOsoby.setMiejsceSmierci(osoba.getMiejsceSmierci());
        daneOsoby.setDataPochowania(osoba.getDataPochowania());
        daneOsoby.setMiejscePochowania(osoba.getMiejscePochowania());
        daneOsoby.setPlec(osoba.getPlec());
        daneOsoby.setWyksztalcenie(osoba.getWyksztalcenie());
        daneOsoby.setZawodyWykonywane(osoba.getZawodyWykonywane());
        daneOsoby.setOpis(osoba.getOpis());
        daneOsoby.setRodzice(null);
        daneOsoby.setMiejsceZamieszkania(osoba.getMiejsceZamieszkania());
        Zwiazek rodzice = osoba.getZwiazekRodzicow();
        if (rodzice != null) {
            daneOsoby.setRodzice(toStruct(rodzice));
        }

        daneOsoby.setRodziny(new Vector<RodzinaStruct>());
        Collection<Zwiazek> rodziny = null;
        if (daneOsoby.getPlec() != null && daneOsoby.getPlec().equalsIgnoreCase("Mezczyzna")) {
            rodziny = osoba.getZwiazkiMezowskie();
        } else {
            rodziny = osoba.getZwiazkiZonowskie();
        }
        if (rodziny != null) {
            for (Zwiazek zwiazek : rodziny) {
                Osoba malzonek = null;
                if (daneOsoby.getPlec() != null && daneOsoby.getPlec().equalsIgnoreCase("Mezczyzna")) {
                    malzonek = zwiazek.getKobieta();
                } else {
                    malzonek = zwiazek.getMezczyzna();
                }
                RodzinaStruct rodzina = new RodzinaStruct();
                if (malzonek != null)
                    rodzina.malzonek = new ReferenceListElement(malzonek.getId(), malzonek.getImiona() + " " + malzonek.getNazwisko());
                rodzina.idDzieci = new Vector<ReferenceListElement>();
                if (zwiazek.getDzieci() != null && zwiazek.getDzieci().size() > 0) {
                    for (Osoba dziecko : zwiazek.getDzieci()) {
                        rodzina.idDzieci.add(new ReferenceListElement(dziecko.getId(), dziecko.getImiona() + " " + dziecko.getNazwisko()));
                    }
                }
                daneOsoby.getRodziny().add(rodzina);
            }
        }

        Set<ReferenceListElement> wybraneDokumenty = new HashSet<ReferenceListElement>();
        if (osoba.getDokumenty() != null && osoba.getDokumenty().size() > 0) {
            for (Dokument dokument : osoba.getDokumenty()) {
                wybraneDokumenty.add(dokumentToReferencedListElement(dokument));
            }
        }

        daneOsoby.setDokumenty(wybraneDokumenty);
        return daneOsoby;
    }

    private static ReferenceListElement dokumentToReferencedListElement(Dokument dokument) {
        ReferenceListElement elementListy = new ReferenceListElement();
        elementListy.setId(dokument.getId());
        elementListy.setDescription(dokument.getTytul() + " (" + dokument.getSymbol() + ")");
        return elementListy;
    }

    public static DaneEdycjiZwiazkuStruct getDaneDoEdycjiZwiazku(Long idZwiazku) {
        System.out.println("[DatabaseDelegate][getDaneDoEdycjiZwiazku]");
        Zwiazek zwiazek = DatabaseLib.getRelationship(idZwiazku);

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
        Klan klan = DatabaseLib.getClan(idKlanu);

        DaneEdycjiKlanuStruct daneKlanu = new DaneEdycjiKlanuStruct();
        daneKlanu.id = klan.getId();
        daneKlanu.nazwa = klan.getNazwa();
        daneKlanu.opis = klan.getOpis();
        daneKlanu.korzen = toStruct(klan.getKorzen());

        return daneKlanu;
    }

    public static DaneEdycjiDokumentu getDaneDoEdycjiDokumentu(Long idDokumentu) {
        System.out.println("[DatabaseDelegate][getDaneDoEdycjiDokumentu]");
        Dokument dokument = DatabaseLib.getDocument(idDokumentu);

        DaneEdycjiDokumentu daneDokumentu = new DaneEdycjiDokumentu();
        daneDokumentu.setId(dokument.getId());
        daneDokumentu.setTytul(dokument.getTytul());
        daneDokumentu.setSymbol(dokument.getSymbol());
        daneDokumentu.setOpis(dokument.getOpis());

        daneDokumentu.setOsoby(osobyToReferenceListElements(dokument.getOsoby()));

        return daneDokumentu;
    }

    private static Set<ReferenceListElement> osobyToReferenceListElements(Set<Osoba> osoby) {
        Set<ReferenceListElement> result = new HashSet<ReferenceListElement>();
        for (Osoba osoba : osoby) {
            result.add(osobaToReferenceListElement(osoba));
        }

        return result;
    }

    private static ReferenceListElement osobaToReferenceListElement(Osoba osoba) {
        ReferenceListElement result = new ReferenceListElement();
        result.setId(osoba.getId());
        result.setDescription(osoba.getImiona() + " " + osoba.getNazwisko() + " (" + osoba.getId() + ")");

        return result;
    }

    public static void zapiszDanePoEdycjiOsoby(DaneEdycjiOsoby daneOsoby) {
        System.out.println("[DatabaseDelegate][zapiszDanePoEdycjiOsoby]");
        Osoba osoba = DatabaseLib.getPerson(daneOsoby.getId());

        osoba.setPlec(daneOsoby.getPlec());
        osoba.setImiona(daneOsoby.getImiona());
        osoba.setNazwisko(daneOsoby.getNazwisko());
        osoba.setDataUrodzenia(daneOsoby.getDataUrodzenia());
        osoba.setMiejsceUrodzenia(daneOsoby.getMiejsceUrodzenia());
        osoba.setDataSmierci(daneOsoby.getDataSmierci());
        osoba.setMiejsceSmierci(daneOsoby.getMiejsceSmierci());
        osoba.setDataPochowania(daneOsoby.getDataPochowania());
        osoba.setMiejscePochowania(daneOsoby.getMiejscePochowania());
        osoba.setWyksztalcenie(daneOsoby.getWyksztalcenie());
        osoba.setZawodyWykonywane(daneOsoby.getZawodyWykonywane());
        osoba.setOpis(daneOsoby.getOpis());
        osoba.setMiejsceZamieszkania(daneOsoby.getMiejsceZamieszkania());

        if (daneOsoby.getRodzice() != null) {
            Long idZwiazkuRodzicow = daneOsoby.getRodzice().id;
            Zwiazek zwiazekRodzicow = DatabaseLib.getRelationship(idZwiazkuRodzicow);
            osoba.setZwiazekRodzicow(zwiazekRodzicow);
        }

        for (Dokument dokument : osoba.getDokumenty()) {
            dokument.getOsoby().remove(osoba);
        }

        osoba.getDokumenty().clear();
        for (ReferenceListElement dokumentElement : daneOsoby.getDokumenty()) {
            Dokument dokument = DatabaseLib.getDocument(dokumentElement.getId());
            osoba.getDokumenty().add(dokument);
            dokument.getOsoby().add(osoba);
        }

        DatabaseLib.save(osoba);
    }

    public static void zapiszDanePoEdycjiZwiazku(DaneEdycjiZwiazkuStruct daneZwiazku) {
        System.out.println("[DatabaseDelegate][zapiszDanePoEdycjiZwiazku]");
        Zwiazek zwiazek = DatabaseLib.getRelationship(daneZwiazku.id);

        if (daneZwiazku.mezczyzna != null) {
            Long idMezczyzny = daneZwiazku.mezczyzna.getId();
            Osoba mezczyzna = DatabaseLib.getPerson(idMezczyzny);
            zwiazek.setMezczyzna(mezczyzna);
        }
        if (daneZwiazku.kobieta != null) {
            Long idKobiety = daneZwiazku.kobieta.getId();
            Osoba kobieta = DatabaseLib.getPerson(idKobiety);
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
        Klan klan = DatabaseLib.getClan(daneKlanu.id);

        klan.setNazwa(daneKlanu.nazwa);
        klan.setOpis(daneKlanu.opis);

        if (daneKlanu.korzen != null) {
            Long idKorzenia = daneKlanu.korzen.getId();
            Osoba osoba = DatabaseLib.getPerson(idKorzenia);
            klan.setKorzen(osoba);
        }

        DatabaseLib.save(klan);
    }

    public static void zapiszDanePoEdycjiDokumentu(DaneEdycjiDokumentu daneDokumentu) {
        System.out.println("[DatabaseDelegate][zapiszDanePoEdycjiDokumentu]");
        Dokument dokument = DatabaseLib.getDocument(daneDokumentu.getId());

        dokument.setTytul(daneDokumentu.getTytul());
        dokument.setSymbol(daneDokumentu.getSymbol());
        dokument.setOpis(daneDokumentu.getOpis());

        for (Osoba osoba : dokument.getOsoby()) {
            osoba.getDokumenty().remove(dokument);
        }

        dokument.getOsoby().clear();
        for (ReferenceListElement osobaElement : daneDokumentu.getOsoby()) {
            Osoba osoba = DatabaseLib.getPerson(osobaElement.getId());
            dokument.getOsoby().add(osoba);
            osoba.getDokumenty().add(dokument);
        }

        DatabaseLib.save(dokument);
    }

    public static void zapiszNowaOsobe(DaneEdycjiOsoby daneOsoby) {
        System.out.println("[DatabaseDelegate][zapiszNowaOsobe]");
        Osoba osoba = new Osoba();

        osoba.setPlec(daneOsoby.getPlec());
        osoba.setImiona(daneOsoby.getImiona());
        osoba.setNazwisko(daneOsoby.getNazwisko());
        osoba.setDataUrodzenia(daneOsoby.getDataUrodzenia());
        osoba.setMiejsceUrodzenia(daneOsoby.getMiejsceUrodzenia());
        osoba.setDataSmierci(daneOsoby.getDataSmierci());
        osoba.setMiejsceSmierci(daneOsoby.getMiejsceSmierci());
        osoba.setDataPochowania(daneOsoby.getDataPochowania());
        osoba.setMiejscePochowania(daneOsoby.getMiejscePochowania());
        osoba.setWyksztalcenie(daneOsoby.getWyksztalcenie());
        osoba.setZawodyWykonywane(daneOsoby.getZawodyWykonywane());

        if (daneOsoby.getRodzice() != null) {
            Long idZwiazkuRodzicow = daneOsoby.getRodzice().id;
            Zwiazek zwiazekRodzicow = DatabaseLib.getRelationship(idZwiazkuRodzicow);
            osoba.setZwiazekRodzicow(zwiazekRodzicow);
        }

        if (daneOsoby.getDokumenty() != null) {
            Set<Dokument> dokumenty = new HashSet<Dokument>();
            for (ReferenceListElement dokumentElement : daneOsoby.getDokumenty()) {
                Dokument dokument = DatabaseLib.getDocument(dokumentElement.getId());
                dokumenty.add(dokument);
                dokument.getOsoby().add(osoba);
            }
            osoba.setDokumenty(dokumenty);
        }

        DatabaseLib.save(osoba);
    }

    public static void zapiszNowyZwiazek(DaneEdycjiZwiazkuStruct daneZwiazku) {
        System.out.println("[DatabaseDelegate][zapiszNowyZwiazek]");
        Zwiazek zwiazek = new Zwiazek();

        if (daneZwiazku.mezczyzna != null) {
            Long idMezczyzny = daneZwiazku.mezczyzna.getId();
            Osoba mezczyzna = DatabaseLib.getPerson(idMezczyzny);
            zwiazek.setMezczyzna(mezczyzna);
        }
        if (daneZwiazku.kobieta != null) {
            Long idKobiety = daneZwiazku.kobieta.getId();
            Osoba kobieta = DatabaseLib.getPerson(idKobiety);
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

        if (daneKlanu.korzen != null) {
            Long idKorzenia = daneKlanu.korzen.getId();
            Osoba osoba = DatabaseLib.getPerson(idKorzenia);
            klan.setKorzen(osoba);
        }

        DatabaseLib.save(klan);
    }

    public static void zapiszNowyDokument(DaneEdycjiDokumentu daneDokumentu) {
        System.out.println("[DatabaseDelegate][zapiszNowyDokument]");
        Dokument dokument = new Dokument();

        dokument.setTytul(daneDokumentu.getTytul());
        dokument.setSymbol(daneDokumentu.getSymbol());
        dokument.setOpis(daneDokumentu.getOpis());

        Set<Osoba> osoby = new HashSet<Osoba>();
        if (daneDokumentu.getOsoby() != null) {
            for (ReferenceListElement osobaElement : daneDokumentu.getOsoby()) {
                Osoba osoba = DatabaseLib.getPerson(osobaElement.getId());
                osoby.add(osoba);
                osoba.getDokumenty().add(dokument);
            }
        }
        dokument.setOsoby(osoby);
        DatabaseLib.save(dokument);
    }

    public static List<ZwiazekStruct> getZwiazki() {
        System.out.println("[DatabaseDelegate][getZwiazki]");
        Collection<Zwiazek> zwiazki = DatabaseLib.getRelationships(null, null);
        List<ZwiazekStruct> rezultat = new ArrayList<ZwiazekStruct>();
        if (zwiazki != null && zwiazki.size() > 0) {
            for (Zwiazek zwiazek : zwiazki) {
                rezultat.add(toStruct(zwiazek));
            }
        }
        return rezultat;
    }

    public static List<ReferenceListElement> getMezczyzni() {
        System.out.println("[DatabaseDelegate][getMezczyzni]");
        Collection<Osoba> osoby = DatabaseLib.getMales();
        return toStruct(osoby);
    }

    public static List<ReferenceListElement> getKobiety() {
        System.out.println("[DatabaseDelegate][getKobiety]");
        Collection<Osoba> osoby = DatabaseLib.getFemales();
        return toStruct(osoby);
    }

    public static List<ReferenceListElement> getOsoby() {
        System.out.println("[DatabaseDelegate][getOsoby]");
        Collection<Osoba> osoby = DatabaseLib.getPeople();
        return toStruct(osoby);
    }

    public static List<ReferenceListElement> getDokumenty() {
        System.out.println("[DatabaseDelegate][getDokumenty]");
        Collection<Dokument> dokumenty = DatabaseLib.getDocuments();
        return dokumentsToReferencedListElements(dokumenty);
    }

    private static ZwiazekStruct toStruct(Zwiazek zwiazek) {
        ZwiazekStruct struct = new ZwiazekStruct();
        struct.id = zwiazek.getId();
        struct.mezczyzna = zwiazek.getMezczyzna().toString();
        struct.kobieta = zwiazek.getKobieta().toString();
        return struct;
    }

    private static ReferenceListElement toStruct(Osoba osoba) {
        ReferenceListElement struct = new ReferenceListElement();
        struct.setId(osoba.getId());
        struct.setDescription(osoba.getImiona() + " " + osoba.getNazwisko());
        return struct;
    }

    private static List<ReferenceListElement> toStruct(Collection<Osoba> osoby) {
        List<ReferenceListElement> rezultat = new ArrayList<ReferenceListElement>();
        if (osoby != null && osoby.size() > 0) {
            for (Osoba osoba : osoby) {
                rezultat.add(toStruct(osoba));
            }
        }
        return rezultat;
    }

    private static List<ReferenceListElement> dokumentsToReferencedListElements(Collection<Dokument> dokumenty) {
        List<ReferenceListElement> rezultat = new ArrayList<ReferenceListElement>();
        if (dokumenty != null && dokumenty.size() > 0) {
            for (Dokument dokument : dokumenty) {
                rezultat.add(dokumentToReferencedListElement(dokument));
            }
        }
        return rezultat;
    }

    public static void usunOsobe(Long idOsoby) {
        Osoba osoba = DatabaseLib.getPerson(idOsoby);
        DatabaseLib.delete(osoba);
    }

    public static void usunZwiazek(Long idZwiazku) {
        Zwiazek zwiazek = DatabaseLib.getRelationship(idZwiazku);
        DatabaseLib.delete(zwiazek);
    }

    public static void usunKlan(Long idKlanu) {
        Klan klan = DatabaseLib.getClan(idKlanu);
        DatabaseLib.delete(klan);
    }

    public static void usunDokument(Long idDokumentu) {
        Dokument dokument = DatabaseLib.getDocument(idDokumentu);
        DatabaseLib.delete(dokument);
    }
}