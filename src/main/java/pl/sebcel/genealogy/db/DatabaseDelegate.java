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

import pl.sebcel.genealogy.dto.FamilyElementForPersonEditData;
import pl.sebcel.genealogy.dto.edit.ClanEditData;
import pl.sebcel.genealogy.dto.edit.DocumentEditData;
import pl.sebcel.genealogy.dto.edit.PersonEditData;
import pl.sebcel.genealogy.dto.edit.RelationshipEditData;
import pl.sebcel.genealogy.dto.list.ClanListElement;
import pl.sebcel.genealogy.dto.list.DocumentListElement;
import pl.sebcel.genealogy.dto.list.PeopleListElement;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.dto.list.RelationshipsListElement;
import pl.sebcel.genealogy.dto.pedigree.FamilyTreeElement;
import pl.sebcel.genealogy.dto.pedigree.PersonTreeElement;
import pl.sebcel.genealogy.entity.Document;
import pl.sebcel.genealogy.entity.Clan;
import pl.sebcel.genealogy.entity.Person;
import pl.sebcel.genealogy.entity.Relationship;

public class DatabaseDelegate {

    private final static DateFormat inputDF = new SimpleDateFormat("dd-MM-yyyy");
    private final static DateFormat outputDF = new SimpleDateFormat("yyyy-MM-dd");

    public static List<PeopleListElement> getPeopleList() {
        Collection<Person> people = DatabaseLib.getPeople();
        List<PeopleListElement> peopleList = new ArrayList<PeopleListElement>();
        if (people != null && people.size() > 0) {
            for (Person person : people) {
                PeopleListElement listElement = new PeopleListElement();
                listElement.setId(person.getId());
                listElement.setName(person.toString());
                listElement.setDescription(DatabaseUtil.getBirthInfo(person) + " " + DatabaseUtil.getDeathInfo(person));
                peopleList.add(listElement);
            }
        }
        return peopleList;
    }

    public static String getPeopleListXML() {
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

    public static String getPeopleListCSV() {
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("\"id\",\"first-names\",\"last-name\",\"birth-date\",\"birth-place\",\"death-date\",\"death-place\",\"burial-date\",\"burial-place\",\"residence\",\"occupation\",\"professions\",\"father\",\"mother\",\"relationships\"\n");
        Collection<Person> people = DatabaseLib.getPeople();
        if (people != null && people.size() > 0) {
            for (Person person : people) {
                xmlData.append(getPersonCSV(person) + "\n");
            }
        }
        return xmlData.toString();
    }

    public static String getDocumentXML(Document document) {
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
        return document.getId() + ";" + document.getTitle() + ";" + document.getSymbol() + ";" + document.getDescription() + "\n";
    }

    public static String getPersonXML(Person osoba) {
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
            zwiazki = osoba.getRelationshipsAsMale();
        } else {
            zwiazki = osoba.getRelationshipsAsFemale();
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

    public static String getPersonCSV(Person osoba) {
        StringBuffer csvRow = new StringBuffer();
        csvRow.append(getCSVField(osoba.getId()));
        csvRow.append(getCSVField(osoba.getNames()));
        csvRow.append(getCSVField(osoba.getSurname()));
        csvRow.append(getCSVField(osoba.getBirthDate()));
        csvRow.append(getCSVField(osoba.getBirthPlace()));
        csvRow.append(getCSVField(osoba.getDeathDate()));
        csvRow.append(getCSVField(osoba.getDeathPlace()));
        csvRow.append(getCSVField(osoba.getBurialDate()));
        csvRow.append(getCSVField(osoba.getBurialPlace()));
        csvRow.append(getCSVField(osoba.getResidence()));
        csvRow.append(getCSVField(osoba.getEducation()));
        csvRow.append(getCSVField(osoba.getOccupation()));
        
        if (osoba.getParents() != null) {
            Person maleParent = osoba.getParents().getMale();
            Person femaleParent = osoba.getParents().getFemale();
            if (maleParent != null) {
                csvRow.append(maleParent.getId());
            } 
            csvRow.append(",");
            if (femaleParent != null) {
                csvRow.append(femaleParent.getId());
            } 
            csvRow.append(",");
        } else {
            csvRow.append(",,");
        }

        Collection<Relationship> zwiazki = null;
        StringBuffer zwiazkiStr = new StringBuffer();
        if (osoba.getSex().equalsIgnoreCase("male")) {
            zwiazki = osoba.getRelationshipsAsMale();
        } else {
            zwiazki = osoba.getRelationshipsAsFemale();
        }

        if (zwiazki != null && zwiazki.size() > 0) {
            for (Relationship zwiazek : zwiazki) {
                Long idMalzonka = null;
                if (osoba.getSex().equalsIgnoreCase("male")) {
                    idMalzonka = zwiazek.getFemale().getId();
                } else {
                    idMalzonka = zwiazek.getMale().getId();
                }
                String zwiazekStr = "" + idMalzonka + ":(";

                Collection<Person> dzieci = zwiazek.getChildren();
                if (dzieci != null && dzieci.size() > 0) {
                    String dzieciStr = "";
                    for (Person dziecko : dzieci) {
                        if (dzieciStr.length() > 0) {
                            dzieciStr += ", ";
                        }
                        dzieciStr += dziecko.getId();
                    }
                    zwiazekStr += dzieciStr;
                }
                zwiazekStr += ")";

                if (zwiazkiStr.length() > 0) {
                    zwiazkiStr.append("; ");
                }
                zwiazkiStr.append(zwiazekStr);
            }
        }
        csvRow.append("\"" + zwiazkiStr+"\"");

        return csvRow.toString();
    }

    public static List<RelationshipsListElement> getRelationshipsList() {
        Collection<Relationship> relationships = DatabaseLib.getRelationships(null, null);
        List<RelationshipsListElement> relationshipsList = new ArrayList<RelationshipsListElement>();
        if (relationships != null && relationships.size() > 0) {
            for (Relationship relationship : relationships) {
                RelationshipsListElement listElement = new RelationshipsListElement();
                listElement.setId(relationship.getId());
                listElement.setMale(relationship.getMale().toString());
                listElement.setFemale(relationship.getFemale().toString());
                listElement.setDescription(DatabaseUtil.getMarriageInfo(relationship) + " " + DatabaseUtil.getDivorceInfo(relationship));
                relationshipsList.add(listElement);
            }
        }
        return relationshipsList;
    }

    public static List<ClanListElement> getClansList() {
        Collection<Clan> clans = DatabaseLib.getClans();
        List<ClanListElement> clansList = new ArrayList<ClanListElement>();
        if (clans != null && clans.size() > 0) {
            for (Clan clan : clans) {
                ClanListElement listElement = new ClanListElement();
                listElement.setId(clan.getId());
                listElement.setName(clan.getName());
                listElement.setDescription(clan.getDescription());
                listElement.setRoot(clan.getRoot().toString());
                clansList.add(listElement);
            }
        }
        return clansList;
    }

    public static List<DocumentListElement> getDocumentsList() {
        Collection<Document> documents = DatabaseLib.getDocuments();
        List<DocumentListElement> documentsList = new ArrayList<DocumentListElement>();
        if (documents != null && documents.size() > 0) {
            for (Document document : documents) {
                DocumentListElement listElement = new DocumentListElement();
                listElement.setId(document.getId());
                listElement.setTitle(document.getTitle());
                listElement.setSymbol(document.getSymbol());
                documentsList.add(listElement);
            }
        }
        return documentsList;
    }

    public static PersonTreeElement getPersonDataForPedigree(Long personId) {
        Person person = DatabaseLib.getPerson(personId);
        PersonTreeElement result = new PersonTreeElement();
        result.setId(person.getId());
        result.setDescription(person.getNames() + " " + person.getSurname());
        result.setBirthData(DatabaseUtil.getBirthInfo(person));
        result.setDeathData(DatabaseUtil.getDeathInfo(person));
        result.setBurialData("");
        result.setResidenceData(DatabaseUtil.getResidenceInfo(person));
        result.setOccupationData(DatabaseUtil.getOccupationInfo(person));
        
        Collection<Relationship> relationships = null;
        if (person.getSex().equalsIgnoreCase("male")) {
            relationships = person.getRelationshipsAsMale();
        } else {
            relationships = person.getRelationshipsAsFemale();
        }

        if (relationships != null && relationships.size() > 0) {
            List<FamilyTreeElement> familyIds = new ArrayList<FamilyTreeElement>();
            for (Relationship relationship : relationships) {
                FamilyTreeElement family = new FamilyTreeElement();
                family.setRelationshipId(relationship.getId());
                if (person.getSex().equalsIgnoreCase("male")) {
                    family.setSpouseId(relationship.getFemale().getId());
                } else {
                    family.setSpouseId(relationship.getMale().getId());
                }
                family.setFirstMetData(DatabaseUtil.getMeetInfo(relationship));
                family.setMarriageData(DatabaseUtil.getMarriageInfo(relationship));
                family.setSeparationData(DatabaseUtil.getSeparationInfo(relationship));
                family.setDivorceData(DatabaseUtil.getDivorceInfo(relationship));
                List<Person> children = sortByBirthDate(relationship.getChildren());
                if (children != null && children.size() > 0) {
                    List<Long> childrenIds = new ArrayList<Long>();
                    for (Person child : children) {
                        childrenIds.add(child.getId());
                    }
                    family.setChildrenIds(childrenIds);
                }
                familyIds.add(family);
                relationship = null;
            }
            result.setFamilies(familyIds);
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

        personEditData.setFamilies(new Vector<FamilyElementForPersonEditData>());
        Collection<Relationship> families = null;
        if (personEditData.getSex() != null && personEditData.getSex().equalsIgnoreCase("Male")) {
            families = person.getRelationshipsAsMale();
        } else {
            families = person.getRelationshipsAsFemale();
        }
        if (families != null) {
            for (Relationship relationship : families) {
                Person spouse = null;
                if (personEditData.getSex() != null && personEditData.getSex().equalsIgnoreCase("male")) {
                    spouse = relationship.getFemale();
                } else {
                    spouse = relationship.getMale();
                }
                FamilyElementForPersonEditData family = new FamilyElementForPersonEditData();
                if (spouse != null)
                    family.setSpouse(new ReferenceListElement(spouse.getId(), spouse.getNames() + " " + spouse.getSurname()));
                family.setChildren(new Vector<ReferenceListElement>());
                if (relationship.getChildren() != null && relationship.getChildren().size() > 0) {
                    for (Person child : relationship.getChildren()) {
                        family.getChildren().add(new ReferenceListElement(child.getId(), child.getNames() + " " + child.getSurname()));
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

        personEditData.setRelatedDocuments(documents);
        return personEditData;
    }

    public static RelationshipEditData getRelationshipEditData(Long relationshipId) {
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
        Clan clan = DatabaseLib.getClan(clanId);

        ClanEditData clanEditData = new ClanEditData();
        clanEditData.setId(clan.getId());
        clanEditData.setName(clan.getName());
        clanEditData.setDescription(clan.getDescription());
        clanEditData.setRoot(personToReferenceListElement(clan.getRoot()));

        return clanEditData;
    }

    public static DocumentEditData getDocumentEditData(Long documentId) {
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
        for (ReferenceListElement documentElement : personData.getRelatedDocuments()) {
            Document document = DatabaseLib.getDocument(documentElement.getId());
            person.getRelatedDocuments().add(document);
            document.getRelatedPeople().add(person);
        }

        DatabaseLib.save(person);
    }

    public static void saveEditedRelationship(RelationshipEditData relationshipData) {
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

        if (personData.getRelatedDocuments() != null) {
            Set<Document> documents = new HashSet<Document>();
            for (ReferenceListElement dokumentElement : personData.getRelatedDocuments()) {
                Document document = DatabaseLib.getDocument(dokumentElement.getId());
                documents.add(document);
                document.getRelatedPeople().add(person);
            }
            person.setRelatedDocuments(documents);
        }

        DatabaseLib.save(person);
    }

    public static void saveNewRelationship(RelationshipEditData relationshipData) {
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
        Collection<Person> males = DatabaseLib.getMales();
        return toStruct(males);
    }

    public static List<ReferenceListElement> getFemales() {
        Collection<Person> females = DatabaseLib.getFemales();
        return toStruct(females);
    }

    public static List<ReferenceListElement> getPeople() {
        Collection<Person> people = DatabaseLib.getPeople();
        return toStruct(people);
    }

    public static List<ReferenceListElement> getDocuments() {
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

    private static String getCSVField(Object value) {
        if (value == null) {
            return ",";
        }
        if (value instanceof String) {
            return "\"" + value + "\",";
        }
        return value.toString() + ",";
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