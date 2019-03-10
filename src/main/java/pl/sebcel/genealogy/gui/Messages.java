package pl.sebcel.genealogy.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Messages");

    public static List<String> getAvailableLanguageVersions() {
        List<String> result = new ArrayList<String>();
        result.add("default");
        result.add("pl");
        result.add("de");
        return result;
    }

    public static void setLanguageVersion(String languageSymbol) {
        System.out.println("Default locale: " + Locale.getDefault());
        System.out.println("Language symbol: " + languageSymbol);
        if (languageSymbol.equals("default")) {
            bundle = ResourceBundle.getBundle("Messages", Locale.ENGLISH);
        } else {
            Locale locale = new Locale(languageSymbol);
            System.out.println("Selected locale: " + locale);
            bundle = ResourceBundle.getBundle("Messages", locale);
            System.out.println("Bundle locale: " + bundle.getLocale());
        }

        System.out.println(Messages.APPLICATION_NAME);
        reloadMessages(bundle);
    }
    
    private static void reloadMessages(ResourceBundle bundle) {
        APPLICATION_NAME = bundle.getString("ApplicationName");
        APPLICATION_VERSION = bundle.getString("ApplicationVersion");

        PEOPLE_LIST = bundle.getString("PeopleList");
        RELATIONSHIPS_LIST = bundle.getString("RelationshipsList");
        CLANS_LIST = bundle.getString("ClansList");
        DOCUMENTS_LIST = bundle.getString("DocumentsList");
    }

    public static String APPLICATION_NAME;
    public static String APPLICATION_VERSION;

    public static String PEOPLE_LIST;
    public static String RELATIONSHIPS_LIST;
    public static String CLANS_LIST;
    public static String DOCUMENTS_LIST;
}