package sebcel.genealogia.entity;

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
@Table (name="zwiazki")
public class Zwiazek {

	private Long id;
	private Osoba mezczyzna;
	private Osoba kobieta;
	private String dataPoznania;
	private String miejscePoznania;
	private String dataSlubu;
	private String miejsceSlubu;
	private String dataRozstania;
	private String miejsceRozstania;
	private String dataRozwodu;
	private String miejsceRozwodu;
	private String opis;
	private Collection<Osoba> dzieci;
	
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
	@JoinColumn (name="id_mezczyzny")
	public Osoba getMezczyzna() {
		return mezczyzna;
	}
	public void setMezczyzna(Osoba mezczyzna) {
		this.mezczyzna = mezczyzna;
	}

	@ManyToOne
	@JoinColumn (name="id_kobiety")
	public Osoba getKobieta() {
		return kobieta;
	}
	public void setKobieta(Osoba kobieta) {
		this.kobieta = kobieta;
	}

	@Column (name="data_slubu", nullable=true)
	public String getDataSlubu() {
		return dataSlubu;
	}
	public void setDataSlubu(String dataSlubu) {
		this.dataSlubu = dataSlubu;
	}
	
	@Column (name="miejsce_slubu", nullable=true)
	public String getMiejsceSlubu() {
		return miejsceSlubu;
	}
	public void setMiejsceSlubu(String miejsceSlubu) {
		this.miejsceSlubu = miejsceSlubu;
	}
	
	@OneToMany (mappedBy="zwiazekRodzicow")
	public Collection<Osoba> getDzieci() {
		return dzieci;
	}
	public void setDzieci(Collection<Osoba> dzieci) {
		this.dzieci = dzieci;
	}
	
	@Column (name="data_poznania", nullable=true)
	public String getDataPoznania() {
		return dataPoznania;
	}
	public void setDataPoznania(String dataPoznania) {
		this.dataPoznania = dataPoznania;
	}
	
	@Column (name="miejsce_poznania", nullable=true)
	public String getMiejscePoznania() {
		return miejscePoznania;
	}
	public void setMiejscePoznania(String miejscePoznania) {
		this.miejscePoznania = miejscePoznania;
	}

	@Column (name="data_rozstania", nullable=true)
	public String getDataRozstania() {
		return dataRozstania;
	}
	public void setDataRozstania(String dataRozstania) {
		this.dataRozstania = dataRozstania;
	}
	
	@Column (name="miejsce_rozstania", nullable=true)
	public String getMiejsceRozstania() {
		return miejsceRozstania;
	}
	public void setMiejsceRozstania(String miejsceRozstania) {
		this.miejsceRozstania = miejsceRozstania;
	}

	@Column (name="data_rozwodu", nullable=true)
	public String getDataRozwodu() {
		return dataRozwodu;
	}
	public void setDataRozwodu(String dataRozwodu) {
		this.dataRozwodu = dataRozwodu;
	}

	@Column (name="miejsce_rozwodu", nullable=true)
	public String getMiejsceRozwodu() {
		return miejsceRozwodu;
	}
	public void setMiejsceRozwodu(String miejsceRozwodu) {
		this.miejsceRozwodu = miejsceRozwodu;
	}
	
	@Column (name="opis", nullable = true)
    public String getOpis() {
        return opis;
    }
	
	public void setOpis(String opis) {
	    this.opis = opis;
	}
}