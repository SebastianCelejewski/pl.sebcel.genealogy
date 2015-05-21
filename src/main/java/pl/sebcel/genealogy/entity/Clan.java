package pl.sebcel.genealogy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="clans")
public class Clan {

	private Long id;
	private String name;
	private String description;
	private Person root;
	
	@Id
	@Column (name="id")
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column (name="name", nullable=true, length=64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column (name="description", nullable=true, length=512)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne 
	@JoinColumn (name="root_id")
	public Person getRoot() {
		return root;
	}
	public void setRoot(Person root) {
		this.root = root;
	}
}