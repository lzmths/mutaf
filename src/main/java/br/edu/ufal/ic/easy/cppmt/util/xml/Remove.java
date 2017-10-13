package br.edu.ufal.ic.easy.cppmt.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Remove {
	
	/**
	 * Removes comments inside the main block
	 * @param nodeList removes comments this list and all the elements within
	 */
	private void removeCommentsRecursive(NodeList nodeList) {
		Node node;
		for (int i = 0; i < nodeList.getLength(); ++i) {
			node = nodeList.item(i);
			if (node.getNodeName().equals("comment")) {
				node(node);
			}
			NodeList newChildren = node.getChildNodes();
			removeCommentsRecursive(newChildren);
		}
	}
	
	/**
	 * Removes all comments
	 * @param document document
	 * @return document without comments
	 */
	public Document comments(Document document) {
		removeCommentsRecursive(document.getChildNodes());
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
