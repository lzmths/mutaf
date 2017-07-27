package br.ufal.edu.ic.easy.mutation;

import java.util.Iterator;
import java.util.List;

import br.ufal.edu.ic.easy.mutation.operation.ACFD;
import br.ufal.edu.ic.easy.mutation.operation.RCFD;
import br.ufal.edu.ic.easy.mutation.operation.RIND;
import br.ufal.edu.ic.easy.mutation.operation.RNID;

/**
 * 
 * @author Luiz Carvalho
 * 
 * It is a mutation operation list.
 * Iterator return a Mutation Operator
 *
 */
public class MutationOperationList implements Iterator<MutationOperator> {
	
	MutationOperator[] lMutationsOperators;
	private int index;
	
	/**
	 * Receive the names of mutations operations
	 * @param lMutationOp names of mutations operations
	 */
	public void setMutationsOperationsNames(List<String> lMutationOp) {
		this.lMutationsOperators = new MutationOperator[lMutationOp.size()];
		int index = 0;
		for (String mutationOp : lMutationOp) {
			if (mutationOp.equals("RIND")) {
				lMutationsOperators[index++] = new RIND();
			} else if (mutationOp.equals("RNID")) {
				lMutationsOperators[index++] = new RNID();
			} else if (mutationOp.equals("RCFD")) {
				lMutationsOperators[index++] = new RCFD();
			} else if (mutationOp.equals("ACFD")) {
				lMutationsOperators[index++] = new ACFD();
			}
		}
	}
	
	/**
	 * Receive the mutations operations
	 * @param lMutationsOperators mutations operations
	 */
	public void setMutationOperator(MutationOperator[] lMutationsOperators) {
		this.lMutationsOperators = lMutationsOperators;
	}

	/**
	 * Is there next?
	 * if there is next mutation operation return true. Otherwise return false.
	 */
	@Override
	public boolean hasNext() {
		return (index < lMutationsOperators.length);
	}

	/**
	 * Return the next mutation operator
	 */
	@Override
	public MutationOperator next() {
		if (this.hasNext()) {
			return lMutationsOperators[index++]; 
		}
		return null;
	}
	
}
