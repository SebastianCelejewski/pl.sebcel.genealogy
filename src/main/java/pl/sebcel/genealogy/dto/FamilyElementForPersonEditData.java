package pl.sebcel.genealogy.dto;

import java.util.List;

import pl.sebcel.genealogy.dto.list.ReferenceListElement;

public class FamilyElementForPersonEditData {
    private ReferenceListElement spouse;
    private List<ReferenceListElement> children;

    public ReferenceListElement getSpouse() {
        return spouse;
    }

    public void setSpouse(ReferenceListElement spouse) {
        this.spouse = spouse;
    }

    public List<ReferenceListElement> getChildren() {
        return children;
    }

    public void setChildren(List<ReferenceListElement> children) {
        this.children = children;
    }
}