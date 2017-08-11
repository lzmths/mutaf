package br.edu.ufal.ic.easy.cppmt.io.features;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Write Recommended features to Test
 *
 */
public class IORecommendedFeatures {
	
	/**
	 * Write features 
	 * @param features impacted by mutation
	 * @param mutation mutation
	 */
	public void write(Mutation mutation) {
		List<String> features = mutation.getFeaturesImpactedByMutation();
		String path = mutation.getOriginalFile().getAbsolutePath();
		path = path.replace(".c", "_" + mutation.getMutationOperationName() + "_" + mutation.getId() + ".info");
		path = path.replace(".h", "_" + mutation.getMutationOperationName() + "_" + mutation.getId() + ".info");
		try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
			br.write("CPPMT using Impact Analysis recommend the test with the following enabled features. \n");
			for (String feature : features) {
				br.write(feature + "\n");
			}
		} catch (IOException e) {
			System.err.println("We can not write in " + path + ". To write recommended features from impact analysis");
		}
	}
	
}
