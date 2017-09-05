package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;


/**
 * 
 * @author Luiz Carvalho
 * 
 * RIND - Replacing ifdef Directive with ifndef Directive from the paper Mutation Operators for Preprocessor-Based Variability
 * It is the implementation
 *
 */
public class RIND implements MutationOperator {
	
	@Override
	public String getInfo() {
		return "Replace";
	}
	
	@Override
	public void run(Document document, File originalFile) {
		Document originalDocument = DocumentClone.clone(document);
		
		final int size = originalDocument.getDocumentElement().getElementsByTagName("cpp:ifdef").getLength();
		for (int i = 0; i < size; ++i) {
			NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:ifdef");
			Node node = nList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				document.renameNode(ele, ele.getNamespaceURI(), "cpp:ifndef");
				NodeList nlDirectives = ele.getElementsByTagName("cpp:directive");
				if (nlDirectives.getLength() == 1) {
					Node nDirective = nlDirectives.item(0);
					nDirective.setTextContent("ifndef");
				}
				Mutation mutation = new Mutation(document, originalDocument, this, i + 1);
				mutation.writeToFile(originalFile);
				System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
				document = DocumentClone.clone(originalDocument);
			}
		}	
	}

	@Override
	public String getName() {
		return "RIND";
	}
	
}
