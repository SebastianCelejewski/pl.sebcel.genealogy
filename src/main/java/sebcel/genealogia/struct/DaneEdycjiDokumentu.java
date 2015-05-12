package sebcel.genealogia.struct;

import java.util.List;

public class DaneEdycjiDokumentu {

    private Long id;
    private String tytul;
    private String symbol;
    private String opis;
    private List<ReferenceListElement> osoby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public List<ReferenceListElement> getOsoby() {
        return osoby;
    }

    public void setOsoby(List<ReferenceListElement> osoby) {
        this.osoby = osoby;
    }
}