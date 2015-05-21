package pl.sebcel.genealogy.db;

import pl.sebcel.genealogy.entity.Person;
import pl.sebcel.genealogy.entity.Relationship;

public class DatabaseUtil {

    public static String getBirthInfo(Person osoba) {
        String info = "";
        if (osoba.getBirthDate() != null && osoba.getBirthDate().length() > 0) {
            info += osoba.getBirthDate();
        }
        if (osoba.getBirthPlace() != null && osoba.getBirthPlace().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += osoba.getBirthPlace();
        }
        if (info.length() > 0)
            return "ur. " + info;
        else
            return "";
    }

    public static String getDeathInfo(Person osoba) {
        String info = "";
        if (osoba.getDeathDate() != null && osoba.getDeathDate().length() > 0) {
            info += osoba.getDeathDate();
        }
        if (osoba.getDeathPlace() != null && osoba.getDeathPlace().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += osoba.getDeathPlace();
        }
        if (info.length() > 0)
            return "zm. " + info;
        else
            return "";
    }

    public static String getMeetInfo(Relationship zwiazek) {
        String info = "";
        if (zwiazek.getFirstMetDate() != null && zwiazek.getFirstMetDate().length() > 0) {
            info += zwiazek.getFirstMetDate();
        }
        if (zwiazek.getFirstMetPlace() != null && zwiazek.getFirstMetPlace().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getFirstMetPlace();
        }
        if (info.length() > 0)
            return "spotkanie: " + info + "";
        else
            return "";
    }

    public static String getMarriageInfo(Relationship zwiazek) {
        String info = "";
        if (zwiazek.getMarriageDate() != null && zwiazek.getMarriageDate().length() > 0) {
            info += zwiazek.getMarriageDate();
        }
        if (zwiazek.getMarriagePlace() != null && zwiazek.getMarriagePlace().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getMarriagePlace();
        }
        if (info.length() > 0)
            return "œlub: " + info;
        else
            return "";
    }

    public static String getSeparationInfo(Relationship zwiazek) {
        String info = "";
        if (zwiazek.getSeparationDate() != null && zwiazek.getSeparationDate().length() > 0) {
            info += zwiazek.getSeparationDate();
        }
        if (zwiazek.getSeparationPlace() != null && zwiazek.getSeparationPlace().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getSeparationPlace();
        }
        if (info.length() > 0)
            return "rozstanie: " + info;
        else
            return "";
    }

    public static String getDivorceInfo(Relationship zwiazek) {
        String info = "";
        if (zwiazek.getDivorceDate() != null && zwiazek.getDivorceDate().length() > 0) {
            info += zwiazek.getDivorceDate();
        }
        if (zwiazek.getDivorcePlace() != null && zwiazek.getDivorcePlace().length() > 0) {
            if (info.length() > 0)
                info += ", ";
            info += zwiazek.getDivorcePlace();
        }
        if (info.length() > 0)
            return "rozwód: " + info;
        else
            return "";
    }

    public static String getOccupationInfo(Person osoba) {
        String info = "";
        if (osoba.getEducation() != null && osoba.getEducation().length() > 0) {
            info += osoba.getEducation();
        }
        if (osoba.getOccupation() != null && osoba.getOccupation().length() > 0) {
            if (info.length() > 0)
                info += " / ";
            info += osoba.getOccupation();
        }
        return info.trim();
    }

    public static String getResidenceInfo(Person osoba) {
        String info = "";
        if (osoba.getResidence() != null && osoba.getResidence().length() > 0) {
            info += osoba.getResidence();
        }
        if (info.length() > 0)
            return "zam. " + info;
        else
            return "";
    }
}