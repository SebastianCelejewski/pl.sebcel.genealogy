package pl.sebcel.genealogy.dto;

import java.util.Set;

public class DocumentEditData {

    private Long id;
    private String title;
    private String symbol;
    private String description;
    private Set<ReferenceListElement> relatedPeople;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ReferenceListElement> getRelatedPeople() {
        return relatedPeople;
    }

    public void setRelatedPeople(Set<ReferenceListElement> relatedPeople) {
        this.relatedPeople = relatedPeople;
    }
}