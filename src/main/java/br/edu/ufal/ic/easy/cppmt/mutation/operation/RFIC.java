package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.File;

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
 * RFIC - Removing Feature of ifdef Condition
 *
 */
public class RFIC implements MutationOperator {
	
	@Override
	public String getInfo() {
		return "Remove";
	}
	
	@Override
	public void run(Document document, File originalFile) {
		Document originalDocument = DocumentClone.clone(document);
		NodeList lNodes = document.getDocumentElement().getElementsByTagName("cpp:if");
		long id = 1;
		for (int i = 0; i < lNodes.getLength(); ++i) {
			Node ifNode = lNodes.item(i);
			NodeList lIfChildNodes = ifNode.getChildNodes();
			for (int j = 0; j < lIfChildNodes.getLength(); ++j) {
				if (lIfChildNodes.item(j).getNodeName().equals("expr")) {
					searchAndRemove(document, lIfChildNodes.item(j));
					Mutation mutation = new Mutation(document, originalDocument, this, id++);
					mutation.writeToFile(originalFile);
					System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
					document = DocumentClone.clone(originalDocument);
					lNodes = document.getDocumentElement().getElementsByTagName("cpp:if");
					break;
				}
			}
		}
	}

	/**
	 * Search for call and operator and remove without first call;
	 * @param doc Document
	 * @param expr node from 'cpp:if' node
	 */
	private void searchAndRemove(Document doc, Node expr) {
		NodeList childNodes = expr.getChildNodes();
		int callCount = 0;
		int operatorCount = 0;
		for (int i = 0; i < childNodes.getLength(); ++i) {
			Node node = childNodes.item(i);
			if (node.getNodeName().equals("call")) {
				++callCount;
				if (callCount == 2) {
					expr.removeChild(node);
				}
			} else if (node.getNodeName().equals("operator")) {
				++operatorCount;
				if (operatorCount == 1) {
					expr.removeChild(node);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "RFIC";
	}
	
	
}
