package pl.sebcel.genealogy.dto;

public class ZwiazekStruct {
	
	public Long id;
	public String mezczyzna;
	public String kobieta;
	
	public String toString() {
		return ""+id+": "+mezczyzna+" i "+kobieta;
	}
	
	public boolean equals(Object object) {
		if (object==this) return true;
		try {
			return id.equals(((ZwiazekStruct) object).id);
		} catch (Exception ex) {
			return false;
		}
	}

}
