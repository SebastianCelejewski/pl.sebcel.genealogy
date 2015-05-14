package pl.sebcel.genealogy.dto;

import java.util.List;
import java.util.Set;

public class DaneEdycjiOsoby {

    private Long id;
    private String imiona;
    private String nazwisko;
    private String dataUrodzenia;
    private String miejsceUrodzenia;
    private String dataSmierci;
    private String miejsceSmierci;
    private String dataPochowania;
    private String miejscePochowania;
    private ZwiazekStruct rodzice;
    private List<RodzinaStruct> rodziny;
    private Set<ReferenceListElement> dokumenty;
    private String plec;
    private String wyksztalcenie;
    private String zawodyWykonywane;
    private String opis;
    private String miejsceZamieszkania;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImiona() {
        return imiona;
    }

    public void setImiona(String imiona) {
        this.imiona = imiona;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getMiejsceUrodzenia() {
        return miejsceUrodzenia;
    }

    public void setMiejsceUrodzenia(String miejsceUrodzenia) {
        this.miejsceUrodzenia = miejsceUrodzenia;
    }

    public String getDataSmierci() {
        return dataSmierci;
    }

    public void setDataSmierci(String dataSmierci) {
        this.dataSmierci = dataSmierci;
    }

    public String getMiejsceSmierci() {
        return miejsceSmierci;
    }

    public void setMiejsceSmierci(String miejsceSmierci) {
        this.miejsceSmierci = miejsceSmierci;
    }

    public String getDataPochowania() {
        return dataPochowania;
    }

    public void setDataPochowania(String dataPochowania) {
        this.dataPochowania = dataPochowania;
    }

    public String getMiejscePochowania() {
        return miejscePochowania;
    }

    public void setMiejscePochowania(String miejscePochowania) {
        this.miejscePochowania = miejscePochowania;
    }

    public ZwiazekStruct getRodzice() {
        return rodzice;
    }

    public void setRodzice(ZwiazekStruct rodzice) {
        this.rodzice = rodzice;
    }

    public List<RodzinaStruct> getRodziny() {
        return rodziny;
    }

    public void setRodziny(List<RodzinaStruct> rodziny) {
        this.rodziny = rodziny;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getWyksztalcenie() {
        return wyksztalcenie;
    }

    public void setWyksztalcenie(String wyksztalcenie) {
        this.wyksztalcenie = wyksztalcenie;
    }

    public String getZawodyWykonywane() {
        return zawodyWykonywane;
    }

    public void setZawodyWykonywane(String zawodyWykonywane) {
        this.zawodyWykonywane = zawodyWykonywane;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getMiejsceZamieszkania() {
        return miejsceZamieszkania;
    }

    public void setMiejsceZamieszkania(String miejsceZamieszkania) {
        this.miejsceZamieszkania = miejsceZamieszkania;
    }

    public Set<ReferenceListElement> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(Set<ReferenceListElement> dokumenty) {
        this.dokumenty = dokumenty;
    }
}