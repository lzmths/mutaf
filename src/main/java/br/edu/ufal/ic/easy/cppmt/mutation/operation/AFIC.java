package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.feature.FeatureParser;
import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 * 
 * @author Luiz Carvalho
 *
 *  AFIC - Adding Feature to ifdef Condition
 *
 */
public class AFIC implements MutationOperator {

	@Override
	public String getInfo() {
		return "Add";
	}
	
	/**
	 * Get child with name from node
	 * @param name name
	 * @param fromNode node to get child
	 * @return the child. Return null if there is not child with this name.
	 */
	private Node getChildWithName(String name, Node fromNode) {
		NodeList childNodes = fromNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); ++i) {
			Node child = childNodes.item(i);
			if (child.getNodeName().equals(name)) {
				return child;
			}
 		}
		return null;
	}
	
	@Override
	public void run(Document document, File originalFile) {
		//List<Mutation> result = new ArrayList<Mutation>();
		List<String> features = new FeatureParser().parser(document);
		Document originalDocument = DocumentClone.clone(document);
		NodeList nList = document.getDocumentElement().getElementsByTagName("cpp:if");
		long id = 1;
		for (int i = 0; i < nList.getLength(); ++i) {
			for (int j = 0; j < features.size(); ++j) {
				Node currentNode = nList.item(i);
				Node exprNode = this.getChildWithName("expr", currentNode);
				exprNode.appendChild(createNode("operator", " && ", document));
				exprNode.appendChild(createCallNode(document, features.get(j)));
				Mutation mutation = new Mutation(document, originalDocument, this, id++);
				mutation.writeToFile(originalFile);
				System.out.println("mutation: " + mutation.getMutationFile().getAbsolutePath());
				document = DocumentClone.clone(originalDocument);
				nList = document.getDocumentElement().getElementsByTagName("cpp:if");
			}
		}
		//return result;
	}

	/**
	 * Create call Node with feature provided
	 * @param document document
	 * @param feature
	 * @return Node
	 */
	private Node createCallNode(Document document, String feature) {
		Node callNode = createNode("call", "", document);
		callNode.appendChild(createNode("name", "defined", document));
		Node argumentListNode = createNode("argument_list", "", document);
		Node argument = createNode("argument", "(", document);
		argumentListNode.appendChild(argument);
		Node expr = createNode("expr", "", document);
		argument.appendChild(expr);
		Node name = createNode("name", feature + ")", document);
		expr.appendChild(name);
		callNode.appendChild(argumentListNode);
		return callNode;
	}

	/**
	 * Create a name
	 * @param name
	 * @param textContent
	 * @param doc document
	 * @return node
	 */
	private Node createNode(String name, String textContent, Document doc) {
		Element element = doc.createElement(name);
		Node node = (Node) element;
		node.setTextContent(textContent);
		return node;
	}

	@Override
	public String getName() {
		return "AFIC";
	}
	
}
