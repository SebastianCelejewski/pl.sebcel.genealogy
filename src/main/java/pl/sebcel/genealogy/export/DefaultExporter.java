package pl.sebcel.genealogy.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class DefaultExporter implements DataExporter {
	
	private String xmlData;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public DefaultExporter(String xmlData) {
		if (xmlData==null) {
			throw new IllegalArgumentException("XML data can not be null.");
		}
		this.xmlData = xmlData;
	}
	
	public void exportData(File file) {
		if (file==null) {
			throw new IllegalArgumentException("File can not be null.");
		}
		logger.debug("Exporting data to file "+file+".");
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(xmlData);
			fw.close();
		} catch (IOException ex) {
			throw new RuntimeException("Error exporting data: "+ex.getMessage(), ex);
		} 
	}
}
