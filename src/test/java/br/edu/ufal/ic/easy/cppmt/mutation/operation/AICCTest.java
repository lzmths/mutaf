package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Luiz Carvalho
 *
 * AICC mutation operator test
 * 
 */
public class AICCTest {

	UsefulForTesting util;
	
	@Before
	public void init() {
		this.util = new UsefulForTesting();
	}
	
	@Test
	public void theSimplestPossibleTest() {
		File fileToTest = new File("inputTests/AICC/theSimplestPossibleTest.c");
		File outputToTest = new File("outputTests/AICC/");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new AICC());
			List<String> expectedResult = util.getContentFromFolder(outputToTest);
			int i = 0;
			for (String mutantResult : mutants) {
				assertEquals(expectedResult.get(i++), mutantResult);			
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			fail("Get exception");
		}
	}
	
}
