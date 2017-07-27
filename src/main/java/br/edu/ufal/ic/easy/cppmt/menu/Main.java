package br.edu.ufal.ic.easy.cppmt.menu;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.edu.ufal.ic.easy.cppmt.io.xml.IOXML;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperationException;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperationList;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperationRunner;
import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) {
		Menu menu = new Menu(args);
		IOXML io = new IOXML();
		List<String> mutationsOperationsNames = menu.getMutationOperationsNames();
		String fileToPath = menu.getFilePath();
		Document document;
		try {
			document = io.read(new File(fileToPath));
			MutationOperationList mOpList = new MutationOperationList();
			mOpList.setMutationsOperationsNames(mutationsOperationsNames);
			MutationOperationRunner mutationOperationRunner = new MutationOperationRunner(mOpList);
			try {
				List<Document> mutations = mutationOperationRunner.runTo(document);
				for (Document m : mutations) {
					io.write(new File(fileToPath), m);
				}
			} catch (MutationOperationException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			System.err.println("We can not read the C file or generated xml using srcML");
		}
	}
	
}
