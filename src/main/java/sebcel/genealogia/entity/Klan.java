package sebcel.genealogia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="klany")
public class Klan {

	private Long id;
	private String nazwa;
	private String opis;
	private Osoba korzen;
	
	@Id
	@Column (name="id")
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column (name="nazwa", nullable=true, length=64)
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	@Column (name="opis", nullable=true, length=512)
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	@ManyToOne 
	@JoinColumn (name="korzen_id")
	public Osoba getKorzen() {
		return korzen;
	}
	public void setKorzen(Osoba korzen) {
		this.korzen = korzen;
	}
	
}