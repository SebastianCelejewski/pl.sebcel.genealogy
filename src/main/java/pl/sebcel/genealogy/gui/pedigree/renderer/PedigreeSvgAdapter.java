package pl.sebcel.genealogy.gui.pedigree.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
//	private int fontSize;
//	private String fontFamily;
	private Font font;

	@Override
	public void initialize(Font font, int widthOfGeneration) {
		this.font = font;

		try {
			documentBuilder = dbf.newDocumentBuilder();
		} catch (Exception ex) {
			throw new RuntimeException("Failed to inicialize DocumentBuilder: " + ex.getMessage(), ex);
		}
		svgDocument = documentBuilder.newDocument();

		svg = svgDocument.createElement("svg");
		svg.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgDocument.appendChild(svg);
	}

	@Override
	public void drawText(String text, int x, int y, Color color) {
		Element textNode = svg.getOwnerDocument().createElement("text");
		textNode.setAttribute("x", Integer.toString(x));
		textNode.setAttribute("y", Integer.toString(y));
		textNode.setAttribute("text-anchor", "start");
		textNode.setAttribute("fill", getColorAttribute(color));
		textNode.setAttribute("stroke", "none");
		textNode.setAttribute("font-family", font.getFamily());
		textNode.setAttribute("style", "font-size: " + font.getSize() + "px");
		textNode.setTextContent(text);
		svg.appendChild(textNode);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
		Element lineNode = svg.getOwnerDocument().createElement("line");
		lineNode.setAttribute("x1", Integer.toString(x1));
		lineNode.setAttribute("y1", Integer.toString(y1 + font.getSize() / 4));
		lineNode.setAttribute("x2", Integer.toString(x2));
		lineNode.setAttribute("y2", Integer.toString(y2 + font.getSize() / 4));
		lineNode.setAttribute("stroke", getColorAttribute(color));
		lineNode.setAttribute("fill", "none");
		svg.appendChild(lineNode);
	}

	@Override
	public int getTextWidth(String text) {
		return (int) font.getStringBounds(text, new FontRenderContext(new AffineTransform(), false, false)).getWidth();
	}

	@Override
	public Component getResult(Dimension dimension) {
		svg.setAttribute("width", Integer.toString(dimension.width));
		svg.setAttribute("height", Integer.toString(dimension.height));

		JSVGCanvas canvas = new JSVGCanvas();
		File svgFile = null;
		try {
			svgFile = File.createTempFile("pl.sebcel.genealogy", ".svg");
			saveSvg(svgDocument, svgFile.getAbsolutePath());
			canvas.setURI(svgFile.toURI().toString());
			return canvas;
		} catch (Exception ex) {
			throw new RuntimeException("Failed to render SVG file: " + ex.getMessage(), ex);
		}
	}

	private void saveSvg(Document svgDocument, String fileName) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(svgDocument);
			FileOutputStream output = new FileOutputStream(fileName);
			StreamResult result = new StreamResult(output);
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "HTML");
			transformer.transform(source, result);
			output.close();
		} catch (Exception ex) {
			throw new RuntimeException("Failed to save SVG image to file " + fileName + ": " + ex.getMessage(), ex);
		}
	}

	@Override
	public void saveImage(String fileName) throws IOException {
		saveSvg(svgDocument, fileName);
	}

	private String getColorAttribute(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		return "rgb(" + red + "," + green + "," + blue + ")";
	}
}