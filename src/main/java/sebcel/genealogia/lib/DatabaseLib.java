package sebcel.genealogia.lib;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import sebcel.genealogia.entity.Dokument;
import sebcel.genealogia.entity.Klan;
import sebcel.genealogia.entity.Osoba;
import sebcel.genealogia.entity.Zwiazek;

public class DatabaseLib {

    // Sekcja GET

    public static Osoba getOsoba(Long idOsoby) {
        System.out.println("[DatabaseLib][getOsoba]");
        return (Osoba) HibernateUtil.getSession().get(Osoba.class, idOsoby);
    }

    public static Zwiazek getZwiazek(Long idZwiazku) {
        System.out.println("[DatabaseLib][getZwiazek]");
        return (Zwiazek) HibernateUtil.getSession().get(Zwiazek.class, idZwiazku);
    }

    public static Klan getKlan(Long idKlanu) {
        System.out.println("[DatabaseLib][getKlan]");
        return (Klan) HibernateUtil.getSession().get(Klan.class, idKlanu);
    }

    public static Dokument getDokument(Long idDokumentu) {
        System.out.println("[DatabaseLib][getDokument]");
        return (Dokument) HibernateUtil.getSession().get(Dokument.class, idDokumentu);
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
    public static List<Osoba> getOsoby() {
        System.out.println("[DatabaseLib][getOsoby]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Osoba.class);
        List<Osoba> rezultat = criteria.list();
        return rezultat;
    }

    @SuppressWarnings("unchecked")
    public static List<Osoba> getMezczyzni() {
        System.out.println("[DatabaseLib][getMezczyzni]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Osoba.class);
        criteria.add(Expression.eq("plec", "mezczyzna"));
        List<Osoba> rezultat = criteria.list();
        return rezultat;
    }

    @SuppressWarnings("unchecked")
    public static List<Osoba> getKobiety() {
        System.out.println("[DatabaseLib][getKobiety]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Osoba.class);
        criteria.add(Expression.eq("plec", "kobieta"));
        List<Osoba> rezultat = criteria.list();
        return rezultat;
    }

    @SuppressWarnings("unchecked")
    public static List<Zwiazek> getZwiazki(Osoba mezczyzna, Osoba kobieta) {
        System.out.println("[DatabaseLib][getZwiazki]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Zwiazek.class);
        if (mezczyzna != null)
            criteria.add(Expression.eq("mezczyzna", mezczyzna));
        if (kobieta != null)
            criteria.add(Expression.eq("kobieta", kobieta));
        List<Zwiazek> rezultat = criteria.list();
        return rezultat;
    }

    @SuppressWarnings("unchecked")
    public static List<Klan> getKlany() {
        System.out.println("[DatabaseLib][getKlany]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Klan.class);
        List<Klan> rezultat = criteria.list();
        return rezultat;
    }

    @SuppressWarnings("unchecked")
    public static List<Dokument> getDokumenty() {
        System.out.println("[DatabaseLib][getDokumenty]");
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(Dokument.class);
        List<Dokument> rezultat = criteria.list();
        return rezultat;
    }
}