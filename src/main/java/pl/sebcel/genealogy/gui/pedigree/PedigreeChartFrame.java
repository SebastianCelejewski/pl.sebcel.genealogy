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
import javax.swing.JOptionPane;
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
    
    private PedigreeRenderer renderer = new PedigreeRenderer();
    private PedigreeAdapter svgAdapter = new PedigreeSvgAdapter();
    private PedigreeAdapter awtAdapter = new PedigreeAwtAdapter();
    
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
    	int fontSize = 12 * chartOptions.getZoom();
        int widthOfTheGeneration = 24;

        Font font = new Font("Times New Roman", Font.PLAIN, fontSize);
        		
        renderer.drawTree(diagramInfo.getRootId(), font, widthOfTheGeneration, svgAdapter, chartOptions);
        Component renderedTree = renderer.drawTree(diagramInfo.getRootId(), font, widthOfTheGeneration, awtAdapter, chartOptions);
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
                    if (extension.equals("svg")) {
                        return true;
                    }
                    return false;
                }

                @Override
                public String getDescription() {
                    return "Supported formats: png, svg";
                }

            });
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    String fileName = fileChooser.getSelectedFile().getCanonicalPath();
                    if (fileName.endsWith(".svg")) {
                    	svgAdapter.saveImage(fileName);
                    } else if (fileName.endsWith(".png")) {
                    	awtAdapter.saveImage(fileName);
                    } else {
                    	JOptionPane.showMessageDialog(this, "Format not supported. Only png and svg formats are supported", "Info", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Wystąpił błąd:\n" + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    

}