package sebcel.genealogia.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dokumenty")
public class Dokument {

    private Long id;
    private String tytul;
    private String symbol;
    private String opis;
    private Set<Osoba> osoby;

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
    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    @Column(name = "symbol", nullable = true, length = 255)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "opis", nullable = true)
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @ManyToMany(mappedBy = "dokumenty")
    public Set<Osoba> getOsoby() {
        return osoby;
    }

    public void setOsoby(Set<Osoba> osoby) {
        this.osoby = osoby;
    }
}