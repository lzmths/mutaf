package br.ufal.edu.ic.easy.mutation.operation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufal.edu.ic.easy.mutation.MutationOperator;
import br.ufal.edu.ic.easy.util.xml.DocumentClone;

/**
 * 
 * @author Luiz Carvalho
 *
 * RCFD - Remove Conditional Feature Definition
 *
 */

public class RCFD implements MutationOperator {

	@Override
	public List<Document> run(Document document) {
		List<Document> lDocument = new ArrayList<Document>();
		Document originalDocument = DocumentClone.clone(document);
	
		final int size = document.getDocumentElement().getElementsByTagName("cpp:define").getLength();

		for (int i = 0; i < size; ++i) {
			NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:define");
			Node parent = nList.item(i).getParentNode();
			parent.removeChild(nList.item(i));
			lDocument.add(document);
			document = DocumentClone.clone(originalDocument);
		}
		
		return lDocument;
	}

	@Override
	public String getName() {
		return "RCFD";
	}
	
	
	
}
