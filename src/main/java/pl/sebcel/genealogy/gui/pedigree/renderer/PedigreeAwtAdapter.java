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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pl.sebcel.genealogy.gui.pedigree.PedigreeTreeComponent;

public class PedigreeAwtAdapter implements PedigreeAdapter {

	private Image image;
	private Graphics g;
	private Dimension bufferDimensions;

	private PedigreeTreeComponent treeComponent = new PedigreeTreeComponent();
	
	public void initialize(Font font, int widthOfGeneration) {
		bufferDimensions = new Dimension(15 * font.getSize() * widthOfGeneration, 1500 * font.getSize());
		image = new BufferedImage(bufferDimensions.width, bufferDimensions.height, BufferedImage.TYPE_BYTE_INDEXED);
		g = image.getGraphics();
		g.setFont(font);
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
		
		treeComponent.setImage(newImage);
		return treeComponent;
	}

	public void drawText(String text, int x, int y, Color color) {
		g.setColor(color);
		g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize()));
		g.drawString(text, x, y);
		g.setFont(new Font(g.getFont().getName(), Font.PLAIN, g.getFont().getSize()));
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

	@Override
	public void saveImage(String fileName) throws IOException {
        Image image = treeComponent.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(treeComponent), image.getHeight(treeComponent), BufferedImage.TYPE_INT_RGB);
    	bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        ImageIO.write(bufferedImage, "png", new File(fileName));
	}
}