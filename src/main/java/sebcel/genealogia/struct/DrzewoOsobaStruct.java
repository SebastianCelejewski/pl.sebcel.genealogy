package sebcel.genealogia.struct;

import java.util.List;

public class DrzewoOsobaStruct {
	
	public Long id;
	public String nazwa;
	public String daneUrodzenia;
	public String daneSmierci;
	public String danePochowania;
	public String daneZamieszkania;
	public String daneZawodu;
	public List<DrzewoRodzinaStruct> rodziny;

}
