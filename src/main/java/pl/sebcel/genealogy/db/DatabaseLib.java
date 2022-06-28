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

    public static Person getPerson(Long personId) {
        return (Person) HibernateUtil.getSession().get(Person.class, personId);
    }

    public static Relationship getRelationship(Long relationshipId) {
        return (Relationship) HibernateUtil.getSession().get(Relationship.class, relationshipId);
    }

    public static Clan getClan(Long clanId) {
        return (Clan) HibernateUtil.getSession().get(Clan.class, clanId);
    }

    public static Document getDocument(Long documentId) {
        return (Document) HibernateUtil.getSession().get(Document.class, documentId);
    }

    public static void save(Object object) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();
        session.flush();
    }

    public static void delete(Object object) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.flush();
    }

    @SuppressWarnings("unchecked")
    public static List<Person> getPeople() {
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Person.class);
        List<Person> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Person> getMales() {
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Person.class);
        criteria.add(Expression.eq("sex", "male"));
        List<Person> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Person> getFemales() {
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Person.class);
        criteria.add(Expression.eq("sex", "female"));
        List<Person> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Relationship> getRelationships(Person male, Person female) {
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
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Clan.class);
        List<Clan> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Document> getDocuments() {
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Document.class);
        List<Document> result = criteria.list();
        return result;
    }
}