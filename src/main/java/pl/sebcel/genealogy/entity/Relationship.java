package pl.sebcel.genealogy.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="relationships")
public class Relationship {

	private Long id;
	private Person male;
	private Person female;
	private String firstMetDate;
	private String firstMetPlace;
	private String MarriageDate;
	private String marriagePlace;
	private String separationDate;
	private String separationPlace;
	private String divorceDate;
	private String divorcePlace;
	private String description;
	private Collection<Person> children;
	
	@Id
	@Column (name="id")
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn (name="male_id")
	public Person getMale() {
		return male;
	}
	public void setMale(Person male) {
		this.male = male;
	}

	@ManyToOne
	@JoinColumn (name="female_id")
	public Person getFemale() {
		return female;
	}
	public void setFemale(Person female) {
		this.female = female;
	}

	@Column (name="marriage_date", nullable=true)
	public String getMarriageDate() {
		return MarriageDate;
	}
	public void setMarriageDate(String MarriageDate) {
		this.MarriageDate = MarriageDate;
	}
	
	@Column (name="marriage_place", nullable=true)
	public String getMarriagePlace() {
		return marriagePlace;
	}
	public void setMarriagePlace(String marriagePlace) {
		this.marriagePlace = marriagePlace;
	}
	
	@OneToMany (mappedBy="parents")
	public Collection<Person> getChildren() {
		return children;
	}
	public void setChildren(Collection<Person> children) {
		this.children = children;
	}
	
	@Column (name="first_met_date", nullable=true)
	public String getFirstMetDate() {
		return firstMetDate;
	}
	public void setFirstMetDate(String firstMetDate) {
		this.firstMetDate = firstMetDate;
	}
	
	@Column (name="first_met_place", nullable=true)
	public String getFirstMetPlace() {
		return firstMetPlace;
	}
	public void setFirstMetPlace(String firstMetPlace) {
		this.firstMetPlace = firstMetPlace;
	}

	@Column (name="separation_date", nullable=true)
	public String getSeparationDate() {
		return separationDate;
	}
	public void setSeparationDate(String separationDate) {
		this.separationDate = separationDate;
	}
	
	@Column (name="separation_place", nullable=true)
	public String getSeparationPlace() {
		return separationPlace;
	}
	public void setSeparationPlace(String separationPlace) {
		this.separationPlace = separationPlace;
	}

	@Column (name="divorce_date", nullable=true)
	public String getDivorceDate() {
		return divorceDate;
	}
	public void setDivorceDate(String divorceDate) {
		this.divorceDate = divorceDate;
	}

	@Column (name="divorce_place", nullable=true)
	public String getDivorcePlace() {
		return divorcePlace;
	}
	public void setDivorcePlace(String divorcePlace) {
		this.divorcePlace = divorcePlace;
	}
	
	@Column (name="description", nullable = true)
    public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
	    this.description = description;
	}
}