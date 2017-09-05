package br.edu.ufal.ic.easy.cppmt.mutation;

import java.io.File;

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
	 * Get info like Mutation operation add, replace or remove.
	 */
	public String getInfo();
	
	/**
	 * Execute the mutation Operator
	 * @param document
	 * @return mutants
	 */
	public void run(Document document, File originalFile);
	
	/**
	 * Return mutation operator name
	 * @return name
	 */
	public String getName();
}
