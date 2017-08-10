package br.edu.ufal.ic.easy.cppmt.mutation;

import java.util.List;

import org.w3c.dom.Document;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Represent a Mutation Operator
 *
 */
public interface MutationOperator {

	/**
	 * Execute the mutation Operator
	 * @param document
	 * @return mutants
	 */
	public List<Mutation> run(Document document);
	
	/**
	 * Return mutation operator name
	 * @return name
	 */
	public String getName();
}
