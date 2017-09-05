package br.edu.ufal.ic.easy.cppmt.mutation;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;

import br.edu.ufal.ic.easy.cppmt.impact.analysis.ImpactAnalysisAlgorithm;
import br.edu.ufal.ic.easy.cppmt.io.xml.IOXML;
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
	private File originalFile;
	private MutationOperator mutationOperation;
	private boolean runnedImpactAnalyzer;
	private long id;
	private ImpactAnalysisAlgorithm impactAnalysisAlgorithm;
	
	/**
	 * Creates a mutation.
	 * @param mutationDoc mutation
	 * @param originalDoc original (before mutation operation has been runned)
	 * @param mutationOperation mutation operation
	 * @param id Identifier (Be careful, must be unique)
	 */
	public Mutation(Document mutationDoc, Document originalDoc, MutationOperator mutationOperation, long id) {
		this.mutationDoc = DocumentClone.clone(mutationDoc);
		this.originalDoc = DocumentClone.clone(originalDoc);
		this.mutationOperation = mutationOperation;
		this.id = id;
	}
	
	/**
	 * Get original document
	 * @return original document (before mutation operation has been runned)
	 */
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
	 * Set original file
	 * @param file original file
	 */
	public void setOriginalFile(File file) {
		this.originalFile = file;
	}
	
	/**
	 * Get original file
	 * @param file
	 * @return original file
	 */
	public File getOriginalFile() {
		return this.originalFile;
	}
	
	/**
	 * Set mutation file
	 * @param file
	 */
	public void setMutationFile(File file) {
		this.mutationFile = file;
	}
	
	/**
	 * Get mutation file
	 * @return file
	 */
	public File getMutationFile() {
		return this.mutationFile;
	}
	
	/**
	 * Get features impacted by mutation using impact analyzer.
	 * @param impactAnalysisAlgorithm Impact Analysis Algorithm
	 * @return features impacted
	 */
	public List<String> getFeaturesImpactedByMutation(ImpactAnalysisAlgorithm impactAnalysisAlgorithm) {
		runImpactAnalysisAlgorithmByMutation(impactAnalysisAlgorithm);
		return this.featuresImpacted;
	}
	
	/**
	 * Get features impacted by mutation using impact analyzer.
	 * @param impactAnalysisAlgorithm Impact Analysis Algorithm
	 * @return features impacted
	 */
	public List<String> getFeaturesImpactedByMutation() {
		return this.featuresImpacted;
	}
	
	/**
	 * Run impact analysis algorithm by mutation
	 * @param impactAnalysisAlgorithm Impact Analysis Algorithm
	 */
	public void runImpactAnalysisAlgorithmByMutation(ImpactAnalysisAlgorithm impactAnalysisAlgorithm) {
		if (this.impactAnalysisAlgorithm == null) {
			this.impactAnalysisAlgorithm = impactAnalysisAlgorithm;
		}
		if (this.mutationFile == null || this.originalFile == null) {
			System.err.println("We need mutation file and original file to call Impact Analysis Algorithm");
		} else if (!runnedImpactAnalyzer || !this.impactAnalysisAlgorithm.equals(impactAnalysisAlgorithm)) {
			this.impactAnalysisAlgorithm = impactAnalysisAlgorithm;
			this.featuresImpacted = this.impactAnalysisAlgorithm.run(getOriginalFile(), getMutationFile(), mutationOperation.getInfo());
			this.runnedImpactAnalyzer = true;
		}
	}
	
	public void writeToFile() {
		IOXML io = new IOXML();
		if (this.originalFile == null) System.err.println("Please. Set original file\n");
		File mutationFile = io.write(this.originalFile, this);
		this.setMutationFile(mutationFile);
	}
	
	public void writeToFile(File originalFile) {
		this.setOriginalFile(originalFile);
		writeToFile();
	}
	
}
