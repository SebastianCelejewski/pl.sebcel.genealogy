package sebcel.genealogia.struct;


public class OsobaStruct {
	
	public Long id;
	public String opis;
	
	public OsobaStruct(Long id, String opis) {
		this.id = id;
		this.opis = opis;
	}

	public String toString() {
		return opis;
	}

	@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		if (!(obj instanceof OsobaStruct)) return false;
		OsobaStruct osoba = (OsobaStruct) obj;
		return this.id.equals(osoba.id);
	}
}