package pl.sebcel.genealogy.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import pl.sebcel.genealogy.entity.Document;
import pl.sebcel.genealogy.entity.Clan;
import pl.sebcel.genealogy.entity.Person;
import pl.sebcel.genealogy.entity.Relationship;

public class DatabaseLib {

    // Sekcja GET

    public static Person getPerson(Long personId) {
        System.out.println("[DatabaseLib][getPerson]");
        return (Person) HibernateUtil.getSession().get(Person.class, personId);
    }

    public static Relationship getRelationship(Long relationshipId) {
        System.out.println("[DatabaseLib][getRelationship"
                + "]");
        return (Relationship) HibernateUtil.getSession().get(Relationship.class, relationshipId);
    }

    public static Clan getClan(Long clanId) {
        System.out.println("[DatabaseLib][getClan]");
        return (Clan) HibernateUtil.getSession().get(Clan.class, clanId);
    }

    public static Document getDocument(Long documentId) {
        System.out.println("[DatabaseLib][getDocument]");
        return (Document) HibernateUtil.getSession().get(Document.class, documentId);
    }

    // Sekcja SAVE

    public static void save(Object object) {
        System.out.println("[DatabaseLib][save]");
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();
        session.flush();
    }

    public static void delete(Object object) {
        System.out.println("[DatabaseLib][delete]");
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.flush();
    }

    // Sekcja LIST
    @SuppressWarnings("unchecked")
    public static List<Person> getPeople() {
        System.out.println("[DatabaseLib][getPeople]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Person.class);
        List<Person> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Person> getMales() {
        System.out.println("[DatabaseLib][getMales]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Person.class);
        criteria.add(Expression.eq("sex", "male"));
        List<Person> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Person> getFemales() {
        System.out.println("[DatabaseLib][getFemales]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Person.class);
        criteria.add(Expression.eq("sex", "female"));
        List<Person> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Relationship> getRelationships(Person male, Person female) {
        System.out.println("[DatabaseLib][getRelationships]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Relationship.class);
        if (male != null) {
            criteria.add(Expression.eq("male", male));
        }
        if (female != null) {
            criteria.add(Expression.eq("female", female));
        }
        List<Relationship> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Clan> getClans() {
        System.out.println("[DatabaseLib][getClans]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Clan.class);
        List<Clan> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Document> getDocuments() {
        System.out.println("[DatabaseLib][getDocuments]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Document.class);
        List<Document> result = criteria.list();
        return result;
    }
}