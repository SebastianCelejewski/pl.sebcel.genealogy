package pl.sebcel.genealogy.gui.pedigree.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PedigreeSvgAdapter implements PedigreeAdapter {

    private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder;
    private Document svgDocument;
    private Element svg;

	@Override
	public void initialize(int fontSize, int widthOfGeneration) {
		try {
        	documentBuilder = dbf.newDocumentBuilder();
        } catch (Exception ex) {
        	throw new RuntimeException("Failed to inicialize DocumentBuilder: " + ex.getMessage(), ex);
        }
        svgDocument = documentBuilder.newDocument();

        svg = svgDocument.createElement("svg");
        svg.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        svg.setAttribute("width", "10000");
        svg.setAttribute("height", "10000");
        svgDocument.appendChild(svg);
	}

	@Override
	public int getFontSize() {
		return 12;
	}

	@Override
	public void drawText(String text, int x, int y, int fontSize, Color color) {
		Element nameNode = svg.getOwnerDocument().createElement("text");
		nameNode.setAttribute("x", Integer.toString(x));
		nameNode.setAttribute("y", Integer.toString(y));
		nameNode.setAttribute("text-anchor", "start");
		nameNode.setAttribute("fill", "black");
		nameNode.setAttribute("style", "font-size: " + fontSize + "px");
		nameNode.setTextContent(text);
		svg.appendChild(nameNode);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        Element line = svg.getOwnerDocument().createElement("line");
        line.setAttribute("x1", Integer.toString(x1));
        line.setAttribute("y1", Integer.toString(y1));
        line.setAttribute("x2", Integer.toString(x2));
        line.setAttribute("y2", Integer.toString(y2));
        line.setAttribute("stroke", "grey");
        svg.appendChild(line);
	}

	@Override
	public int getTextWidth(String text) {
		return 10; // TODO: Calculate it somehow
	}

	@Override
	public Component getResult(Dimension dimension) {
        JSVGCanvas canvas = new JSVGCanvas();
        URI uri = saveSvg(svgDocument);
        canvas.setURI(uri.toString());
        return canvas;
	}
	
    private URI saveSvg(Document svgDocument) {
		File svgFile = null;
    	try {
    		svgFile = File.createTempFile("pl.sebcel.genealogy", ".svg");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(svgDocument);
            FileOutputStream output = new FileOutputStream(svgFile);
            StreamResult result = new StreamResult(output);
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "HTML");
            transformer.transform(source, result);
            output.close();
            return svgFile.toURI();
    	} catch (Exception ex) {
    		throw new RuntimeException("Failed to save SVG image to file " + svgFile.getAbsolutePath() + ": " + ex.getMessage(), ex);
    	}
    }	
}