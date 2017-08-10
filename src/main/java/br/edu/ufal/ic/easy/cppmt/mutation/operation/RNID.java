package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.util.ArrayList;
import java.util.List;

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
 * RNID - Replacing ifndef Directive with ifdef Directive from the paper Mutation Operators for Preprocessor-Based Variability
 *  It is the implementation
 *
 */
public class RNID implements MutationOperator {
	
	@Override
	public List<Mutation> run(Document document) {
		List<Mutation> lDocument = new ArrayList<Mutation>();
		Document originalDocument = DocumentClone.clone(document);
		
		final int size = originalDocument.getDocumentElement().getElementsByTagName("cpp:ifndef").getLength();
		for (int i = 0; i < size; ++i) {
			NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:ifndef");
			Node node = nList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				document.renameNode(ele, ele.getNamespaceURI(), "cpp:ifdef");
				NodeList nlDirectives = ele.getElementsByTagName("cpp:directive");
				if (nlDirectives.getLength() == 1) {
					Node nDirective = nlDirectives.item(0);
					nDirective.setTextContent("ifdef");
				}
				lDocument.add(new Mutation(document, originalDocument, this, i));
				document = DocumentClone.clone(originalDocument);
			}
		}	
		return lDocument;
	}

	@Override
	public String getName() {
		return "RNID";
	}

}
