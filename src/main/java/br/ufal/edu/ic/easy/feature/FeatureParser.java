package br.ufal.edu.ic.easy.feature;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FeatureParser {

	List<String> lFeature = new ArrayList<String>();
	
	private void getFromDefine(Document document) {
		NodeList lNode = document.getDocumentElement().getElementsByTagName("cpp:define");
		for (int i = 0; i < lNode.getLength(); ++i) {
			Node node = lNode.item(i);
			NodeList lChildNodes = node.getChildNodes();
			for (int j = 0; j < lChildNodes.getLength(); ++j) {
				Node pMacro = lChildNodes.item(j);
				if (pMacro.getNodeName().equals("cpp:macro")) {
					final int onlyFirst = 0;
					Node nameNode = pMacro.getChildNodes().item(onlyFirst);
					if (!lFeature.contains(nameNode.getTextContent())) lFeature.add(nameNode.getTextContent());
				}
			}
		}
	}
	
	public List<String> parser(Document document) {
		this.lFeature.clear();
		getFromDefine(document);
		return lFeature;
	}
	
	
}
