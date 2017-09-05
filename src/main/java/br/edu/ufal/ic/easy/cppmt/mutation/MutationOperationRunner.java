package br.edu.ufal.ic.easy.cppmt.mutation;

import java.io.File;

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
	public void runTo(Document document, File originalFile) throws MutationOperationException {
		while (this.mOperationSelector.hasNext()) {
			MutationOperator mutationOperator = this.mOperationSelector.next();
			System.out.println("Running: " + mutationOperator.getName());
			mutationOperator.run(DocumentClone.clone(document), originalFile);
			System.out.println("Runned: " + mutationOperator.getName());
		}
	}
	
}
