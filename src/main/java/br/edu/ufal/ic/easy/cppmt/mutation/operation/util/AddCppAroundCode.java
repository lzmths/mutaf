package br.edu.ufal.ic.easy.cppmt.mutation.operation.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Luiz Carvalho
 * 
 * Adds C pre-processor (CPP) around the code. 
 * We can add #ifdef, #ifndef and #endif
 * 
 */
public class AddCppAroundCode {
	
	/**
	 * Creates ifdef or ifdef or other CPP depending on name param.
	 * @param featureStr feature name
	 * @param node node
	 * @param document document
	 * @param name name
	 */
	private void create(String featureStr, Node node, Document document, String name) {
		Element newElementIfdef = document.createElement("cpp:" + name);
		newElementIfdef.setTextContent("#");
		Element newDirective = document.createElement("cpp:directive");
		newDirective.setTextContent(name + " ");
		newElementIfdef.appendChild(newDirective);
		Element newName = document.createElement("name");
		newName.setTextContent(featureStr + " \n");
		newElementIfdef.appendChild(newName);
		node.getParentNode().insertBefore(newElementIfdef, node);
	}

	/**
	 * Create a #ifndef
	 * @param featureStr feature name
	 * @param node node
	 * @param document document
	 */
	public void createIfndef(String featureStr, Node node, Document document) {
		create(featureStr, node, document, "ifndef");
	}
	
	/**
	 * Create a #ifdef
	 * @param featureStr feature name
	 * @param node node
	 * @param document document
	 */
	public void createIfdef(String featureStr, Node currentNode, Document document) {
		create(featureStr, currentNode, document, "ifdef");
	}
	
	public void createEndif(NodeList nList, Node node, Document document) {
		Element newElementIfndef = document.createElement("cpp:endif");
		newElementIfndef.setTextContent("\n#");
		Element newDirective = document.createElement("cpp:directive");
		newDirective.setTextContent("endif");
		newElementIfndef.appendChild(newDirective);
		node.getParentNode().insertBefore(newElementIfndef, node);
	}
	
}
