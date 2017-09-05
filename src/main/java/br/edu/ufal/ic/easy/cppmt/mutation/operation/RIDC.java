package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 * @author Luiz Carvalho
 * 
 * RIDC - Remove ifdef Condition.
 * Moreover 
 *  1) Remove 'ifndef'
 *  2) Remove 'if'
 *  3) Remove 'else' and 'else' condition when associated with 'ifdef' or 'ifndef'
 */
public class RIDC implements MutationOperator {

	private int ifdefOrIfndefOrIfCount;
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
			String childNodeName = childNode.getNodeName();
			if (childNodeName.equals("cpp:ifdef") || childNodeName.equals("cpp:ifndef") || childNodeName.equals("cpp:if")) {
				++this.ifdefOrIfndefOrIfCount;
				if (this.ifdefSelected == this.ifdefOrIfndefOrIfCount) {
					removeNode(childNode);
					this.endifCount = this.ifdefOrIfndefOrIfCount - 1;
					this.elseCount = endifCount;
				}
			} else if (childNodeName.equals("cpp:endif")) {
				++this.endifCount;
				if (this.endifCount > this.ifdefOrIfndefOrIfCount) {
					System.err.println("There is more #endif than #ifdef");
					return false;
				} else if (this.ifdefOrIfndefOrIfCount == this.endifCount && this.ifdefSelected <= this.ifdefOrIfndefOrIfCount) {
					removeNode(childNode);
					return true;
				}
			} else if (childNodeName.equals("cpp:else")) {
				++this.elseCount;
				if (this.elseCount > this.ifdefOrIfndefOrIfCount) {
					System.err.println("There is more #else than #ifdef");
					return false;
				} else if (this.ifdefOrIfndefOrIfCount == this.elseCount && this.ifdefSelected <= this.ifdefOrIfndefOrIfCount) {
					removeNode(childNode);
				}
			} else if (childNodeName.equals("cpp:elif")) {
				if ((this.ifdefOrIfndefOrIfCount - 1) == this.endifCount && this.ifdefSelected <= this.ifdefOrIfndefOrIfCount) {
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
	public void run(Document originalDocument, File originalFile) {
		final int ifdefAndIfndefSize = originalDocument.getDocumentElement().getElementsByTagName("cpp:ifdef").getLength() + 
				originalDocument.getDocumentElement().getElementsByTagName("cpp:ifndef").getLength() + 
				originalDocument.getDocumentElement().getElementsByTagName("cpp:if").getLength();
		final int endifSize = originalDocument.getDocumentElement().getElementsByTagName("cpp:endif").getLength();
		final int startIfdefSelected = 1;
		
		if (endifSize != ifdefAndIfndefSize) {
			System.err.println("Problem with size of ifdef, ifndef and endif");
			return;
		}
		
		for (int i = 0; i < ifdefAndIfndefSize; ++i) {
			Document document = DocumentClone.clone(originalDocument);
			this.ifdefOrIfndefOrIfCount = 0;
			this.elseCount = 0;
			this.endifCount = 0;
			this.ifdefSelected = startIfdefSelected + i;
			if (removeIfdefOrIfndef(document, document.getFirstChild(), false)) {
				Mutation mutation = new Mutation(document, originalDocument, this, i + 1);
				mutation.writeToFile(originalFile);
				System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
				document = DocumentClone.clone(originalDocument);;
			} else {
				return;
			}
		}
		return;
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

	@Override
	public String getInfo() {
		return "Remove";
	}
	
}
