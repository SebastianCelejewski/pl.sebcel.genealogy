package sebcel.genealogia.struct;

import java.util.List;

public class DaneEdycjiOsobyStruct {

	public Long id;
	public String imiona;
	public String nazwisko;
	public String dataUrodzenia;
	public String miejsceUrodzenia;
	public String dataSmierci;
	public String miejsceSmierci;
	public String dataPochowania;
	public String miejscePochowania;
	public ZwiazekStruct rodzice;
	public List<RodzinaStruct> rodziny;
	public String plec;
	public String wyksztalcenie;
	public String zawodyWykonywane;
	public String opis;
	public String miejsceZamieszkania;
}