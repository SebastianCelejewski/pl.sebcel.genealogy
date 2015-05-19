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

    private final static DateFormat inputDF = new SimpleDateFormat("dd-MM-yyyy");
    private final static DateFormat outputDF = new SimpleDateFormat("yyyy-MM-dd");

    public static List<ElementListyOsobStruct> getPeopleList() {
        System.out.println("[DatabaseDelegate][getPeopleList]");
        Collection<Osoba> people = DatabaseLib.getPeople();
        List<ElementListyOsobStruct> peopleList = new ArrayList<ElementListyOsobStruct>();
        if (people != null && people.size() > 0) {
            for (Osoba person : people) {
                ElementListyOsobStruct listElement = new ElementListyOsobStruct();
                listElement.id = person.getId();
                listElement.nazwa = person.toString();
                listElement.opis = DatabaseUtil.getBirthInfo(person) + " " + DatabaseUtil.getDeathInfo(person);
                peopleList.add(listElement);
            }
        }
        return peopleList;
    }

    public static String getPeopleListXML() {
        System.out.println("[DatabaseDelegate][getPeopleListXML]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlData.append("<people>\n");
        Collection<Osoba> people = DatabaseLib.getPeople();
        if (people != null && people.size() > 0) {
            for (Osoba person : people) {
                xmlData.append(getPersonXML(person));
            }
        }
        xmlData.append("</people>\n");
        return xmlData.toString();
    }

    public static String getDocumentListXML() {
        System.out.println("[DatabaseDelegate][getDocumentListXML]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlData.append("<documents>\n");
        Collection<Dokument> documents = DatabaseLib.getDocuments();
        if (documents != null && documents.size() > 0) {
            for (Dokument document : documents) {
                xmlData.append(getDocumentXML(document));
            }
        }
        xmlData.append("</documents>\n");
        return xmlData.toString();
    }

    public static String getDocumentListTXT() {
        System.out.println("[DatabaseDelegate][getDocumentListTXT]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("id;title;symbol;description\n");
        Collection<Dokument> documents = DatabaseLib.getDocuments();
        if (documents != null && documents.size() > 0) {
            for (Dokument document : documents) {
                xmlData.append(getDocumentTXT(document));
            }
        }
        return xmlData.toString();
    }

    public static String getPeopleListTXT() {
        System.out.println("[DatabaseDelegate][getPeopleListTXT]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("id;first-names;last-name;birth-date;birth-place;death-date;death-place;burial-date;burial-place;residence;occupation;professions;parents;relationships\n");
        Collection<Osoba> people = DatabaseLib.getPeople();
        if (people != null && people.size() > 0) {
            for (Osoba person : people) {
                xmlData.append(getPersonTXT(person) + "\n");
            }
        }
        return xmlData.toString();
    }

    public static String getDocumentXML(Dokument document) {
        System.out.println("[DatabaseDelegate][getDocumentXML]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<document>\n");
        xmlData.append("  " + getXMLField("id", document.getId()));
        xmlData.append("  " + getXMLField("title", document.getTytul()));
        xmlData.append("  " + getXMLField("symbol", document.getSymbol()));
        xmlData.append("  " + getXMLField("description", document.getOpis()));
        xmlData.append("</document>\n");
        return xmlData.toString();
    }

    public static String getDocumentTXT(Dokument document) {
        System.out.println("[DatabaseDelegate][getDocumentTXT]");
        return document.getId() + ";" + document.getTytul() + ";" + document.getSymbol() + ";" + document.getOpis() + "\n";
    }

    public static String getPersonXML(Osoba osoba) {
        System.out.println("[DatabaseDelegate][getPersonXML]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<person>\n");
        xmlData.append("  " + getXMLField("id", osoba.getId()));
        xmlData.append("  " + getXMLField("first-names", osoba.getImiona()));
        xmlData.append("  " + getXMLField("last-name", osoba.getNazwisko()));
        if (osoba.getZwiazekRodzicow() != null) {
            xmlData.append("  " + getXMLField("parents", osoba.getZwiazekRodzicow().getId()));
        }

        xmlData.append("  <birth>\n");
        xmlData.append("    " + getXMLField("date", osoba.getDataUrodzenia()));
        xmlData.append("    " + getXMLField("place", osoba.getMiejsceUrodzenia()));
        xmlData.append("  </birth>\n");

        xmlData.append("  <death>\n");
        xmlData.append("    " + getXMLField("date", osoba.getDataSmierci()));
        xmlData.append("    " + getXMLField("place", osoba.getMiejsceSmierci()));
        xmlData.append("  </death>\n");

        xmlData.append("  <burial>\n");
        xmlData.append("    " + getXMLField("date", osoba.getDataPochowania()));
        xmlData.append("    " + getXMLField("place", osoba.getMiejscePochowania()));
        xmlData.append("  </burial>\n");

        xmlData.append("  " + getXMLField("residence", osoba.getMiejsceZamieszkania()));
        xmlData.append("  " + getXMLField("occupation", osoba.getWyksztalcenie()));
        xmlData.append("  " + getXMLField("proffesions", osoba.getZawodyWykonywane()));

        xmlData.append("  <relationships>\n");
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

        xmlData.append("  </relationships>\n");
        xmlData.append("</person>\n");
        return xmlData.toString();
    }

    public static String getPersonTXT(Osoba osoba) {
        System.out.println("[DatabaseDelegate][getPersonTXT]");
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

    public static List<ElementListyZwiazkowStruct> getRelationshipsList() {
        System.out.println("[DatabaseDelegate][getRelationshipsList]");
        Collection<Zwiazek> relationships = DatabaseLib.getRelationships(null, null);
        List<ElementListyZwiazkowStruct> relationshipsList = new ArrayList<ElementListyZwiazkowStruct>();
        if (relationships != null && relationships.size() > 0) {
            for (Zwiazek relationship : relationships) {
                ElementListyZwiazkowStruct listElement = new ElementListyZwiazkowStruct();
                listElement.id = relationship.getId();
                listElement.mezczyzna = relationship.getMezczyzna().toString();
                listElement.kobieta = relationship.getKobieta().toString();
                listElement.opis = DatabaseUtil.getMarriageInfo(relationship) + " " + DatabaseUtil.getDivorceInfo(relationship);
                relationshipsList.add(listElement);
            }
        }
        return relationshipsList;
    }

    public static List<ElementListyKlanowStruct> getClansList() {
        System.out.println("[DatabaseDelegate][getClansList]");
        Collection<Klan> clans = DatabaseLib.getClans();
        List<ElementListyKlanowStruct> clansList = new ArrayList<ElementListyKlanowStruct>();
        if (clans != null && clans.size() > 0) {
            for (Klan clan : clans) {
                ElementListyKlanowStruct listElement = new ElementListyKlanowStruct();
                listElement.id = clan.getId();
                listElement.nazwa = clan.getNazwa();
                listElement.opis = clan.getOpis();
                listElement.korzen = clan.getKorzen().toString();
                clansList.add(listElement);
            }
        }
        return clansList;
    }

    public static List<ElementListyDokumentowStruct> getDocumentsList() {
        System.out.println("[DatabaseDelegate][getDocumentsList]");
        Collection<Dokument> documents = DatabaseLib.getDocuments();
        List<ElementListyDokumentowStruct> documentsList = new ArrayList<ElementListyDokumentowStruct>();
        if (documents != null && documents.size() > 0) {
            for (Dokument document : documents) {
                ElementListyDokumentowStruct listElement = new ElementListyDokumentowStruct();
                listElement.setId(document.getId());
                listElement.setTytul(document.getTytul());
                listElement.setSymbol(document.getSymbol());
                documentsList.add(listElement);
            }
        }
        return documentsList;
    }

    public static DrzewoOsobaStruct getPersonDataForPedigree(Long personId) {
        System.out.println("[DatabaseDelegate][getPersonDataForPedigree]");
        Osoba person = DatabaseLib.getPerson(personId);
        DrzewoOsobaStruct result = new DrzewoOsobaStruct();
        result.id = person.getId();
        result.nazwa = person.getImiona() + " " + person.getNazwisko();
        result.daneUrodzenia = DatabaseUtil.getBirthInfo(person);
        result.daneSmierci = DatabaseUtil.getDeathInfo(person);
        result.danePochowania = "";
        result.daneZamieszkania = DatabaseUtil.getResidenceInfo(person);
        result.daneZawodu = DatabaseUtil.getOccupationInfo(person);

        Collection<Zwiazek> relationships = null;
        if (person.getPlec().equalsIgnoreCase("mezczyzna")) {
            relationships = person.getZwiazkiMezowskie();
        } else {
            relationships = person.getZwiazkiZonowskie();
        }

        if (relationships != null && relationships.size() > 0) {
            List<DrzewoRodzinaStruct> familyIds = new ArrayList<DrzewoRodzinaStruct>();
            for (Zwiazek relationship : relationships) {
                DrzewoRodzinaStruct family = new DrzewoRodzinaStruct();
                family.idZwiazku = relationship.getId();
                if (person.getPlec().equalsIgnoreCase("mezczyzna")) {
                    family.idMalzonka = relationship.getKobieta().getId();
                } else {
                    family.idMalzonka = relationship.getMezczyzna().getId();
                }
                family.daneSpotkania = DatabaseUtil.getMeetInfo(relationship);
                family.daneSlubu = DatabaseUtil.getMarriageInfo(relationship);
                family.daneRozstania = DatabaseUtil.getSeparationInfo(relationship);
                family.daneRozwodu = DatabaseUtil.getDivorceInfo(relationship);
                List<Osoba> children = sortByBirthDate(relationship.getDzieci());
                if (children != null && children.size() > 0) {
                    List<Long> childrenIds = new ArrayList<Long>();
                    for (Osoba child : children) {
                        childrenIds.add(child.getId());
                    }
                    family.idDzieci = childrenIds;
                }
                familyIds.add(family);
                relationship = null;
            }
            result.rodziny = familyIds;
        }
        return result;
    }

    private static List<Osoba> sortByBirthDate(Collection<Osoba> people) {
        List<Osoba> result = new ArrayList<Osoba>();
        result.addAll(people);
        Collections.sort(result, new Comparator<Osoba>() {

            public int compare(Osoba person1, Osoba person2) {
                String ds1 = person1.getDataUrodzenia();
                String ds2 = person2.getDataUrodzenia();
                if (ds1 == null && ds2 == null) {
                    return 0;
                }
                if (ds1 == null) {
                    return 1;
                }
                if (ds2 == null) {
                    return -1;
                }

                String d1 = reformatDate(ds1);
                String d2 = reformatDate(ds2);
                return d1.compareTo(d2);
            }
        });
        return result;
    }

    public static DaneEdycjiOsoby getPersonEditData(Long personId) {
        System.out.println("[DatabaseDelegate][getPersonEditData]");
        Osoba person = DatabaseLib.getPerson(personId);
        DaneEdycjiOsoby personEditData = new DaneEdycjiOsoby();

        personEditData.setId(person.getId());
        personEditData.setImiona(person.getImiona());
        personEditData.setNazwisko(person.getNazwisko());
        personEditData.setDataUrodzenia(person.getDataUrodzenia());
        personEditData.setMiejsceUrodzenia(person.getMiejsceUrodzenia());
        personEditData.setDataSmierci(person.getDataSmierci());
        personEditData.setMiejsceSmierci(person.getMiejsceSmierci());
        personEditData.setDataPochowania(person.getDataPochowania());
        personEditData.setMiejscePochowania(person.getMiejscePochowania());
        personEditData.setPlec(person.getPlec());
        personEditData.setWyksztalcenie(person.getWyksztalcenie());
        personEditData.setZawodyWykonywane(person.getZawodyWykonywane());
        personEditData.setOpis(person.getOpis());
        personEditData.setRodzice(null);
        personEditData.setMiejsceZamieszkania(person.getMiejsceZamieszkania());
        Zwiazek parents = person.getZwiazekRodzicow();
        if (parents != null) {
            personEditData.setRodzice(toStruct(parents));
        }

        personEditData.setRodziny(new Vector<RodzinaStruct>());
        Collection<Zwiazek> families = null;
        if (personEditData.getPlec() != null && personEditData.getPlec().equalsIgnoreCase("Mezczyzna")) {
            families = person.getZwiazkiMezowskie();
        } else {
            families = person.getZwiazkiZonowskie();
        }
        if (families != null) {
            for (Zwiazek relationship : families) {
                Osoba spouse = null;
                if (personEditData.getPlec() != null && personEditData.getPlec().equalsIgnoreCase("Mezczyzna")) {
                    spouse = relationship.getKobieta();
                } else {
                    spouse = relationship.getMezczyzna();
                }
                RodzinaStruct family = new RodzinaStruct();
                if (spouse != null)
                    family.malzonek = new ReferenceListElement(spouse.getId(), spouse.getImiona() + " " + spouse.getNazwisko());
                family.idDzieci = new Vector<ReferenceListElement>();
                if (relationship.getDzieci() != null && relationship.getDzieci().size() > 0) {
                    for (Osoba child : relationship.getDzieci()) {
                        family.idDzieci.add(new ReferenceListElement(child.getId(), child.getImiona() + " " + child.getNazwisko()));
                    }
                }
                personEditData.getRodziny().add(family);
            }
        }

        Set<ReferenceListElement> documents = new HashSet<ReferenceListElement>();
        if (person.getDokumenty() != null && person.getDokumenty().size() > 0) {
            for (Dokument document : person.getDokumenty()) {
                documents.add(dokumentToReferencedListElement(document));
            }
        }

        personEditData.setDokumenty(documents);
        return personEditData;
    }

    public static DaneEdycjiZwiazkuStruct getRelationshipEditData(Long relationshipId) {
        System.out.println("[DatabaseDelegate][getRelationshipEditData]");
        Zwiazek relationship = DatabaseLib.getRelationship(relationshipId);

        DaneEdycjiZwiazkuStruct relationshipEditData = new DaneEdycjiZwiazkuStruct();
        relationshipEditData.id = relationship.getId();
        relationshipEditData.mezczyzna = toStruct(relationship.getMezczyzna());
        relationshipEditData.kobieta = toStruct(relationship.getKobieta());
        relationshipEditData.dataPoznania = relationship.getDataPoznania();
        relationshipEditData.miejscePoznania = relationship.getMiejscePoznania();
        relationshipEditData.dataSlubu = relationship.getDataSlubu();
        relationshipEditData.miejsceSlubu = relationship.getMiejsceSlubu();
        relationshipEditData.dataRozstania = relationship.getDataRozstania();
        relationshipEditData.miejsceRozstania = relationship.getMiejsceRozstania();
        relationshipEditData.dataRozwodu = relationship.getDataRozwodu();
        relationshipEditData.miejsceRozwodu = relationship.getMiejsceRozwodu();
        relationshipEditData.opis = relationship.getOpis();

        return relationshipEditData;
    }

    public static DaneEdycjiKlanuStruct getClanEditData(Long clanId) {
        System.out.println("[DatabaseDelegate][getClanEditData]");
        Klan clan = DatabaseLib.getClan(clanId);

        DaneEdycjiKlanuStruct clanEditData = new DaneEdycjiKlanuStruct();
        clanEditData.id = clan.getId();
        clanEditData.nazwa = clan.getNazwa();
        clanEditData.opis = clan.getOpis();
        clanEditData.korzen = toStruct(clan.getKorzen());

        return clanEditData;
    }

    public static DaneEdycjiDokumentu getDocumentEditData(Long documentId) {
        System.out.println("[DatabaseDelegate][getDocumentEditData]");
        Dokument document = DatabaseLib.getDocument(documentId);

        DaneEdycjiDokumentu documentEditData = new DaneEdycjiDokumentu();
        documentEditData.setId(document.getId());
        documentEditData.setTytul(document.getTytul());
        documentEditData.setSymbol(document.getSymbol());
        documentEditData.setOpis(document.getOpis());

        documentEditData.setOsoby(peopleToReferenceListElements(document.getOsoby()));

        return documentEditData;
    }

    public static void saveEditedPerson(DaneEdycjiOsoby personData) {
        System.out.println("[DatabaseDelegate][saveEditedPerson]");
        Osoba person = DatabaseLib.getPerson(personData.getId());

        person.setPlec(personData.getPlec());
        person.setImiona(personData.getImiona());
        person.setNazwisko(personData.getNazwisko());
        person.setDataUrodzenia(personData.getDataUrodzenia());
        person.setMiejsceUrodzenia(personData.getMiejsceUrodzenia());
        person.setDataSmierci(personData.getDataSmierci());
        person.setMiejsceSmierci(personData.getMiejsceSmierci());
        person.setDataPochowania(personData.getDataPochowania());
        person.setMiejscePochowania(personData.getMiejscePochowania());
        person.setWyksztalcenie(personData.getWyksztalcenie());
        person.setZawodyWykonywane(personData.getZawodyWykonywane());
        person.setOpis(personData.getOpis());
        person.setMiejsceZamieszkania(personData.getMiejsceZamieszkania());

        if (personData.getRodzice() != null) {
            Long parentsRelationshipId = personData.getRodzice().id;
            Zwiazek parentsRelationship = DatabaseLib.getRelationship(parentsRelationshipId);
            person.setZwiazekRodzicow(parentsRelationship);
        }

        for (Dokument document : person.getDokumenty()) {
            document.getOsoby().remove(person);
        }

        person.getDokumenty().clear();
        for (ReferenceListElement documentElement : personData.getDokumenty()) {
            Dokument document = DatabaseLib.getDocument(documentElement.getId());
            person.getDokumenty().add(document);
            document.getOsoby().add(person);
        }

        DatabaseLib.save(person);
    }

    public static void saveEditedRelationship(DaneEdycjiZwiazkuStruct relationshipData) {
        System.out.println("[DatabaseDelegate][saveEditedRelationship]");
        Zwiazek relationship = DatabaseLib.getRelationship(relationshipData.id);

        if (relationshipData.mezczyzna != null) {
            Long maleId = relationshipData.mezczyzna.getId();
            Osoba male = DatabaseLib.getPerson(maleId);
            relationship.setMezczyzna(male);
        }
        if (relationshipData.kobieta != null) {
            Long femaleId = relationshipData.kobieta.getId();
            Osoba female = DatabaseLib.getPerson(femaleId);
            relationship.setKobieta(female);
        }

        relationship.setDataPoznania(relationshipData.dataPoznania);
        relationship.setMiejscePoznania(relationshipData.miejscePoznania);
        relationship.setDataSlubu(relationshipData.dataSlubu);
        relationship.setMiejsceSlubu(relationshipData.miejsceSlubu);
        relationship.setDataRozstania(relationshipData.dataRozstania);
        relationship.setMiejsceRozstania(relationshipData.miejsceRozstania);
        relationship.setDataRozwodu(relationshipData.dataRozwodu);
        relationship.setMiejsceRozwodu(relationshipData.miejsceRozwodu);
        relationship.setOpis(relationshipData.opis);

        DatabaseLib.save(relationship);
    }

    public static void saveEditedClan(DaneEdycjiKlanuStruct clanData) {
        System.out.println("[DatabaseDelegate][saveClanData]");
        Klan clan = DatabaseLib.getClan(clanData.id);

        clan.setNazwa(clanData.nazwa);
        clan.setOpis(clanData.opis);

        if (clanData.korzen != null) {
            Long clanRootId = clanData.korzen.getId();
            Osoba person = DatabaseLib.getPerson(clanRootId);
            clan.setKorzen(person);
        }

        DatabaseLib.save(clan);
    }

    public static void saveEditedDocument(DaneEdycjiDokumentu documentData) {
        System.out.println("[DatabaseDelegate][saveEditedDocument]");
        Dokument document = DatabaseLib.getDocument(documentData.getId());

        document.setTytul(documentData.getTytul());
        document.setSymbol(documentData.getSymbol());
        document.setOpis(documentData.getOpis());

        for (Osoba person : document.getOsoby()) {
            person.getDokumenty().remove(document);
        }

        document.getOsoby().clear();
        for (ReferenceListElement personElement : documentData.getOsoby()) {
            Osoba person = DatabaseLib.getPerson(personElement.getId());
            document.getOsoby().add(person);
            person.getDokumenty().add(document);
        }

        DatabaseLib.save(document);
    }

    public static void saveNewPerson(DaneEdycjiOsoby personData) {
        System.out.println("[DatabaseDelegate][saveNewPerson]");
        Osoba person = new Osoba();

        person.setPlec(personData.getPlec());
        person.setImiona(personData.getImiona());
        person.setNazwisko(personData.getNazwisko());
        person.setDataUrodzenia(personData.getDataUrodzenia());
        person.setMiejsceUrodzenia(personData.getMiejsceUrodzenia());
        person.setDataSmierci(personData.getDataSmierci());
        person.setMiejsceSmierci(personData.getMiejsceSmierci());
        person.setDataPochowania(personData.getDataPochowania());
        person.setMiejscePochowania(personData.getMiejscePochowania());
        person.setWyksztalcenie(personData.getWyksztalcenie());
        person.setZawodyWykonywane(personData.getZawodyWykonywane());

        if (personData.getRodzice() != null) {
            Long parentsRelationshipId = personData.getRodzice().id;
            Zwiazek parentsRelationship = DatabaseLib.getRelationship(parentsRelationshipId);
            person.setZwiazekRodzicow(parentsRelationship);
        }

        if (personData.getDokumenty() != null) {
            Set<Dokument> documents = new HashSet<Dokument>();
            for (ReferenceListElement dokumentElement : personData.getDokumenty()) {
                Dokument document = DatabaseLib.getDocument(dokumentElement.getId());
                documents.add(document);
                document.getOsoby().add(person);
            }
            person.setDokumenty(documents);
        }

        DatabaseLib.save(person);
    }

    public static void saveNewRelationship(DaneEdycjiZwiazkuStruct relationshipData) {
        System.out.println("[DatabaseDelegate][saveNewRelationship]");
        Zwiazek relationship = new Zwiazek();

        if (relationshipData.mezczyzna != null) {
            Long maleId = relationshipData.mezczyzna.getId();
            Osoba male = DatabaseLib.getPerson(maleId);
            relationship.setMezczyzna(male);
        }
        if (relationshipData.kobieta != null) {
            Long femaleId = relationshipData.kobieta.getId();
            Osoba female = DatabaseLib.getPerson(femaleId);
            relationship.setKobieta(female);
        }

        relationship.setDataPoznania(relationshipData.dataPoznania);
        relationship.setMiejscePoznania(relationshipData.miejscePoznania);
        relationship.setDataSlubu(relationshipData.dataSlubu);
        relationship.setMiejsceSlubu(relationshipData.miejsceSlubu);
        relationship.setDataRozstania(relationshipData.dataRozstania);
        relationship.setMiejsceRozstania(relationshipData.miejsceRozstania);
        relationship.setDataRozwodu(relationshipData.dataRozwodu);
        relationship.setMiejsceRozwodu(relationshipData.miejsceRozwodu);
        relationship.setOpis(relationshipData.opis);

        DatabaseLib.save(relationship);
    }

    public static void saveNewClan(DaneEdycjiKlanuStruct clanData) {
        System.out.println("[DatabaseDelegate][saveNewClan]");
        Klan clan = new Klan();

        clan.setNazwa(clanData.nazwa);
        clan.setOpis(clanData.opis);

        if (clanData.korzen != null) {
            Long clanRootId = clanData.korzen.getId();
            Osoba person = DatabaseLib.getPerson(clanRootId);
            clan.setKorzen(person);
        }

        DatabaseLib.save(clan);
    }

    public static void saveNewDocument(DaneEdycjiDokumentu documentData) {
        System.out.println("[DatabaseDelegate][saveNewDocument]");
        Dokument document = new Dokument();

        document.setTytul(documentData.getTytul());
        document.setSymbol(documentData.getSymbol());
        document.setOpis(documentData.getOpis());

        Set<Osoba> people = new HashSet<Osoba>();
        if (documentData.getOsoby() != null) {
            for (ReferenceListElement personElement : documentData.getOsoby()) {
                Osoba person = DatabaseLib.getPerson(personElement.getId());
                people.add(person);
                person.getDokumenty().add(document);
            }
        }
        document.setOsoby(people);
        DatabaseLib.save(document);
    }

    public static List<ZwiazekStruct> getRelationships() {
        System.out.println("[DatabaseDelegate][getRelationships]");
        Collection<Zwiazek> relationships = DatabaseLib.getRelationships(null, null);
        List<ZwiazekStruct> result = new ArrayList<ZwiazekStruct>();
        if (relationships != null && relationships.size() > 0) {
            for (Zwiazek relationship : relationships) {
                result.add(toStruct(relationship));
            }
        }
        return result;
    }

    public static List<ReferenceListElement> getMales() {
        System.out.println("[DatabaseDelegate][getMales]");
        Collection<Osoba> males = DatabaseLib.getMales();
        return toStruct(males);
    }

    public static List<ReferenceListElement> getFemales() {
        System.out.println("[DatabaseDelegate][getFemales]");
        Collection<Osoba> females = DatabaseLib.getFemales();
        return toStruct(females);
    }

    public static List<ReferenceListElement> getPeople() {
        System.out.println("[DatabaseDelegate][getPeople]");
        Collection<Osoba> people = DatabaseLib.getPeople();
        return toStruct(people);
    }

    public static List<ReferenceListElement> getDocuments() {
        System.out.println("[DatabaseDelegate][getDocuments]");
        Collection<Dokument> documents = DatabaseLib.getDocuments();
        return dokumentsToReferencedListElements(documents);
    }

    private static ZwiazekStruct toStruct(Zwiazek relationship) {
        ZwiazekStruct struct = new ZwiazekStruct();
        struct.id = relationship.getId();
        struct.mezczyzna = relationship.getMezczyzna().toString();
        struct.kobieta = relationship.getKobieta().toString();
        return struct;
    }

    private static ReferenceListElement toStruct(Osoba person) {
        ReferenceListElement struct = new ReferenceListElement();
        struct.setId(person.getId());
        struct.setDescription(person.getImiona() + " " + person.getNazwisko());
        return struct;
    }

    private static List<ReferenceListElement> toStruct(Collection<Osoba> people) {
        List<ReferenceListElement> result = new ArrayList<ReferenceListElement>();
        if (people != null && people.size() > 0) {
            for (Osoba person : people) {
                result.add(toStruct(person));
            }
        }
        return result;
    }

    private static List<ReferenceListElement> dokumentsToReferencedListElements(Collection<Dokument> documents) {
        List<ReferenceListElement> result = new ArrayList<ReferenceListElement>();
        if (documents != null && documents.size() > 0) {
            for (Dokument document : documents) {
                result.add(dokumentToReferencedListElement(document));
            }
        }
        return result;
    }

    public static void deletePersonOsobe(Long personId) {
        Osoba person = DatabaseLib.getPerson(personId);
        DatabaseLib.delete(person);
    }

    public static void deleteRelationship(Long relationshipId) {
        Zwiazek relationship = DatabaseLib.getRelationship(relationshipId);
        DatabaseLib.delete(relationship);
    }

    public static void deleteClan(Long clanId) {
        Klan clan = DatabaseLib.getClan(clanId);
        DatabaseLib.delete(clan);
    }

    public static void deleteDocument(Long documentId) {
        Dokument document = DatabaseLib.getDocument(documentId);
        DatabaseLib.delete(document);
    }

    private static String getXMLField(String name, Object value) {
        if (value != null) {
            return "<" + name + ">" + value.toString() + "</" + name + ">\n";
        } else {
            return "";
        }
    }

    private static String getTXTField(Object value) {
        if (value == null) {
            return ";";
        }
        if (value instanceof String) {
            return "\"" + value + "\";";
        }
        return value.toString() + ";";
    }

    private static String reformatDate(String input) {
        try {
            return outputDF.format(inputDF.parse(input));
        } catch (ParseException ex) {
            return input;
        }
    }

    private static Set<ReferenceListElement> peopleToReferenceListElements(Set<Osoba> people) {
        Set<ReferenceListElement> result = new HashSet<ReferenceListElement>();
        for (Osoba person : people) {
            result.add(personToReferenceListElement(person));
        }

        return result;
    }

    private static ReferenceListElement dokumentToReferencedListElement(Dokument document) {
        ReferenceListElement result = new ReferenceListElement();
        result.setId(document.getId());
        result.setDescription(document.getTytul() + " (" + document.getSymbol() + ")");
        return result;
    }

    private static ReferenceListElement personToReferenceListElement(Osoba person) {
        ReferenceListElement result = new ReferenceListElement();
        result.setId(person.getId());
        result.setDescription(person.getImiona() + " " + person.getNazwisko() + " (" + person.getId() + ")");

        return result;
    }
}