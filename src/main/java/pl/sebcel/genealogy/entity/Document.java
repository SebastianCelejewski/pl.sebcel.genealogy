package pl.sebcel.genealogy.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dokumenty")
public class Document {

    private Long id;
    private String title;
    private String symbol;
    private String description;
    private Set<Person> relatedPeople;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tytul", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "symbol", nullable = true, length = 255)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "opis", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(mappedBy = "relatedDocuments")
    public Set<Person> getRelatedPeople() {
        return relatedPeople;
    }

    public void setRelatedPeople(Set<Person> relatedPeople) {
        this.relatedPeople = relatedPeople;
    }
}