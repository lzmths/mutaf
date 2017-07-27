package br.ufal.edu.ic.easy.mutation.operation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufal.edu.ic.easy.mutation.MutationOperator;
import br.ufal.edu.ic.easy.util.xml.DocumentClone;


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
	public List<Document> run(Document document) {
		List<Document> lDocument = new ArrayList<Document>();
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
				lDocument.add(document);
				document = DocumentClone.clone(originalDocument);
			}
		}	
		return lDocument;
	}

	@Override
	public String getName() {
		return "RIND";
	}
	
}
