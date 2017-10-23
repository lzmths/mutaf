package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;
import br.edu.ufal.ic.easy.cppmt.util.xml.Remove;

/**
 * 
 * @author Luiz Carvalho
 *
 * RCFD - Remove Conditional Feature Definition
 *
 */

public class RCFD implements MutationOperator {

	@Override
	public String getInfo() {
		return "Remove";
	}

	private int conditionNesting;
	private int orderToRemoveDefine;
	private int currentOrderDefine;
	
	/**
	 * Remove define inside condition(#ifdef, #ifndef and #if)
	 * @param doc document
	 * @param currentNode current node inspected
	 * @return true if remove define inside condition. Otherwise false.
	 */
	private boolean removeDefineInsideCondition(Document doc, Node currentNode) {	
		if (currentNode == null) {
			NodeList nodeList = doc.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); ++i) {
				conditionNesting = 0;
				if (removeDefineInsideCondition(doc, nodeList.item(i))) {
					return true;
				}
			}
		} else {
			if (currentNode.getNodeName().equals("cpp:define")) {
				++currentOrderDefine;
				if ((orderToRemoveDefine == currentOrderDefine) && (conditionNesting > 0)) {
					Remove remove = new Remove();
					remove.node(currentNode);
					return true;
				} else if (orderToRemoveDefine < currentOrderDefine) {
					return false;
				}
			} else {
				NodeList nodeList = currentNode.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); ++i) {
					Node childNode = nodeList.item(i);
					String childNodeName = childNode.getNodeName();
					if (childNodeName.equals("cpp:ifdef") || childNodeName.equals("cpp:ifndef") 
							|| childNodeName.equals("cpp:if")) {
						++conditionNesting;
					} else if (childNodeName.equals("cpp:endif"))  {
						--conditionNesting;
					}
					if (removeDefineInsideCondition(doc, childNode)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void run(Document document, File originalFile) {
		Document originalDocument = DocumentClone.clone(document);
		final int candidatesSize = document.getDocumentElement().getElementsByTagName("cpp:define").getLength();
		int id = 0;
		for (int i = 0; i < candidatesSize; ++i) {
			orderToRemoveDefine = i + 1;
			currentOrderDefine = 0;
			if (removeDefineInsideCondition(document, null)) {
				Mutation mutation = new Mutation(document, originalDocument, this, ++id);
				mutation.writeToFile(originalFile);
				System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
				document = DocumentClone.clone(originalDocument);
			}
		}
	}

	@Override
	public String getName() {
		return "RCFD";
	}
	
}
