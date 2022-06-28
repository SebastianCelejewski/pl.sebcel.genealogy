package pl.sebcel.genealogy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.sebcel.genealogy.gui.MainFrame;

public class MainProgram {
	
	private static Logger log = LogManager.getLogger(MainProgram.class); 

	public static void main(String[] args) {
		log.info("Starting Genealogy");
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}