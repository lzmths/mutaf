package br.edu.ufal.ic.easy.cppmt.generation;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Luiz Carvalho
 * 
 * Generation C from XML using srcML(www.srcml.org)
 *
 */
public class GenerationC {
	
	public File convertsFromXML(File xmlFile) throws IOException {
		String pathXML = xmlFile.getAbsolutePath();
		String pathC = pathXML.replace(".xml", ".c");
		try {
			String command = "srcml " + "-l " + "C " + pathXML + " -o " + pathC;
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			File cFile = new File(pathC);
			return cFile;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		throw new IOException("We can not used the srmML");
	}

}
