package br.edu.ufal.ic.easy.cppmt.impact.analysis;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Luiz Carvalho
 * 
 *  Impact Analysis Algorithm.
 *
 */
public interface ImpactAnalysisAlgorithm {

	/**
	 * Run the algorithm and return the features impacted.
	 * @param file1 file
	 * @param file2 fil2
	 * @param info What is the call order of the files depending on the characteristics of the mutantation.
	 * Like add, replace, remove.
	 * @return features impacted.
	 */
	public List<String> run(File original, File mutation, String info);
	
}
