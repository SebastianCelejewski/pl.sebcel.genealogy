package pl.sebcel.genealogy.gui.pedigree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.gui.IDrawOptionsListener;
import pl.sebcel.genealogy.gui.pedigree.renderer.PedigreeAdapter;
import pl.sebcel.genealogy.gui.pedigree.renderer.PedigreeAwtAdapter;
import pl.sebcel.genealogy.gui.pedigree.renderer.PedigreeRenderer;
import pl.sebcel.genealogy.gui.pedigree.renderer.PedigreeSvgAdapter;

public class PedigreeChartFrame extends JFrame implements ActionListener, IDrawOptionsListener {

    public final static long serialVersionUID = 0l;

    private final JPanel buttonsPanel = new JPanel();
    private final PedigreeChartOptionsPanel chartOptionsPanel = new PedigreeChartOptionsPanel();
    private final JButton saveButton = new JButton("Zapisz");
    private final JButton closeButton = new JButton("Zamknij");
    private final JButton optionsButton = new JButton("Opcje");
    private final JScrollPane scrollPane = new JScrollPane(new JLabel("Proszę czekać...", JLabel.CENTER));

    private DiagramInfoStruct diagramInfo;
    
    public PedigreeChartFrame() {
        buttonsPanel.add(saveButton);
        buttonsPanel.add(closeButton);
        buttonsPanel.add(optionsButton);

        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        optionsButton.addActionListener(this);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        chartOptionsPanel.setDrawOptionsListener(this);
    }

    public void drawPedigreeChart(final DiagramInfoStruct diagramInfo) {
        this.setSize(800, 600);
        this.setLocation(100, 100);
        this.setTitle(diagramInfo.getDescription());
        this.setVisible(true);
        this.diagramInfo = diagramInfo;

        new Thread() {
            public void run() {
                draw(new PedigreeChartOptions());
            }
        }.start();
    }

    @Override
    public void updateDrawing(PedigreeChartOptions chartOptions) {
        draw(chartOptions);
    }

    private void draw(PedigreeChartOptions chartOptions) {
        System.out.println("rysuję");
        PedigreeRenderer renderer = new PedigreeRenderer();
//        PedigreeAdapter adapter = new PedigreeAwtAdapter();
        PedigreeAdapter adapter = new PedigreeSvgAdapter();

        Font font = new Font("Times", Font.PLAIN, 12 * chartOptions.getZoom());
        int width = 24;
        		
        Component renderedTree = renderer.drawTree(diagramInfo.getRootId(), font, width, adapter, chartOptions);
        scrollPane.setViewportView(renderedTree);
        
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(closeButton)) {
            this.setVisible(false);
        }

        if (e.getSource().equals(optionsButton)) {
            chartOptionsPanel.setVisible(true);
        }

        if (e.getSource().equals(saveButton)) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                	String fileName = f.getName();
                	String extension = "";
                	int lastDot = fileName.lastIndexOf('.');
                	if (lastDot != -1) {
                		extension = fileName.substring(lastDot + 1).toLowerCase();
                	}

                    if (extension.equals("png")) {
                        return true;
                    }
                    if (extension.equals("jpg")) {
                        return true;
                    }
                    if (extension.equals("gif")) {
                        return true;
                    }
                    if (extension.equals("bmp")) {
                        return true;
                    }
                    if (extension.equals("png")) {
                        return true;
                    }
                    return false;
                }

                @Override
                public String getDescription() {
                    return "Supported graphics formats";
                }

            });
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
//                try {
//                    String filename = fileChooser.getSelectedFile().getCanonicalPath();
//                    Image image = treeComponent.getImage();
//                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_RGB);
//                	bufferedImage.getGraphics().drawImage(image, 0, 0, null);
//                    ImageIO.write(bufferedImage, "png", new File(filename));
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(this, "Wystąpił błąd:\n" + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
//                }
            }
        }
    }
    

}