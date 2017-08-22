package br.edu.ufal.ic.easy.cppmt.impact.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Impact Analysis from Larrisa Braz et al.
 *
 */
public class ImpactAnalysisFromBraz implements ImpactAnalysisAlgorithm {
	
	private File fileToImpact = new File("braz/impact.py");
	
	@Override
	public List<String> run(File file1, File file2, String info) {
		info = info.toLowerCase();
		if (info.equals("remove")) {
			File aux = file1;
			file1 = file2;
			file2 = aux;
		}
		String pathResult = file1.getAbsolutePath();
		pathResult = pathResult.replace(".c", "_resultFromImpactAnalyses.info");
		List<String> result = new ArrayList<String>();
		String command = "python3" + " " + this.fileToImpact.getAbsolutePath() + " " + file1.getAbsolutePath() + " " + 
						file2.getAbsolutePath();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
	        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			p.waitFor(); 
		} catch (IOException | InterruptedException e) {
			System.err.println("We can not run python3 or impact.py");
		}
		return result;
	}

}
