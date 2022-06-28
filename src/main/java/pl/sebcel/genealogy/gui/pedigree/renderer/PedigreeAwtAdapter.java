package pl.sebcel.genealogy.gui.pedigree.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import pl.sebcel.genealogy.gui.pedigree.PedigreeTreeComponent;

public class PedigreeAwtAdapter implements PedigreeAdapter {

	private Image image;
	private Graphics g;
	private Dimension bufferDimensions;

	@Override
	public int getFontSize() {
		return 12;
	}

	public void initialize(int fontSize, int widthOfGeneration) {
		bufferDimensions = new Dimension(15 * fontSize * widthOfGeneration, 1000 * fontSize);
		image = new BufferedImage(bufferDimensions.width, bufferDimensions.height, BufferedImage.TYPE_BYTE_INDEXED);
		g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bufferDimensions.width, bufferDimensions.height);
	}

	public Component getResult(Dimension dimension) {
		BufferedImage newImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_BYTE_INDEXED);
		newImage.getGraphics().drawImage(image, 0, 0, new ImageObserver() {
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return true;
			}
		});

		PedigreeTreeComponent result = new PedigreeTreeComponent();
		result.setImage(newImage);
		return result;
	}

	public void drawText(String text, int x, int y, int fontSize, Color color) {
		g.setColor(color);
		g.setFont(new Font(g.getFont().getName(), Font.BOLD, fontSize));
		g.drawString(text, x, y);
		g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public int getTextWidth(String text) {
		return (int) g.getFont().getStringBounds(text, new FontRenderContext(new AffineTransform(), false, false)).getWidth();
	}
}