package sebcel.genealogia.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "osoby")
public class Osoba {

    private Long id;
    private String imiona;
    private String nazwisko;
    private String plec;

    private String dataUrodzenia;
    private String miejsceUrodzenia;
    private String dataSmierci;
    private String miejsceSmierci;
    private String dataPochowania;
    private String miejscePochowania;

    private Zwiazek zwiazekRodzicow;
    private Collection<Zwiazek> zwiazkiMezowskie;
    private Collection<Zwiazek> zwiazkiZonowskie;

    private String miejsceZamieszkania;

    private String wyksztalcenie;
    private String zawodyWykonywane;
    private Collection<Notatka> notatki;
    private String opis;
    private Set<Dokument> dokumenty;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "plec", nullable = true, length = 1)
    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    @Column(name = "imiona", nullable = true, length = 64)
    public String getImiona() {
        return imiona;
    }

    public void setImiona(String imiona) {
        this.imiona = imiona;
    }

    @Column(name = "nazwisko", nullable = true, length = 128)
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Column(name = "data_urodzenia", nullable = true, length = 64)
    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    @Column(name = "miejsce_urodzenia", nullable = true, length = 128)
    public String getMiejsceUrodzenia() {
        return miejsceUrodzenia;
    }

    public void setMiejsceUrodzenia(String miejsceUrodzenia) {
        this.miejsceUrodzenia = miejsceUrodzenia;
    }

    @Column(name = "data_smierci", nullable = true, length = 64)
    public String getDataSmierci() {
        return dataSmierci;
    }

    public void setDataSmierci(String dataSmierci) {
        this.dataSmierci = dataSmierci;
    }

    @Column(name = "miejsce_smierci", nullable = true, length = 128)
    public String getMiejsceSmierci() {
        return miejsceSmierci;
    }

    public void setMiejsceSmierci(String miejsceSmierci) {
        this.miejsceSmierci = miejsceSmierci;
    }

    @Column(name = "data_pochowania", nullable = true, length = 64)
    public String getDataPochowania() {
        return dataPochowania;
    }

    public void setDataPochowania(String dataPochowania) {
        this.dataPochowania = dataPochowania;
    }

    @Column(name = "miejsce_pochowania", nullable = true, length = 128)
    public String getMiejscePochowania() {
        return miejscePochowania;
    }

    public void setMiejscePochowania(String miejscePochowania) {
        this.miejscePochowania = miejscePochowania;
    }

    @ManyToOne
    @JoinColumn(name = "id_zwiazku_rodzicow")
    public Zwiazek getZwiazekRodzicow() {
        return zwiazekRodzicow;
    }

    public void setZwiazekRodzicow(Zwiazek zwiazekRodzicow) {
        this.zwiazekRodzicow = zwiazekRodzicow;
    }

    @OneToMany(mappedBy = "mezczyzna")
    public Collection<Zwiazek> getZwiazkiMezowskie() {
        return zwiazkiMezowskie;
    }

    public void setZwiazkiMezowskie(Collection<Zwiazek> zwiazkiMezowskie) {
        this.zwiazkiMezowskie = zwiazkiMezowskie;
    }

    @OneToMany(mappedBy = "kobieta")
    public Collection<Zwiazek> getZwiazkiZonowskie() {
        return zwiazkiZonowskie;
    }

    public void setZwiazkiZonowskie(Collection<Zwiazek> zwiazkiZonowskie) {
        this.zwiazkiZonowskie = zwiazkiZonowskie;
    }

    @Column(name = "zawody_wykonywane", nullable = true, length = 255)
    public String getZawodyWykonywane() {
        return zawodyWykonywane;
    }

    public void setZawodyWykonywane(String zawodyWykonywane) {
        this.zawodyWykonywane = zawodyWykonywane;
    }

    @Column(name = "wyksztalcenie", nullable = true)
    public String getWyksztalcenie() {
        return wyksztalcenie;
    }

    public void setWyksztalcenie(String wyksztalcenie) {
        this.wyksztalcenie = wyksztalcenie;
    }

    @OneToMany(mappedBy = "osoba")
    public Collection<Notatka> getNotatki() {
        return notatki;
    }

    public void setNotatki(Collection<Notatka> notatki) {
        this.notatki = notatki;
    }

    @Column(name = "opis", nullable = true)
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Column(name = "miejsce_zamieszkania", nullable = true)
    public String getMiejsceZamieszkania() {
        return miejsceZamieszkania;
    }

    public void setMiejsceZamieszkania(String miejsceZamieszkania) {
        this.miejsceZamieszkania = miejsceZamieszkania;
    }

    @ManyToMany
    @JoinTable (name = "dokumenty_osoby", 
        joinColumns = {@JoinColumn(name = "id_osoby", nullable = false)}, 
        inverseJoinColumns = {@JoinColumn (name = "id_dokumentu", nullable = false) }
    )
    public Set<Dokument> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(Set<Dokument> dokumenty) {
        this.dokumenty = dokumenty;
    }

    public String toString() {
        return getImiona() + " " + getNazwisko() + " (" + getId() + ")";
    }
}