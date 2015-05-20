package pl.sebcel.genealogy.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "osoby")
public class Person {

    private Long id;
    private String names;
    private String surname;
    private String plec;

    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String burialDate;
    private String burialPlace;

    private Relationship parents;
    private Collection<Relationship> relationshipsAsMale;
    private Collection<Relationship> relationshipsAsFemale;

    private String residence;

    private String education;
    private String occupation;
    private String description;
    private Set<Document> relatedDocuments;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "plec", nullable = true, length = 1)
    public String getSex() {
        return plec;
    }

    public void setSex(String plec) {
        this.plec = plec;
    }

    @Column(name = "imiona", nullable = true, length = 64)
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    @Column(name = "nazwisko", nullable = true, length = 128)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "data_urodzenia", nullable = true, length = 64)
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "miejsce_urodzenia", nullable = true, length = 128)
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Column(name = "data_smierci", nullable = true, length = 64)
    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    @Column(name = "miejsce_smierci", nullable = true, length = 128)
    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    @Column(name = "data_pochowania", nullable = true, length = 64)
    public String getBurialDate() {
        return burialDate;
    }

    public void setBurialDate(String burialDate) {
        this.burialDate = burialDate;
    }

    @Column(name = "miejsce_pochowania", nullable = true, length = 128)
    public String getBurialPlace() {
        return burialPlace;
    }

    public void setBurialPlace(String burialPlace) {
        this.burialPlace = burialPlace;
    }

    @ManyToOne
    @JoinColumn(name = "id_zwiazku_rodzicow")
    public Relationship getParents() {
        return parents;
    }

    public void setParents(Relationship parents) {
        this.parents = parents;
    }

    @OneToMany(mappedBy = "male")
    public Collection<Relationship> getRelationshipsAsMale() {
        return relationshipsAsMale;
    }

    public void setRelationshipsAsMale(Collection<Relationship> relationshipsAsMale) {
        this.relationshipsAsMale = relationshipsAsMale;
    }

    @OneToMany(mappedBy = "female")
    public Collection<Relationship> getRelationshipsAsFemale() {
        return relationshipsAsFemale;
    }

    public void setRelationshipsAsFemale(Collection<Relationship> relationshipsAsFemale) {
        this.relationshipsAsFemale = relationshipsAsFemale;
    }

    @Column(name = "zawody_wykonywane", nullable = true, length = 255)
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Column(name = "wyksztalcenie", nullable = true)
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Column(name = "opis", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "miejsce_zamieszkania", nullable = true)
    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    @ManyToMany
    @JoinTable (name = "dokumenty_osoby", 
        joinColumns = {@JoinColumn(name = "id_osoby", nullable = false)}, 
        inverseJoinColumns = {@JoinColumn (name = "id_dokumentu", nullable = false) }
    )
    public Set<Document> getRelatedDocuments() {
        return relatedDocuments;
    }

    public void setRelatedDocuments(Set<Document> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }

    public String toString() {
        return getNames() + " " + getSurname() + " (" + getId() + ")";
    }
}