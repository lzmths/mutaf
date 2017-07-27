package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.feature.FeatureParser;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 *
 * @author Luiz Carvalho
 * ACFD - Add Condition to Feature Definition
 *
 */
public class ACFD implements MutationOperator {

	private void createIfdef(String featureStr, NodeList nList, Node currentNode, Document document) {
		Element newElementIfdef = document.createElement("cpp:ifdef");
		newElementIfdef.setTextContent("#");
		Element newDirective = document.createElement("cpp:directive");
		newDirective.setTextContent("ifdef ");
		newElementIfdef.appendChild(newDirective);
		Element newName = document.createElement("name");
		newName.setTextContent(featureStr + " ");
		newElementIfdef.appendChild(newName);
		currentNode.getParentNode().insertBefore(newElementIfdef, currentNode);
	}
	
	public void createEndif(NodeList nList, Node node, Document document) {
		Element newElementIfndef = document.createElement("cpp:endif");
		newElementIfndef.setTextContent(" #");
		Element newDirective = document.createElement("cpp:directive");
		newDirective.setTextContent("endif");
		newElementIfndef.appendChild(newDirective);
		node.getParentNode().insertBefore(newElementIfndef, node);
	}
	
	@Override
	public List<Document> run(Document document) {
		Document originalDocument = DocumentClone.clone(document);
		FeatureParser fParser = new FeatureParser();
		List<String> features = fParser.parser(document);
		List<Document> lDocument = new ArrayList<Document>();
		final int size = document.getDocumentElement().getElementsByTagName("cpp:define").getLength();
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < features.size(); ++j) {
				String featureStr = features.get(j);
				NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:define");
				Node currentNode = nList.item(i);
				Node sibling = currentNode.getNextSibling();
				if (sibling == null) continue;
				createIfdef(featureStr, nList, currentNode, document);
				createEndif(nList, sibling, document);
				lDocument.add(document);
				document = DocumentClone.clone(originalDocument);
			}
		}
		return lDocument;
	}

	@Override
	public String getName() {
		return "ACFD";
	}

}
