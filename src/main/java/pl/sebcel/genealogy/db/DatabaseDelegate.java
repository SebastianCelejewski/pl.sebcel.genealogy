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

import pl.sebcel.genealogy.dto.DocumentEditData;
import pl.sebcel.genealogy.dto.ClanEditData;
import pl.sebcel.genealogy.dto.PersonEditData;
import pl.sebcel.genealogy.dto.RelationshipEditData;
import pl.sebcel.genealogy.dto.DrzewoOsobaStruct;
import pl.sebcel.genealogy.dto.DrzewoRodzinaStruct;
import pl.sebcel.genealogy.dto.ElementListyDokumentowStruct;
import pl.sebcel.genealogy.dto.ElementListyKlanowStruct;
import pl.sebcel.genealogy.dto.ElementListyOsobStruct;
import pl.sebcel.genealogy.dto.ElementListyZwiazkowStruct;
import pl.sebcel.genealogy.dto.ReferenceListElement;
import pl.sebcel.genealogy.dto.RodzinaStruct;
import pl.sebcel.genealogy.entity.Document;
import pl.sebcel.genealogy.entity.Clan;
import pl.sebcel.genealogy.entity.Person;
import pl.sebcel.genealogy.entity.Relationship;

public class DatabaseDelegate {

    private final static DateFormat inputDF = new SimpleDateFormat("dd-MM-yyyy");
    private final static DateFormat outputDF = new SimpleDateFormat("yyyy-MM-dd");

    public static List<ElementListyOsobStruct> getPeopleList() {
        System.out.println("[DatabaseDelegate][getPeopleList]");
        Collection<Person> people = DatabaseLib.getPeople();
        List<ElementListyOsobStruct> peopleList = new ArrayList<ElementListyOsobStruct>();
        if (people != null && people.size() > 0) {
            for (Person person : people) {
                ElementListyOsobStruct listElement = new ElementListyOsobStruct();
                listElement.id = person.getId();
                listElement.nazwa = person.toString();
                listElement.description = DatabaseUtil.getBirthInfo(person) + " " + DatabaseUtil.getDeathInfo(person);
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
        Collection<Person> people = DatabaseLib.getPeople();
        if (people != null && people.size() > 0) {
            for (Person person : people) {
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
        Collection<Document> documents = DatabaseLib.getDocuments();
        if (documents != null && documents.size() > 0) {
            for (Document document : documents) {
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
        Collection<Document> documents = DatabaseLib.getDocuments();
        if (documents != null && documents.size() > 0) {
            for (Document document : documents) {
                xmlData.append(getDocumentTXT(document));
            }
        }
        return xmlData.toString();
    }

    public static String getPeopleListTXT() {
        System.out.println("[DatabaseDelegate][getPeopleListTXT]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("id;first-names;last-name;birth-date;birth-place;death-date;death-place;burial-date;burial-place;residence;occupation;professions;parents;relationships\n");
        Collection<Person> people = DatabaseLib.getPeople();
        if (people != null && people.size() > 0) {
            for (Person person : people) {
                xmlData.append(getPersonTXT(person) + "\n");
            }
        }
        return xmlData.toString();
    }

    public static String getDocumentXML(Document document) {
        System.out.println("[DatabaseDelegate][getDocumentXML]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<document>\n");
        xmlData.append("  " + getXMLField("id", document.getId()));
        xmlData.append("  " + getXMLField("title", document.getTitle()));
        xmlData.append("  " + getXMLField("symbol", document.getSymbol()));
        xmlData.append("  " + getXMLField("description", document.getDescription()));
        xmlData.append("</document>\n");
        return xmlData.toString();
    }

    public static String getDocumentTXT(Document document) {
        System.out.println("[DatabaseDelegate][getDocumentTXT]");
        return document.getId() + ";" + document.getTitle() + ";" + document.getSymbol() + ";" + document.getDescription() + "\n";
    }

    public static String getPersonXML(Person osoba) {
        System.out.println("[DatabaseDelegate][getPersonXML]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<person>\n");
        xmlData.append("  " + getXMLField("id", osoba.getId()));
        xmlData.append("  " + getXMLField("first-names", osoba.getNames()));
        xmlData.append("  " + getXMLField("last-name", osoba.getSurname()));
        if (osoba.getParents() != null) {
            xmlData.append("  " + getXMLField("parents", osoba.getParents().getId()));
        }

        xmlData.append("  <birth>\n");
        xmlData.append("    " + getXMLField("date", osoba.getBirthDate()));
        xmlData.append("    " + getXMLField("place", osoba.getBirthPlace()));
        xmlData.append("  </birth>\n");

        xmlData.append("  <death>\n");
        xmlData.append("    " + getXMLField("date", osoba.getDeathDate()));
        xmlData.append("    " + getXMLField("place", osoba.getDeathPlace()));
        xmlData.append("  </death>\n");

        xmlData.append("  <burial>\n");
        xmlData.append("    " + getXMLField("date", osoba.getBurialDate()));
        xmlData.append("    " + getXMLField("place", osoba.getBurialPlace()));
        xmlData.append("  </burial>\n");

        xmlData.append("  " + getXMLField("residence", osoba.getResidence()));
        xmlData.append("  " + getXMLField("occupation", osoba.getEducation()));
        xmlData.append("  " + getXMLField("proffesions", osoba.getOccupation()));

        xmlData.append("  <relationships>\n");
        Collection<Relationship> zwiazki = null;
        if (osoba.getSex().equalsIgnoreCase("male")) {
            zwiazki = osoba.getZwiazkiMezowskie();
        } else {
            zwiazki = osoba.getZwiazkiZonowskie();
        }

        if (zwiazki != null && zwiazki.size() > 0) {
            for (Relationship zwiazek : zwiazki) {
                xmlData.append("    " + getXMLField("zwiazek-id", zwiazek.getId()));
            }
        }

        xmlData.append("  </relationships>\n");
        xmlData.append("</person>\n");
        return xmlData.toString();
    }

    public static String getPersonTXT(Person osoba) {
        System.out.println("[DatabaseDelegate][getPersonTXT]");
        StringBuffer xmlData = new StringBuffer();
        xmlData.append(getTXTField(osoba.getId()));
        xmlData.append(getTXTField(osoba.getNames()));
        xmlData.append(getTXTField(osoba.getSurname()));
        xmlData.append(getTXTField(osoba.getBirthDate()));
        xmlData.append(getTXTField(osoba.getBirthPlace()));
        xmlData.append(getTXTField(osoba.getDeathDate()));
        xmlData.append(getTXTField(osoba.getDeathPlace()));
        xmlData.append(getTXTField(osoba.getBurialDate()));
        xmlData.append(getTXTField(osoba.getBurialPlace()));
        xmlData.append(getTXTField(osoba.getResidence()));
        xmlData.append(getTXTField(osoba.getEducation()));
        xmlData.append(getTXTField(osoba.getOccupation()));

        Collection<Relationship> zwiazki = null;
        StringBuffer zwiazkiStr = new StringBuffer();
        if (osoba.getSex().equalsIgnoreCase("male")) {
            zwiazki = osoba.getZwiazkiMezowskie();
        } else {
            zwiazki = osoba.getZwiazkiZonowskie();
        }

        if (zwiazki != null && zwiazki.size() > 0) {
            for (Relationship zwiazek : zwiazki) {
                Long idMalzonka = null;
                if (osoba.getSex().equalsIgnoreCase("male")) {
                    idMalzonka = zwiazek.getFemale().getId();
                } else {
                    idMalzonka = zwiazek.getMale().getId();
                }
                String zwiazekStr = "" + zwiazek.getId() + ":(" + idMalzonka;

                Collection<Person> dzieci = zwiazek.getChildren();
                if (dzieci != null && dzieci.size() > 0) {
                    String dzieciStr = "";
                    for (Person dziecko : dzieci) {
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
        Collection<Relationship> relationships = DatabaseLib.getRelationships(null, null);
        List<ElementListyZwiazkowStruct> relationshipsList = new ArrayList<ElementListyZwiazkowStruct>();
        if (relationships != null && relationships.size() > 0) {
            for (Relationship relationship : relationships) {
                ElementListyZwiazkowStruct listElement = new ElementListyZwiazkowStruct();
                listElement.id = relationship.getId();
                listElement.male = relationship.getMale().toString();
                listElement.female = relationship.getFemale().toString();
                listElement.description = DatabaseUtil.getMarriageInfo(relationship) + " " + DatabaseUtil.getDivorceInfo(relationship);
                relationshipsList.add(listElement);
            }
        }
        return relationshipsList;
    }

    public static List<ElementListyKlanowStruct> getClansList() {
        System.out.println("[DatabaseDelegate][getClansList]");
        Collection<Clan> clans = DatabaseLib.getClans();
        List<ElementListyKlanowStruct> clansList = new ArrayList<ElementListyKlanowStruct>();
        if (clans != null && clans.size() > 0) {
            for (Clan clan : clans) {
                ElementListyKlanowStruct listElement = new ElementListyKlanowStruct();
                listElement.id = clan.getId();
                listElement.nazwa = clan.getName();
                listElement.description = clan.getDescription();
                listElement.korzen = clan.getRoot().toString();
                clansList.add(listElement);
            }
        }
        return clansList;
    }

    public static List<ElementListyDokumentowStruct> getDocumentsList() {
        System.out.println("[DatabaseDelegate][getDocumentsList]");
        Collection<Document> documents = DatabaseLib.getDocuments();
        List<ElementListyDokumentowStruct> documentsList = new ArrayList<ElementListyDokumentowStruct>();
        if (documents != null && documents.size() > 0) {
            for (Document document : documents) {
                ElementListyDokumentowStruct listElement = new ElementListyDokumentowStruct();
                listElement.setId(document.getId());
                listElement.setTytul(document.getTitle());
                listElement.setSymbol(document.getSymbol());
                documentsList.add(listElement);
            }
        }
        return documentsList;
    }

    public static DrzewoOsobaStruct getPersonDataForPedigree(Long personId) {
        System.out.println("[DatabaseDelegate][getPersonDataForPedigree]");
        Person person = DatabaseLib.getPerson(personId);
        DrzewoOsobaStruct result = new DrzewoOsobaStruct();
        result.id = person.getId();
        result.nazwa = person.getNames() + " " + person.getSurname();
        result.daneUrodzenia = DatabaseUtil.getBirthInfo(person);
        result.daneSmierci = DatabaseUtil.getDeathInfo(person);
        result.danePochowania = "";
        result.daneZamieszkania = DatabaseUtil.getResidenceInfo(person);
        result.daneZawodu = DatabaseUtil.getOccupationInfo(person);

        Collection<Relationship> relationships = null;
        if (person.getSex().equalsIgnoreCase("male")) {
            relationships = person.getZwiazkiMezowskie();
        } else {
            relationships = person.getZwiazkiZonowskie();
        }

        if (relationships != null && relationships.size() > 0) {
            List<DrzewoRodzinaStruct> familyIds = new ArrayList<DrzewoRodzinaStruct>();
            for (Relationship relationship : relationships) {
                DrzewoRodzinaStruct family = new DrzewoRodzinaStruct();
                family.idZwiazku = relationship.getId();
                if (person.getSex().equalsIgnoreCase("male")) {
                    family.idMalzonka = relationship.getFemale().getId();
                } else {
                    family.idMalzonka = relationship.getMale().getId();
                }
                family.daneSpotkania = DatabaseUtil.getMeetInfo(relationship);
                family.daneSlubu = DatabaseUtil.getMarriageInfo(relationship);
                family.daneRozstania = DatabaseUtil.getSeparationInfo(relationship);
                family.daneRozwodu = DatabaseUtil.getDivorceInfo(relationship);
                List<Person> children = sortByBirthDate(relationship.getChildren());
                if (children != null && children.size() > 0) {
                    List<Long> childrenIds = new ArrayList<Long>();
                    for (Person child : children) {
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

    private static List<Person> sortByBirthDate(Collection<Person> people) {
        List<Person> result = new ArrayList<Person>();
        result.addAll(people);
        Collections.sort(result, new Comparator<Person>() {

            public int compare(Person person1, Person person2) {
                String ds1 = person1.getBirthDate();
                String ds2 = person2.getBirthDate();
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

    public static PersonEditData getPersonEditData(Long personId) {
        System.out.println("[DatabaseDelegate][getPersonEditData]");
        Person person = DatabaseLib.getPerson(personId);
        PersonEditData personEditData = new PersonEditData();

        personEditData.setId(person.getId());
        personEditData.setNames(person.getNames());
        personEditData.setSurname(person.getSurname());
        personEditData.setBirthDate(person.getBirthDate());
        personEditData.setBirthPlace(person.getBirthPlace());
        personEditData.setDeathDate(person.getDeathDate());
        personEditData.setDeathPlace(person.getDeathPlace());
        personEditData.setBurialDate(person.getBurialDate());
        personEditData.setBurialPlace(person.getBurialPlace());
        personEditData.setSex(person.getSex());
        personEditData.setEducation(person.getEducation());
        personEditData.setOccupation(person.getOccupation());
        personEditData.setDescription(person.getDescription());
        personEditData.setParents(null);
        personEditData.setResidence(person.getResidence());
        Relationship parents = person.getParents();
        if (parents != null) {
            personEditData.setParents(relationshipToReferenceListElement(parents));
        }

        personEditData.setFamilies(new Vector<RodzinaStruct>());
        Collection<Relationship> families = null;
        if (personEditData.getSex() != null && personEditData.getSex().equalsIgnoreCase("Male")) {
            families = person.getZwiazkiMezowskie();
        } else {
            families = person.getZwiazkiZonowskie();
        }
        if (families != null) {
            for (Relationship relationship : families) {
                Person spouse = null;
                if (personEditData.getSex() != null && personEditData.getSex().equalsIgnoreCase("Male")) {
                    spouse = relationship.getFemale();
                } else {
                    spouse = relationship.getMale();
                }
                RodzinaStruct family = new RodzinaStruct();
                if (spouse != null)
                    family.malzonek = new ReferenceListElement(spouse.getId(), spouse.getNames() + " " + spouse.getSurname());
                family.idDzieci = new Vector<ReferenceListElement>();
                if (relationship.getChildren() != null && relationship.getChildren().size() > 0) {
                    for (Person child : relationship.getChildren()) {
                        family.idDzieci.add(new ReferenceListElement(child.getId(), child.getNames() + " " + child.getSurname()));
                    }
                }
                personEditData.getFamilies().add(family);
            }
        }

        Set<ReferenceListElement> documents = new HashSet<ReferenceListElement>();
        if (person.getRelatedDocuments() != null && person.getRelatedDocuments().size() > 0) {
            for (Document document : person.getRelatedDocuments()) {
                documents.add(dokumentToReferencedListElement(document));
            }
        }

        personEditData.setDokumenty(documents);
        return personEditData;
    }

    public static RelationshipEditData getRelationshipEditData(Long relationshipId) {
        System.out.println("[DatabaseDelegate][getRelationshipEditData]");
        Relationship relationship = DatabaseLib.getRelationship(relationshipId);

        RelationshipEditData relationshipEditData = new RelationshipEditData();
        relationshipEditData.setId(relationship.getId());
        relationshipEditData.setMale(personToReferenceListElement(relationship.getMale()));
        relationshipEditData.setFemale(personToReferenceListElement(relationship.getFemale()));
        relationshipEditData.setFirstMetDate(relationship.getFirstMetDate());
        relationshipEditData.setFirstMetPlace(relationship.getFirstMetPlace());
        relationshipEditData.setMarriageDate(relationship.getMarriageDate());
        relationshipEditData.setMarriagePlace(relationship.getMarriagePlace());
        relationshipEditData.setSeparationDate(relationship.getSeparationDate());
        relationshipEditData.setSeparationPlace(relationship.getSeparationPlace());
        relationshipEditData.setDivorceDate(relationship.getDivorceDate());
        relationshipEditData.setDivorcePlace(relationship.getDivorcePlace());
        relationshipEditData.setDescription(relationship.getDescription());

        return relationshipEditData;
    }

    public static ClanEditData getClanEditData(Long clanId) {
        System.out.println("[DatabaseDelegate][getClanEditData]");
        Clan clan = DatabaseLib.getClan(clanId);

        ClanEditData clanEditData = new ClanEditData();
        clanEditData.setId(clan.getId());
        clanEditData.setName(clan.getName());
        clanEditData.setDescription(clan.getDescription());
        clanEditData.setRoot(personToReferenceListElement(clan.getRoot()));

        return clanEditData;
    }

    public static DocumentEditData getDocumentEditData(Long documentId) {
        System.out.println("[DatabaseDelegate][getDocumentEditData]");
        Document document = DatabaseLib.getDocument(documentId);

        DocumentEditData documentEditData = new DocumentEditData();
        documentEditData.setId(document.getId());
        documentEditData.setTitle(document.getTitle());
        documentEditData.setSymbol(document.getSymbol());
        documentEditData.setDescription(document.getDescription());

        documentEditData.setRelatedPeople(peopleToReferenceListElements(document.getRelatedPeople()));

        return documentEditData;
    }

    public static void saveEditedPerson(PersonEditData personData) {
        System.out.println("[DatabaseDelegate][saveEditedPerson]");
        Person person = DatabaseLib.getPerson(personData.getId());

        person.setSex(personData.getSex());
        person.setNames(personData.getNames());
        person.setSurname(personData.getSurname());
        person.setBirthDate(personData.getBirthDate());
        person.setBirthPlace(personData.getBirthPlace());
        person.setDeathDate(personData.getDeathDate());
        person.setDeathPlace(personData.getDeathPlace());
        person.setBurialDate(personData.getBurialDate());
        person.setBurialPlace(personData.getBurialPlace());
        person.setEducation(personData.getEducation());
        person.setOccupation(personData.getOccupation());
        person.setDescription(personData.getDescription());
        person.setResidence(personData.getResidence());

        if (personData.getParents() != null) {
            Long parentsRelationshipId = personData.getParents().getId();
            Relationship parentsRelationship = DatabaseLib.getRelationship(parentsRelationshipId);
            person.setParents(parentsRelationship);
        }

        for (Document document : person.getRelatedDocuments()) {
            document.getRelatedPeople().remove(person);
        }

        person.getRelatedDocuments().clear();
        for (ReferenceListElement documentElement : personData.getDokumenty()) {
            Document document = DatabaseLib.getDocument(documentElement.getId());
            person.getRelatedDocuments().add(document);
            document.getRelatedPeople().add(person);
        }

        DatabaseLib.save(person);
    }

    public static void saveEditedRelationship(RelationshipEditData relationshipData) {
        System.out.println("[DatabaseDelegate][saveEditedRelationship]");
        Relationship relationship = DatabaseLib.getRelationship(relationshipData.getId());

        if (relationshipData.getMale() != null) {
            Long maleId = relationshipData.getMale().getId();
            Person male = DatabaseLib.getPerson(maleId);
            relationship.setMale(male);
        }
        if (relationshipData.getFemale() != null) {
            Long femaleId = relationshipData.getFemale().getId();
            Person female = DatabaseLib.getPerson(femaleId);
            relationship.setFemale(female);
        }

        relationship.setFirstMetDate(relationshipData.getFirstMetDate());
        relationship.setFirstMetPlace(relationshipData.getFirstMetPlace());
        relationship.setMarriageDate(relationshipData.getMarriageDate());
        relationship.setMarriagePlace(relationshipData.getMarriagePlace());
        relationship.setSeparationDate(relationshipData.getSeparationDate());
        relationship.setSeparationPlace(relationshipData.getSeparationPlace());
        relationship.setDivorceDate(relationshipData.getDivorceDate());
        relationship.setDivorcePlace(relationshipData.getDivorcePlace());
        relationship.setDescription(relationshipData.getDescription());

        DatabaseLib.save(relationship);
    }

    public static void saveEditedClan(ClanEditData clanData) {
        System.out.println("[DatabaseDelegate][saveClanData]");
        Clan clan = DatabaseLib.getClan(clanData.getId());

        clan.setName(clanData.getName());
        clan.setDescription(clanData.getDescription());

        if (clanData.getRoot() != null) {
            Long clanRootId = clanData.getRoot().getId();
            Person person = DatabaseLib.getPerson(clanRootId);
            clan.setRoot(person);
        }

        DatabaseLib.save(clan);
    }

    public static void saveEditedDocument(DocumentEditData documentData) {
        System.out.println("[DatabaseDelegate][saveEditedDocument]");
        Document document = DatabaseLib.getDocument(documentData.getId());

        document.setTitle(documentData.getTitle());
        document.setSymbol(documentData.getSymbol());
        document.setDescription(documentData.getDescription());

        for (Person person : document.getRelatedPeople()) {
            person.getRelatedDocuments().remove(document);
        }

        document.getRelatedPeople().clear();
        for (ReferenceListElement personElement : documentData.getRelatedPeople()) {
            Person person = DatabaseLib.getPerson(personElement.getId());
            document.getRelatedPeople().add(person);
            person.getRelatedDocuments().add(document);
        }

        DatabaseLib.save(document);
    }

    public static void saveNewPerson(PersonEditData personData) {
        System.out.println("[DatabaseDelegate][saveNewPerson]");
        Person person = new Person();

        person.setSex(personData.getSex());
        person.setNames(personData.getNames());
        person.setSurname(personData.getSurname());
        person.setBirthDate(personData.getBirthDate());
        person.setBirthPlace(personData.getBirthPlace());
        person.setDeathDate(personData.getDeathDate());
        person.setDeathPlace(personData.getDeathPlace());
        person.setBurialDate(personData.getBurialDate());
        person.setBurialPlace(personData.getBurialPlace());
        person.setEducation(personData.getEducation());
        person.setOccupation(personData.getOccupation());

        if (personData.getParents() != null) {
            Long parentsRelationshipId = personData.getParents().getId();
            Relationship parentsRelationship = DatabaseLib.getRelationship(parentsRelationshipId);
            person.setParents(parentsRelationship);
        }

        if (personData.getDokumenty() != null) {
            Set<Document> documents = new HashSet<Document>();
            for (ReferenceListElement dokumentElement : personData.getDokumenty()) {
                Document document = DatabaseLib.getDocument(dokumentElement.getId());
                documents.add(document);
                document.getRelatedPeople().add(person);
            }
            person.setRelatedDocuments(documents);
        }

        DatabaseLib.save(person);
    }

    public static void saveNewRelationship(RelationshipEditData relationshipData) {
        System.out.println("[DatabaseDelegate][saveNewRelationship]");
        Relationship relationship = new Relationship();

        if (relationshipData.getMale() != null) {
            Long maleId = relationshipData.getMale().getId();
            Person male = DatabaseLib.getPerson(maleId);
            relationship.setMale(male);
        }
        if (relationshipData.getFemale() != null) {
            Long femaleId = relationshipData.getFemale().getId();
            Person female = DatabaseLib.getPerson(femaleId);
            relationship.setFemale(female);
        }

        relationship.setFirstMetDate(relationshipData.getFirstMetDate());
        relationship.setFirstMetPlace(relationshipData.getFirstMetPlace());
        relationship.setMarriageDate(relationshipData.getMarriageDate());
        relationship.setMarriagePlace(relationshipData.getMarriagePlace());
        relationship.setSeparationDate(relationshipData.getSeparationDate());
        relationship.setSeparationPlace(relationshipData.getSeparationPlace());
        relationship.setDivorceDate(relationshipData.getDivorceDate());
        relationship.setDivorcePlace(relationshipData.getDivorcePlace());
        relationship.setDescription(relationshipData.getDescription());

        DatabaseLib.save(relationship);
    }

    public static void saveNewClan(ClanEditData clanData) {
        System.out.println("[DatabaseDelegate][saveNewClan]");
        Clan clan = new Clan();

        clan.setName(clanData.getName());
        clan.setDescription(clanData.getDescription());

        if (clanData.getRoot() != null) {
            Long clanRootId = clanData.getRoot().getId();
            Person person = DatabaseLib.getPerson(clanRootId);
            clan.setRoot(person);
        }

        DatabaseLib.save(clan);
    }

    public static void saveNewDocument(DocumentEditData documentData) {
        System.out.println("[DatabaseDelegate][saveNewDocument]");
        Document document = new Document();

        document.setTitle(documentData.getTitle());
        document.setSymbol(documentData.getSymbol());
        document.setDescription(documentData.getDescription());

        Set<Person> people = new HashSet<Person>();
        if (documentData.getRelatedPeople() != null) {
            for (ReferenceListElement personElement : documentData.getRelatedPeople()) {
                Person person = DatabaseLib.getPerson(personElement.getId());
                people.add(person);
                person.getRelatedDocuments().add(document);
            }
        }
        document.setRelatedPeople(people);
        DatabaseLib.save(document);
    }

    public static List<ReferenceListElement> getRelationships() {
        System.out.println("[DatabaseDelegate][getRelationships]");
        Collection<Relationship> relationships = DatabaseLib.getRelationships(null, null);
        List<ReferenceListElement> result = new ArrayList<ReferenceListElement>();
        if (relationships != null && relationships.size() > 0) {
            for (Relationship relationship : relationships) {
                result.add(relationshipToReferenceListElement(relationship));
            }
        }
        return result;
    }

    public static List<ReferenceListElement> getMales() {
        System.out.println("[DatabaseDelegate][getMales]");
        Collection<Person> males = DatabaseLib.getMales();
        return toStruct(males);
    }

    public static List<ReferenceListElement> getFemales() {
        System.out.println("[DatabaseDelegate][getFemales]");
        Collection<Person> females = DatabaseLib.getFemales();
        return toStruct(females);
    }

    public static List<ReferenceListElement> getPeople() {
        System.out.println("[DatabaseDelegate][getPeople]");
        Collection<Person> people = DatabaseLib.getPeople();
        return toStruct(people);
    }

    public static List<ReferenceListElement> getDocuments() {
        System.out.println("[DatabaseDelegate][getDocuments]");
        Collection<Document> documents = DatabaseLib.getDocuments();
        return dokumentsToReferencedListElements(documents);
    }

    private static ReferenceListElement relationshipToReferenceListElement(Relationship relationship) {
        ReferenceListElement result = new ReferenceListElement();
        result.setId(relationship.getId());
        result.setDescription(relationship.getMale().toString() + " " + relationship.getFemale().toString());
        return result;
    }
    
    private static List<ReferenceListElement> toStruct(Collection<Person> people) {
        List<ReferenceListElement> result = new ArrayList<ReferenceListElement>();
        if (people != null && people.size() > 0) {
            for (Person person : people) {
                result.add(personToReferenceListElement(person));
            }
        }
        return result;
    }

    private static List<ReferenceListElement> dokumentsToReferencedListElements(Collection<Document> documents) {
        List<ReferenceListElement> result = new ArrayList<ReferenceListElement>();
        if (documents != null && documents.size() > 0) {
            for (Document document : documents) {
                result.add(dokumentToReferencedListElement(document));
            }
        }
        return result;
    }

    public static void deletePersonOsobe(Long personId) {
        Person person = DatabaseLib.getPerson(personId);
        DatabaseLib.delete(person);
    }

    public static void deleteRelationship(Long relationshipId) {
        Relationship relationship = DatabaseLib.getRelationship(relationshipId);
        DatabaseLib.delete(relationship);
    }

    public static void deleteClan(Long clanId) {
        Clan clan = DatabaseLib.getClan(clanId);
        DatabaseLib.delete(clan);
    }

    public static void deleteDocument(Long documentId) {
        Document document = DatabaseLib.getDocument(documentId);
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

    private static Set<ReferenceListElement> peopleToReferenceListElements(Set<Person> people) {
        Set<ReferenceListElement> result = new HashSet<ReferenceListElement>();
        for (Person person : people) {
            result.add(personToReferenceListElement(person));
        }

        return result;
    }

    private static ReferenceListElement dokumentToReferencedListElement(Document document) {
        ReferenceListElement result = new ReferenceListElement();
        result.setId(document.getId());
        result.setDescription(document.getTitle() + " (" + document.getSymbol() + ")");
        return result;
    }

    private static ReferenceListElement personToReferenceListElement(Person person) {
        ReferenceListElement result = new ReferenceListElement();
        result.setId(person.getId());
        result.setDescription(person.getNames() + " " + person.getSurname() + " (" + person.getId() + ")");

        return result;
    }
}