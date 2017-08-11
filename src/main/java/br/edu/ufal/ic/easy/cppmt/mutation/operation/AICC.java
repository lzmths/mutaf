package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.edu.ufal.ic.easy.cppmt.feature.FeatureParser;
import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;
import br.edu.ufal.ic.easy.cppmt.mutation.operation.util.AddCppAroundCode;
import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 * @author Luiz Carvalho
 * 
 * AICC - Adding ifdef Condition around Code
 *
 */
public class AICC implements MutationOperator {
	
	@Override
	public String getInfo() {
		return "Add";
	}
	
	@Override
	public List<Mutation> run(Document document) {
		Document originalDocument = DocumentClone.clone(document);
		FeatureParser fParser = new FeatureParser();
		List<String> features = fParser.parser(document);
		List<Mutation> lDocument = new ArrayList<Mutation>();
		final int size = document.getDocumentElement().getElementsByTagName("function").getLength();
		AddCppAroundCode add = new AddCppAroundCode();
		final int cppSize = 2;
		final int ifdef = 0;
		long id = 1;
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < features.size(); ++j) {
				for (int k = 0; k < cppSize; ++k) {
					String featureStr = features.get(j);
					NodeList nList = document.getDocumentElement().getElementsByTagName("function");
					Node currentNode = nList.item(i);
					Node sibling = currentNode.getNextSibling();
					if (sibling == null) continue;
					if (k == ifdef) {
						add.createIfdef(featureStr, currentNode, document);
					} else {
						add.createIfndef(featureStr, currentNode, document);
					}
					add.createEndif(nList, sibling, document);
					lDocument.add(new Mutation(document, originalDocument, this, id++));
					document = DocumentClone.clone(originalDocument);
				}
			}
		}
		return lDocument;
	}

	@Override
	public String getName() {
		return "AICC";
	}

}
