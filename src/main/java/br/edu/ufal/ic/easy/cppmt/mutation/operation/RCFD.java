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
 * RCFD - Remove Conditional Feature Definition
 *
 */

public class RCFD implements MutationOperator {

	@Override
	public String getInfo() {
		return "Remove";
	}
	
	@Override
	public void run(Document document, File originalFile) {
		Document originalDocument = DocumentClone.clone(document);
	
		final int size = document.getDocumentElement().getElementsByTagName("cpp:define").getLength();
		
		long id = 1;
		for (int i = 0; i < size; ++i) {
			NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:define");
			Node parent = nList.item(i).getParentNode();
			parent.removeChild(nList.item(i));
			Mutation mutation = new Mutation(document, originalDocument, this, id++);
			mutation.writeToFile(originalFile);
			System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
			document = DocumentClone.clone(originalDocument);
		}		
	}

	@Override
	public String getName() {
		return "RCFD";
	}
	
	
	
}
