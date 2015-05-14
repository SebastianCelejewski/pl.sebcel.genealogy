package pl.sebcel.genealogy.db;

import pl.sebcel.genealogy.entity.Osoba;
import pl.sebcel.genealogy.entity.Zwiazek;

public class DatabaseUtil {

    public static String getBirthInfo(Osoba osoba) {
        String info = "";
        if (osoba.getDataUrodzenia() != null && osoba.getDataUrodzenia().length() > 0) {
            info += osoba.getDataUrodzenia();
        }
        if (osoba.getMiejsceUrodzenia() != null && osoba.getMiejsceUrodzenia().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += osoba.getMiejsceUrodzenia();
        }
        if (info.length() > 0)
            return "ur. " + info;
        else
            return "";
    }

    public static String getDeathInfo(Osoba osoba) {
        String info = "";
        if (osoba.getDataSmierci() != null && osoba.getDataSmierci().length() > 0) {
            info += osoba.getDataSmierci();
        }
        if (osoba.getMiejsceSmierci() != null && osoba.getMiejsceSmierci().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += osoba.getMiejsceSmierci();
        }
        if (info.length() > 0)
            return "zm. " + info;
        else
            return "";
    }

    public static String getMeetInfo(Zwiazek zwiazek) {
        String info = "";
        if (zwiazek.getDataPoznania() != null && zwiazek.getDataPoznania().length() > 0) {
            info += zwiazek.getDataPoznania();
        }
        if (zwiazek.getMiejscePoznania() != null && zwiazek.getMiejscePoznania().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getMiejscePoznania();
        }
        if (info.length() > 0)
            return "spotkanie: " + info + "";
        else
            return "";
    }

    public static String getMarriageInfo(Zwiazek zwiazek) {
        String info = "";
        if (zwiazek.getDataSlubu() != null && zwiazek.getDataSlubu().length() > 0) {
            info += zwiazek.getDataSlubu();
        }
        if (zwiazek.getMiejsceSlubu() != null && zwiazek.getMiejsceSlubu().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getMiejsceSlubu();
        }
        if (info.length() > 0)
            return "œlub: " + info;
        else
            return "";
    }

    public static String getSeparationInfo(Zwiazek zwiazek) {
        String info = "";
        if (zwiazek.getDataRozstania() != null && zwiazek.getDataRozstania().length() > 0) {
            info += zwiazek.getDataRozstania();
        }
        if (zwiazek.getMiejsceRozstania() != null && zwiazek.getMiejsceRozstania().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getMiejsceRozstania();
        }
        if (info.length() > 0)
            return "rozstanie: " + info;
        else
            return "";
    }

    public static String getDivorceInfo(Zwiazek zwiazek) {
        String info = "";
        if (zwiazek.getDataRozwodu() != null && zwiazek.getDataRozwodu().length() > 0) {
            info += zwiazek.getDataRozwodu();
        }
        if (zwiazek.getMiejsceRozwodu() != null && zwiazek.getMiejsceRozwodu().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getMiejsceRozwodu();
        }
        if (info.length() > 0)
            return "rozwód: " + info;
        else
            return "";
    }

    public static String getOccupationInfo(Osoba osoba) {
        String info = "";
        if (osoba.getWyksztalcenie() != null && osoba.getWyksztalcenie().length() > 0) {
            info += osoba.getWyksztalcenie();
        }
        if (osoba.getZawodyWykonywane() != null && osoba.getZawodyWykonywane().length() > 0) {
            if (info.length() > 0)
                info += " / ";
            info += osoba.getZawodyWykonywane();
        }
        return info.trim();
    }

    public static String getResidenceInfo(Osoba osoba) {
        String info = "";
        if (osoba.getMiejsceZamieszkania() != null && osoba.getMiejsceZamieszkania().length() > 0) {
            info += osoba.getMiejsceZamieszkania();
        }
        if (info.length() > 0)
            return "zam. " + info;
        else
            return "";
    }
}