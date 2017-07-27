package br.ufal.edu.ic.easy.mutation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import br.ufal.edu.ic.easy.util.xml.DocumentClone;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Runner mutations operations;
 *
 */
public class MutationOperationRunner {
	
	private MutationOperationList mOperationSelector;

	public MutationOperationRunner(MutationOperationList mOperatorSelector) {
		this.mOperationSelector = mOperatorSelector;
	}
	
	/**
	 * Set mutation operation list
	 * @param mOperationSelector Mutation Operation List
	 */
	public void setMutationOperationSelector(MutationOperationList mOperationSelector) {
		this.mOperationSelector = mOperationSelector;
	}
	
	/**
	 * 
	 * @param element will be mutated by mutation by operation list 
	 * @return mutations
	 * @throws MutationOperationException
	 */
	public List<Document> runTo(Document document) throws MutationOperationException {
		List<Document> mutationsAll = new ArrayList<Document>();
		while (this.mOperationSelector.hasNext()) {
			MutationOperator mutationOperator = this.mOperationSelector.next();
			List<Document> mutations = mutationOperator.run(DocumentClone.clone(document));
			mutationsAll.addAll(mutations);
			System.out.println("Runned: " + mutationOperator.getName());
		}
		return mutationsAll;
	}
	
}
