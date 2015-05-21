package pl.sebcel.genealogy.dto.list;

public class ReferenceListElement {

    private long id;
    private String description;

    public ReferenceListElement() {
    }

    public ReferenceListElement(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description + " (" + id + ")";
    }

    @Override
    public int hashCode() {
        return (int) (id % 15);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ReferenceListElement other = (ReferenceListElement) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}