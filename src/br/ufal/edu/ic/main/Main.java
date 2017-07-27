package br.ufal.edu.ic.main;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File fXmlFile = new File("/home/lmac/Downloads/srcML/bin/vim.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringElementContentWhitespace(false);
		System.out.println(dbFactory.isIgnoringElementContentWhitespace());
		dbFactory.setNamespaceAware(true);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		//Node root = doc.cloneNode(true);
		
		doc.getDocumentElement().normalize();
		
		//System.out.println(doc.getDocumentElement().getNodeName());
		
		//Mutation ifdef to ifndef!
		
		NodeList nList = doc.getDocumentElement().getElementsByTagName("cpp:ifdef");		
		
		for (int i = 0; i < nList.getLength(); ++i) {
			Node node = nList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				//System.out.println(element.getNamespaceURI());
				doc.renameNode(element, element.getNamespaceURI(), "cpp:ifndef");
				NodeList nlDirectives = element.getElementsByTagName("cpp:directive");
				if (nlDirectives.getLength() == 1) {
					Node nDirective = nlDirectives.item(0);
					nDirective.setTextContent("ifndef");
				}
				break;
			}
		}

		//TO FILE 
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "not");

		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);

		String xmlString = result.getWriter().toString();
		System.out.println(xmlString);
	}
	
}
