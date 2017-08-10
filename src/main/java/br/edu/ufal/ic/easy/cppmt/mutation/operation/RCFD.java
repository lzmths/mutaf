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
 * RCFD - Remove Conditional Feature Definition
 *
 */

public class RCFD implements MutationOperator {

	@Override
	public List<Mutation> run(Document document) {
		List<Mutation> lDocument = new ArrayList<Mutation>();
		Document originalDocument = DocumentClone.clone(document);
	
		final int size = document.getDocumentElement().getElementsByTagName("cpp:define").getLength();
		
		long id = 1;
		for (int i = 0; i < size; ++i) {
			NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:define");
			Node parent = nList.item(i).getParentNode();
			parent.removeChild(nList.item(i));
			lDocument.add(new Mutation(document, originalDocument, this, id++));
			document = DocumentClone.clone(originalDocument);
		}
		
		return lDocument;
	}

	@Override
	public String getName() {
		return "RCFD";
	}
	
	
	
}
