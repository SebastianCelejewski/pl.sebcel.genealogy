package pl.sebcel.genealogy.gui.component.list.model;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CustomFileFilter extends FileFilter {

	private String description;
	private String[] extensions;
	
	public CustomFileFilter(String description, String... extensions) {
		this.description = description;
		this.extensions = extensions;
	}
	
	@Override
	public boolean accept(File f) {
		String end = getExtension(f);
		for (String extension : extensions) {
			if (extension.equalsIgnoreCase(end)) return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public static String getExtension(File f) {
		if (f==null) return "";
		if (f.getName()==null) return "";
		if (f.getName().length()==0) return "";
		if (f.getName().indexOf('.')==-1) return "";
		if (f.getName().endsWith(".")) return "";
		String extension = f.getName().substring(f.getName().indexOf('.')+1);
		System.err.println(""+f.getName()+": "+extension);
		return extension.toLowerCase();
	}
}
