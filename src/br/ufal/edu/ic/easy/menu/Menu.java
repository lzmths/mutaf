package br.ufal.edu.ic.easy.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luiz Carvalho
 * 
 * Parser to Menu arguments
 * 
 */
public class Menu {

	private List<String> mutationsOperationsNames = new ArrayList<String>();
	private String filePath;
	
	public Menu(String[] args) {
		parserToArguments(args);
	}
	
	/**
	 * @return Mutation Operations Names
	 */
	public List<String> getMutationOperationsNames() {
		return this.mutationsOperationsNames;
	}
	
	/**
	 * @return path to file
	 */
	public String getFilePath() {
		return this.filePath;
	}
	
	/**
	 * Parser to arguments
	 */
	public void parserToArguments(String[] args) {
		for (int i = 0; i < args.length; ++i) {
			if (args[i].equals("-mutations")) {
				parserMutations(args, i + 1);
			} else if (args[i].equals("-file")) {
				parserFile(args, i + 1);
			}
		}
	}
	
	/**
	 * Check if there is not -
	 * @param arg argument
	 */
	private boolean hasSanity(String arg) { 
		return !arg.contains("-"); 
	}
	
	/**
	 * Parser to mutations in arguments
	 */
	private void parserMutations(String[] args, int index) {
		this.mutationsOperationsNames = new ArrayList<String>();
		for (int i = index; i < args.length; ++i) {
			if (hasSanity(args[i])) {
				mutationsOperationsNames.add(args[i]);
			} else {
				return;
			}
		}
	}
	
	/**
	 * Parser to file in arguments
	 */
	private void parserFile(String[] args, int index) {
		for (int i = index; i < args.length; ) {
			if (hasSanity(args[i])) {
				this.filePath = args[i];
				return;
			} else {
				return;
			}
		}
	}
	
}
