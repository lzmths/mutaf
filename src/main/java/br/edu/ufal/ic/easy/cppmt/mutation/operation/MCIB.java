package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.File;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
 * MCIB - Moving Code around ifdef Blocks
 *
 */
public class MCIB implements MutationOperator{

	private int ifdefAndIfndefAndIfCount;
	private int endifCount;
	private int selectedIfdefAndIfndefAndIf;
	private Node beforeIfdefNodeSelected;
	private Node IfdefNodeSelected;
	private Node blankLine;
	
	@Override
	public String getInfo() {
		return "Moving";
	}

	/**
	 * Removes a node
	 * @param node node
	 */
	private void removeNode(Node node) {
		Node parentNode = node.getParentNode();
		parentNode.removeChild(node);
	}
	
	/**
	 * Moving Code around ifdef Blocks
	 * @param node node
	 * @param beforeSiblingNode before sibling node
	 * @param afterSiblingNode after or next sibling node
	 * @param before true if moving code before ifdef block. Otherwise, moving code after ifdef block
	 * @return true if MCIB has been done. Otherwise, false.
	 */
	private boolean movingIfdefAndIfndefAndIf(Node node, Node beforeSiblingNode, Node afterSiblingNode, boolean before) {
		String nodeName = node.getNodeName();
		if (nodeName.equals("cpp:ifdef") || nodeName.equals("cpp:ifndef") || nodeName.equals("cpp:if")) {
			++this.ifdefAndIfndefAndIfCount;
			if (this.ifdefAndIfndefAndIfCount == this.selectedIfdefAndIfndefAndIf) {
				this.endifCount = this.ifdefAndIfndefAndIfCount - 1;
				
				if (beforeSiblingNode == null) return false;
				this.beforeIfdefNodeSelected = beforeSiblingNode.cloneNode(true);
				this.IfdefNodeSelected = node;
				if (before) {
					removeNode(beforeSiblingNode);
				}
			} 
		} else if (nodeName.equals("cpp:endif")) {
			++this.endifCount;
			if (this.ifdefAndIfndefAndIfCount >= this.selectedIfdefAndIfndefAndIf) {
				if (this.endifCount == this.ifdefAndIfndefAndIfCount) {
					if (afterSiblingNode == null) return false;
					if (before) {
						try {
							if (this.beforeIfdefNodeSelected == null || afterSiblingNode == null) return false;
							if (!this.beforeIfdefNodeSelected.getOwnerDocument().equals(afterSiblingNode.getOwnerDocument())) return false;
							node.getParentNode().insertBefore(this.beforeIfdefNodeSelected, afterSiblingNode);
							node.getParentNode().insertBefore(this.blankLine.cloneNode(true), afterSiblingNode);
							return true;
						} catch (DOMException e) {
							System.err.println(e.getMessage());
						}
					} else {
						try {
							if (afterSiblingNode == null || this.IfdefNodeSelected == null) return false;
							if (!this.IfdefNodeSelected.getOwnerDocument().equals(afterSiblingNode.getOwnerDocument())) return false;
							node.getParentNode().insertBefore(afterSiblingNode.cloneNode(true), this.IfdefNodeSelected);
							node.getParentNode().insertBefore(this.blankLine.cloneNode(true), this.IfdefNodeSelected);
							removeNode(afterSiblingNode);
							return true;
						} catch(DOMException e) {
							System.err.println(e.getMessage());
						}
					}
				}
			} 
		} else {
			NodeList nList = node.getChildNodes();
			Node bChildNodeSibling = null;
			Node childNode = null;
			Node aChildNodeSibling = null;
			Node currentChildNode;
			for (int i = 0; i < nList.getLength(); ++i) {
				currentChildNode = nList.item(i);
				if (currentChildNode.getNodeName().equals("#text")) {
					if (movingIfdefAndIfndefAndIf(currentChildNode, null, null, before)) return true;
					else continue;
				} else {
					if (childNode == null) {
						childNode = currentChildNode;
					} else if (aChildNodeSibling == null) {
						aChildNodeSibling = currentChildNode;
					} else {
						bChildNodeSibling = childNode;
						childNode = aChildNodeSibling;
						aChildNodeSibling = currentChildNode;
					}
					if (movingIfdefAndIfndefAndIf(childNode, bChildNodeSibling, aChildNodeSibling, before)) return true;
				}
			}
			if (aChildNodeSibling != null) {
				if (movingIfdefAndIfndefAndIf(aChildNodeSibling, null, null, before)) return true;
			}
		}
		return false;
	}
	
	/**
	 * Reset fields used by recursion
	 * @param index selected ifdef and others
	 * @param doc Document
	 */
	private void resetFields(int index, Document doc) {
		this.ifdefAndIfndefAndIfCount = 0;
		this.endifCount = 0;
		this.selectedIfdefAndIfndefAndIf = index;
		this.blankLine = (Node) doc.createElement("text");
		this.blankLine.setNodeValue("\n");
		this.blankLine.setTextContent("\n");
	}
	
	@Override
	public void run(Document document, File originalFile) {
		Remove remove = new Remove();
		document = remove.comments(document);
		Document originalDocument = DocumentClone.clone(document);
		Element elem = document.getDocumentElement();
		final int candidates = elem.getElementsByTagName("cpp:ifdef").getLength() + elem.getElementsByTagName("cpp:ifndef").getLength() +
				elem.getElementsByTagName("cpp:if").getLength();
		for (int i = 0, j = 0; i < candidates; ++i) {
			resetFields(i + 1, document);
			if (movingIfdefAndIfndefAndIf(document.getFirstChild(), null, null, true)) {
				Mutation mutation = new Mutation(document, originalDocument, this, ++j);
				mutation.writeToFile(originalFile);
				System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
			}
			document = DocumentClone.clone(originalDocument);
			
			resetFields(i + 1, document);
			if (movingIfdefAndIfndefAndIf(document.getFirstChild(), null, null, false)) {
				Mutation mutation = new Mutation(document, originalDocument, this, ++j);
				mutation.writeToFile(originalFile);
				System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
			}
			document = DocumentClone.clone(originalDocument);
		}
	}

	@Override
	public String getName() {
		return "MCIB";
	}

}
