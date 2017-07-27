package br.edu.ufal.ic.easy.cppmt.util.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Make a Document clone
 *
 */
public class DocumentClone{
	
	public static Document clone(Document document) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
	        Node root = document.getDocumentElement();
	        Document cloneDocument = db.newDocument();
	        Node copiedRoot = cloneDocument.importNode(root, true);
	        cloneDocument.appendChild(copiedRoot);
	        return cloneDocument;
		} catch (ParserConfigurationException e) {
			System.err.println("Fail in clone document");
		}
        return null;
	}
	
}
