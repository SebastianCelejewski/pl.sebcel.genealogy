package pl.sebcel.genealogy.dto.edit;

import java.util.List;
import java.util.Set;

import pl.sebcel.genealogy.dto.FamilyElementForPersonEditData;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;

public class PersonEditData {

    private Long id;
    private String names;
    private String surname;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String burialDate;
    private String burialPlace;
    private ReferenceListElement parents;
    private List<FamilyElementForPersonEditData> families;
    private Set<ReferenceListElement> relatedDocuments;
    private String sex;
    private String education;
    private String occupation;
    private String description;
    private String residence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public String getBurialDate() {
        return burialDate;
    }

    public void setBurialDate(String burialDate) {
        this.burialDate = burialDate;
    }

    public String getBurialPlace() {
        return burialPlace;
    }

    public void setBurialPlace(String burialPlace) {
        this.burialPlace = burialPlace;
    }

    public ReferenceListElement getParents() {
        return parents;
    }

    public void setParents(ReferenceListElement parents) {
        this.parents = parents;
    }

    public List<FamilyElementForPersonEditData> getFamilies() {
        return families;
    }

    public void setFamilies(List<FamilyElementForPersonEditData> families) {
        this.families = families;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public Set<ReferenceListElement> getRelatedDocuments() {
        return relatedDocuments;
    }

    public void setRelatedDocuments(Set<ReferenceListElement> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }
}