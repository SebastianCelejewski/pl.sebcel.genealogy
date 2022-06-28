package pl.sebcel.genealogy.gui.pedigree.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public interface PedigreeAdapter {
	
	public void initialize(int fontSize, int widthOfGeneration);
	
	public int getFontSize();
	
	public void drawText(String text, int x, int y, int fontSize, Color color);
	
	public void drawLine(int x1, int y1, int x2, int y2, Color color);
	
	public int getTextWidth(String text);
	
	public Component getResult(Dimension dimension);
	
}