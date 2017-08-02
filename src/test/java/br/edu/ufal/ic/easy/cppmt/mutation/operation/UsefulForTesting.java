package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;

import br.edu.ufal.ic.easy.cppmt.io.xml.IOXML;
import br.edu.ufal.ic.easy.cppmt.mutation.MutationOperator;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Useful for testing
 *
 */
public class UsefulForTesting {

	/**
	 * Run mutation operator
	 * @param file input case
	 * @param mOperator mutation operator
	 * @return all the codes in C
	 * @throws IOException
	 */
	public List<String> runMutationOperator(File file, MutationOperator mOperator) throws IOException {
		IOXML io = new IOXML();
		List<String> result = new ArrayList<String>();
		Document doc = io.read(file);
		List<Document> mts = mOperator.run(io.read(file));
		for (Document mutation : mts) {
			File mtResultFile = io.write(file, mutation);
			BufferedReader brMt = new BufferedReader(new FileReader(mtResultFile));
			String line, mtStr;
			mtStr = "";
			while ((line = brMt.readLine()) != null) {
				mtStr = mtStr + line;
			}
			result.add(mtStr);
			brMt.close();
			mtResultFile.deleteOnExit();
		} 
		return result;
	}
	
	/**
	 * Get content from folder
	 * @param folder folder with content
	 * @return all content from folder
	 * @throws IOException
	 */
	public List<String> getContentFromFolder(File folder) throws IOException {
		List<String> result = new ArrayList<String>();
		if (folder.isDirectory()) {
			String[] files = folder.list();
			Arrays.sort(files);
			for (int i = 0; i < files.length; ++i) {
				File file = new File(folder.getAbsolutePath() + File.separator + files[i]);
				BufferedReader brMt = new BufferedReader(new FileReader(file));
				String line, mtStr;
				mtStr = "";
				while ((line = brMt.readLine()) != null) {
					mtStr = mtStr + line;
				}
				result.add(mtStr);
				brMt.close();
			}
		}
		return result;
	}
	
}
