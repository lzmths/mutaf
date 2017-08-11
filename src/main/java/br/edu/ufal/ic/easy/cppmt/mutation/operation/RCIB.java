package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 * 
 * @author Luiz Carvalho
 * 
 * RCIB - Removing Complete ifdef Block
 *
 */

public class RCIB implements MutationOperator {

	private int ifdefAndIfndefCount;
	private int endifCount;
	private int selectedIfdefAndIfndef;
	
	@Override
	public String getInfo() {
		return "Remove";
	}
	
	/**
	 * Remove node
	 * @param node node
	 */
	private void removeNode(Node node) {
		Node parentNode = node.getParentNode();
		parentNode.removeChild(node);
	}
	
	/**
	 * Remove Ifdef and Ifndef block depending on selectedIfdefAndifndef field (e.g: first, second...)
	 * @param node root
	 * @return If removed true otherwise false
	 */
	public boolean removeIfdefAndIfndefBlock(Node node) {
		String nodeName = node.getNodeName();
		if (nodeName.equals("cpp:ifdef") || nodeName.equals("cpp:ifndef")) {
			++this.ifdefAndIfndefCount;
			if (this.ifdefAndIfndefCount == this.selectedIfdefAndIfndef) {
				this.endifCount = this.ifdefAndIfndefCount - 1;
			} 
		} else if (nodeName.equals("cpp:endif")) {
			++this.endifCount;
			if (this.ifdefAndIfndefCount >= this.selectedIfdefAndIfndef) {
				if (this.endifCount == this.ifdefAndIfndefCount) {
					removeNode(node);
					return true;
				}
			} 
		} else {
			NodeList nList = node.getChildNodes();
			for (int i = 0; i < nList.getLength(); ++i) {
				Node childNode = nList.item(i);
				if (removeIfdefAndIfndefBlock(childNode)) return true;
				if (this.ifdefAndIfndefCount >= this.selectedIfdefAndIfndef) {
					removeNode(childNode);
				}
			}
		}
		return false;
	}
	
	@Override
	public List<Mutation> run(Document document) {
		List<Mutation> result = new ArrayList<Mutation>();
		Document originalDocument = DocumentClone.clone(document);
		final int ifdefAndIfndefSize = document.getDocumentElement().getElementsByTagName("cpp:ifdef").getLength() 
				+ document.getDocumentElement().getElementsByTagName("cpp:ifndef").getLength();		
		for (int i = 0; i < ifdefAndIfndefSize; ++i) {
			this.ifdefAndIfndefCount = 0;
			this.endifCount = 0;
			this.selectedIfdefAndIfndef = i + 1;
			if (removeIfdefAndIfndefBlock(document.getFirstChild())) {
				result.add(new Mutation(document, originalDocument, this, i));
				document = DocumentClone.clone(originalDocument);
			} else {
				break;
			}
		}
		return result;
	}

	@Override
	public String getName() {
		return "RCIB";
	}

}
