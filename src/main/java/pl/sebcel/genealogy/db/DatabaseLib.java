package pl.sebcel.genealogy.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import pl.sebcel.genealogy.entity.Dokument;
import pl.sebcel.genealogy.entity.Klan;
import pl.sebcel.genealogy.entity.Osoba;
import pl.sebcel.genealogy.entity.Zwiazek;

public class DatabaseLib {

    // Sekcja GET

    public static Osoba getPerson(Long personId) {
        System.out.println("[DatabaseLib][getPerson]");
        return (Osoba) HibernateUtil.getSession().get(Osoba.class, personId);
    }

    public static Zwiazek getRelationship(Long relationshipId) {
        System.out.println("[DatabaseLib][getRelationship"
                + "]");
        return (Zwiazek) HibernateUtil.getSession().get(Zwiazek.class, relationshipId);
    }

    public static Klan getClan(Long clanId) {
        System.out.println("[DatabaseLib][getClan]");
        return (Klan) HibernateUtil.getSession().get(Klan.class, clanId);
    }

    public static Dokument getDocument(Long documentId) {
        System.out.println("[DatabaseLib][getDocument]");
        return (Dokument) HibernateUtil.getSession().get(Dokument.class, documentId);
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
    public static List<Osoba> getPeople() {
        System.out.println("[DatabaseLib][getPeople]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Osoba.class);
        List<Osoba> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Osoba> getMales() {
        System.out.println("[DatabaseLib][getMales]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Osoba.class);
        criteria.add(Expression.eq("plec", "mezczyzna"));
        List<Osoba> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Osoba> getFemales() {
        System.out.println("[DatabaseLib][getFemales]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Osoba.class);
        criteria.add(Expression.eq("plec", "kobieta"));
        List<Osoba> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Zwiazek> getRelationships(Osoba male, Osoba female) {
        System.out.println("[DatabaseLib][getRelationships]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Zwiazek.class);
        if (male != null) {
            criteria.add(Expression.eq("mezczyzna", male));
        }
        if (female != null) {
            criteria.add(Expression.eq("kobieta", female));
        }
        List<Zwiazek> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Klan> getClans() {
        System.out.println("[DatabaseLib][getClans]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Klan.class);
        List<Klan> result = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Dokument> getDocuments() {
        System.out.println("[DatabaseLib][getDocuments]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Dokument.class);
        List<Dokument> result = criteria.list();
        return result;
    }
}