package br.edu.ufal.ic.easy.cppmt.mutation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

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
	 * Run mutations
	 * @param document element will be mutated by mutation by operation list 
	 * @return mutations
	 * @throws MutationOperationException
	 */
	public List<Mutation> runTo(Document document) throws MutationOperationException {
		List<Mutation> mutationsAll = new ArrayList<Mutation>();
		while (this.mOperationSelector.hasNext()) {
			MutationOperator mutationOperator = this.mOperationSelector.next();
			List<Mutation> mutations = mutationOperator.run(DocumentClone.clone(document));
			mutationsAll.addAll(mutations);
			System.out.println("Runned: " + mutationOperator.getName());
		}
		return mutationsAll;
	}
	
}
