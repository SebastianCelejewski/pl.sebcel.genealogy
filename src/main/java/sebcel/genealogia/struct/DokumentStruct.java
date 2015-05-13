package sebcel.genealogia.struct;

public class DokumentStruct {

    public Long id;
    public String opis;

    public DokumentStruct(Long id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public String toString() {
        return opis;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DokumentStruct)) {
            return false;
        }
        DokumentStruct dokument = (DokumentStruct) obj;
        return this.id.equals(dokument.id);
    }
}