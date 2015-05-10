package sebcel.genealogia.gui;

public class OpcjeRysowania {

    private boolean pokazId;
    private boolean pokazDaneUrodzenia;
    private boolean pokazDaneSmierci;
    private boolean pokazWyksztalcenie;
    private boolean pokazDaneZamieszkania;
    private boolean pokazDanePoznaniaSie;
    private boolean pokazDaneSlubu;
    private boolean pokazDaneRozstaniaSie;
    private boolean pokazDaneRozwodu;
    private int zoom = 1;

    public boolean isPokazId() {
        return pokazId;
    }

    public void setPokazId(boolean pokazId) {
        this.pokazId = pokazId;
    }

    public boolean isPokazDaneUrodzenia() {
        return pokazDaneUrodzenia;
    }

    public void setPokazDaneUrodzenia(boolean pokazDaneUrodzenia) {
        this.pokazDaneUrodzenia = pokazDaneUrodzenia;
    }

    public boolean isPokazDaneSmierci() {
        return pokazDaneSmierci;
    }

    public void setPokazDaneSmierci(boolean pokazDaneSmierci) {
        this.pokazDaneSmierci = pokazDaneSmierci;
    }

    public boolean isPokazWyksztalcenie() {
        return pokazWyksztalcenie;
    }

    public void setPokazWyksztalcenie(boolean pokazWyksztalcenie) {
        this.pokazWyksztalcenie = pokazWyksztalcenie;
    }

    public boolean isPokazDaneZamieszkania() {
        return pokazDaneZamieszkania;
    }

    public void setPokazDaneZamieszkania(boolean pokazDaneZamieszkania) {
        this.pokazDaneZamieszkania = pokazDaneZamieszkania;
    }

    public boolean isPokazDanePoznaniaSie() {
        return pokazDanePoznaniaSie;
    }

    public void setPokazDanePoznaniaSie(boolean pokazDanePoznaniaSie) {
        this.pokazDanePoznaniaSie = pokazDanePoznaniaSie;
    }

    public boolean isPokazDaneSlubu() {
        return pokazDaneSlubu;
    }

    public void setPokazDaneSlubu(boolean pokazDaneSlubu) {
        this.pokazDaneSlubu = pokazDaneSlubu;
    }

    public boolean isPokazDaneRozstaniaSie() {
        return pokazDaneRozstaniaSie;
    }

    public void setPokazDaneRozstaniaSie(boolean pokazDaneRozstaniaSie) {
        this.pokazDaneRozstaniaSie = pokazDaneRozstaniaSie;
    }

    public boolean isPokazDaneRozwodu() {
        return pokazDaneRozwodu;
    }

    public void setPokazDaneRozwodu(boolean pokazDaneRozwodu) {
        this.pokazDaneRozwodu = pokazDaneRozwodu;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}