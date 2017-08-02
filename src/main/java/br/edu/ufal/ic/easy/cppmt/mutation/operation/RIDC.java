package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 * @author Luiz Carvalho
 * 
 * RIDC - Remove ifdef Condition.
 * Moreover 
 *  1) Remove 'ifndef'
 *  2) Remove 'else' condition when associated with 'ifdef' or 'ifndef'
 *
 */
public class RIDC implements MutationOperator {

	private int ifdefOrIfndefCount;
	private int endifCount;
	private int elseCount;
	private int ifdefSelected;
	
	/**
	 * Remove ifdef or ifndef.
	 * @param document document
	 * @param currentNode current node
	 * @return if removed true otherwise false
	 */
	private boolean removeIfdefOrIfndef(Document document, Node currentNode, boolean ifdefRemoved) {
		NodeList childNodes = currentNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); ++i) {
			Node childNode = childNodes.item(i);
			if (childNode.getNodeName().equals("cpp:ifdef") || childNode.getNodeName().equals("cpp:ifndef")) {
				++this.ifdefOrIfndefCount;
				if (this.ifdefSelected == this.ifdefOrIfndefCount) {
					removeNode(childNode);
					this.endifCount = this.ifdefOrIfndefCount - 1;
					this.elseCount = endifCount;
				}
			} else if (childNode.getNodeName().equals("cpp:endif")) {
				++this.endifCount;
				if (this.endifCount > this.ifdefOrIfndefCount) {
					System.err.println("There is more #endif than #ifdef");
					return false;
				} else if (this.ifdefOrIfndefCount == this.endifCount && this.ifdefSelected <= this.ifdefOrIfndefCount) {
					removeNode(childNode);
					return true;
				}
			} else if (childNode.getNodeName().equals("cpp:else")) {
				++this.elseCount;
				if (this.elseCount > this.ifdefOrIfndefCount) {
					System.err.println("There is more #else than #ifdef");
					return false;
				} else if (this.ifdefOrIfndefCount == this.elseCount && this.ifdefSelected <= this.ifdefOrIfndefCount) {
					removeNode(childNode);
				}
			} else {
				if (removeIfdefOrIfndef(document, childNode, ifdefRemoved)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<Document> run(Document originalDocument) {
		List<Document> lDocument = new ArrayList<Document>();
		final int ifdefAndIfndefSize = originalDocument.getDocumentElement().getElementsByTagName("cpp:ifdef").getLength() + 
				originalDocument.getDocumentElement().getElementsByTagName("cpp:ifndef").getLength();
		final int endifSize = originalDocument.getDocumentElement().getElementsByTagName("cpp:endif").getLength();
		final int startIfdefSelected = 1;
		
		if (endifSize != ifdefAndIfndefSize) {
			System.err.println("Problem with size of ifdef, ifndef and endif");
			return lDocument;
		}
		
		for (int i = 0; i < ifdefAndIfndefSize; ++i) {
			Document document = DocumentClone.clone(originalDocument);
			this.ifdefOrIfndefCount = 0;
			this.elseCount = 0;
			this.endifCount = 0;
			this.ifdefSelected = startIfdefSelected + i;
			if (removeIfdefOrIfndef(document, document.getFirstChild(), false)) {
				lDocument.add(document);
				document = DocumentClone.clone(originalDocument);;
			} else {
				return lDocument;
			}
		}
		return lDocument;
	}
	
	/**
	 * Remove node
	 * @param node node
	 */
	private void removeNode(Node node) {
		Node parentNode = node.getParentNode();
		parentNode.removeChild(node);
	}

	@Override
	public String getName() {
		return "RIDC";
	}

}
