package pl.sebcel.genealogy.dto;

public class RelationshipEditData {

    private Long id;
    private ReferenceListElement male;
    private ReferenceListElement female;
    private String firstMetDate;
    private String firstMetPlace;
    private String marriageDate;
    private String marriagePlace;
    private String separationDate;
    private String separationPlace;
    private String divorceDate;
    private String divorcePlace;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReferenceListElement getMale() {
        return male;
    }

    public void setMale(ReferenceListElement male) {
        this.male = male;
    }

    public ReferenceListElement getFemale() {
        return female;
    }

    public void setFemale(ReferenceListElement female) {
        this.female = female;
    }

    public String getFirstMetDate() {
        return firstMetDate;
    }

    public void setFirstMetDate(String firstMet) {
        this.firstMetDate = firstMet;
    }

    public String getFirstMetPlace() {
        return firstMetPlace;
    }

    public void setFirstMetPlace(String firstMetPlace) {
        this.firstMetPlace = firstMetPlace;
    }

    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getMarriagePlace() {
        return marriagePlace;
    }

    public void setMarriagePlace(String marriagePlace) {
        this.marriagePlace = marriagePlace;
    }

    public String getSeparationDate() {
        return separationDate;
    }

    public void setSeparationDate(String separationDate) {
        this.separationDate = separationDate;
    }

    public String getSeparationPlace() {
        return separationPlace;
    }

    public void setSeparationPlace(String separationPlace) {
        this.separationPlace = separationPlace;
    }

    public String getDivorceDate() {
        return divorceDate;
    }

    public void setDivorceDate(String divorceDate) {
        this.divorceDate = divorceDate;
    }

    public String getDivorcePlace() {
        return divorcePlace;
    }

    public void setDivorcePlace(String divorcePlace) {
        this.divorcePlace = divorcePlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}