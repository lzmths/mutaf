package br.edu.ufal.ic.easy.cppmt.mutation;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;

import br.edu.ufal.ic.easy.cppmt.util.xml.DocumentClone;

/**
 * @author Luiz Carvalho
 *
 * Represents a mutation
 *
 */
public class Mutation {
	
	private Document originalDoc;
	private Document mutationDoc;
	private List<String> featuresImpacted;
	private File mutationFile;
	private MutationOperator mutationOperation;
	private boolean runnedImpactAnalyzer;
	private long id;
	
	public Mutation(Document mutationDoc, Document originalDoc, MutationOperator mutationOperation, long id) {
		this.mutationDoc = DocumentClone.clone(mutationDoc);
		this.originalDoc = DocumentClone.clone(originalDoc);
		this.mutationOperation = mutationOperation;
		this.id = id;
	}
	
	/**
	 * Run impact analyzer.
	 * @return features impacted by mutation
	 */
	private List<String> runImpactAnalyzer() {
		//TODO
		return null;
	}
	
	public Document getOriginalDocument() {
		return this.originalDoc;
	}
	
	/**
	 * Get ID
	 * @return id
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Get mutation operation name
	 * @return mutation operation name
	 */
	public String getMutationOperationName() {
		return this.mutationOperation.getName();
	}
	
	/**
	 * Get Document
	 * @return Document
	 */
	public Document getDocument() {
		return this.mutationDoc;
	}
	
	/**
	 * Set file
	 * @param file
	 */
	public void setFile(File file) {
		this.mutationFile = file;
	}
	
	/**
	 * Get file
	 * @return file
	 */
	public File getFile() {
		return this.mutationFile;
	}
	
	/**
	 * Get features impacted by mutation using impact analyzer.
	 * @return features impacted by mutation
	 */
	public List<String> getFeaturesImpactedByMutation() {
		if (!this.runnedImpactAnalyzer) {
			this.featuresImpacted = runImpactAnalyzer();
			this.runnedImpactAnalyzer = true;
		}
		return this.featuresImpacted;
	}
	
}
