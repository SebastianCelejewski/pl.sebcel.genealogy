package pl.sebcel.genealogy.gui.pedigree.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

public interface PedigreeAdapter {
	
	public void initialize(Font font, int widthOfGeneration);
	
	public void drawText(String text, int x, int y, Color color);
	
	public void drawLine(int x1, int y1, int x2, int y2, Color color);
	
	public int getTextWidth(String text);
	
	public Component getResult(Dimension dimension);
	
	public void saveImage(String fileName) throws IOException;
	
}