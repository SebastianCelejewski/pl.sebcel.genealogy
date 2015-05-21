package pl.sebcel.genealogy.gui.pedigree;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class PedigreeTreeComponent extends JComponent {

    public final static long serialVersionUID = 0l;

    private Image image;

    public void setImage(Image image) {
        this.image = image;
        this.setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
    }

    public Image getImage() {
        return this.image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }
}
