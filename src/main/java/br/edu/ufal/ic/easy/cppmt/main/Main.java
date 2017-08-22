package br.edu.ufal.ic.easy.cppmt.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.edu.ufal.ic.easy.cppmt.impact.analysis.ImpactAnalysisFromBraz;
import br.edu.ufal.ic.easy.cppmt.io.features.IORecommendedFeatures;
import br.edu.ufal.ic.easy.cppmt.io.xml.IOXML;
import br.edu.ufal.ic.easy.cppmt.menu.Menu;
import br.edu.ufal.ic.easy.cppmt.mutation.Mutation;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperationException;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperationList;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperationRunner;

import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) {
		Menu menu = new Menu(args);
		IOXML io = new IOXML();
		IORecommendedFeatures ioRecommendedFeatures = new IORecommendedFeatures();
		List<String> mutationsOperationsNames = menu.getMutationOperationsNames();
		String fileToPath = menu.getFilePath();
		File originalFile = new File(fileToPath);
		Document document;
		try {
			document = io.read(originalFile);
			MutationOperationList mOpList = new MutationOperationList();
			mOpList.setMutationsOperationsNames(mutationsOperationsNames);
			MutationOperationRunner mutationOperationRunner = new MutationOperationRunner(mOpList);
			try {
				List<Mutation> mutations = mutationOperationRunner.runTo(document);
				for (Mutation m : mutations) {
					File mutationFile = io.write(new File(fileToPath), m);
					m.setMutationFile(mutationFile);
					m.setOriginalFile(originalFile);
					System.out.println("mutation: " + mutationFile);
					m.runImpactAnalysisAlgorithmByMutation(new ImpactAnalysisFromBraz());
					ioRecommendedFeatures.write(m);
				}
			} catch (MutationOperationException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			System.err.println("We can not read the C file or generated xml using srcML");
		}
	}
	
}
