package pl.sebcel.genealogy.dto.pedigree;

import java.util.List;

public class PersonTreeElement {

    private Long id;
    private String description;
    private String birthData;
    private String deathData;
    private String burialData;
    private String residenceData;
    private String occupationData;
    private List<FamilyTreeElement> families;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthData() {
        return birthData;
    }

    public void setBirthData(String birthData) {
        this.birthData = birthData;
    }

    public String getDeathData() {
        return deathData;
    }

    public void setDeathData(String deathData) {
        this.deathData = deathData;
    }

    public String getBurialData() {
        return burialData;
    }

    public void setBurialData(String burialData) {
        this.burialData = burialData;
    }

    public String getResidenceData() {
        return residenceData;
    }

    public void setResidenceData(String residenceData) {
        this.residenceData = residenceData;
    }

    public String getOccupationData() {
        return occupationData;
    }

    public void setOccupationData(String occupationData) {
        this.occupationData = occupationData;
    }

    public List<FamilyTreeElement> getFamilies() {
        return families;
    }

    public void setFamilies(List<FamilyTreeElement> families) {
        this.families = families;
    }

}
