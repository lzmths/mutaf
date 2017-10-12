package br.edu.ufal.ic.easy.cppmt.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Remove {
	
	/**
	 * Removes all comments
	 * @param document document
	 * @return document without comments
	 */
	public Document comments(Document document) {
		NodeList nodeList = document.getDocumentElement().getElementsByTagName("comment");
		for (int i = 0; i < nodeList.getLength(); ++i) {
			node(nodeList.item(i));
		}
		return document;
	}
	
	/**
	 * Removes a node
	 * @param node node
	 */
	public void node(Node node) {
		Node parentNode = node.getParentNode();
		parentNode.removeChild(node);
	}
}
