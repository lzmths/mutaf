package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Luiz Carvalho
 * 
 * RIDC mutation operator test
 *
 */
public class RIDCTest {
	
	UsefulForTesting util;
	
	@Before
	public void init() {
		this.util = new UsefulForTesting();
	}

	
	@Test
	public void theSimplestPossibleTest() {
		File fileToTest = new File("inputTests/RIDC/theSimplestPossibleTest.c");
		File outputToTest = new File("outputTests/RIDC/theSimplestPossibleTest");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new RIDC());
			List<String> expectedResult = util.getContentFromFolder(outputToTest);
			if (mutants.isEmpty()) {
				fail("There is not mutants");
			} else if (expectedResult.isEmpty()) {
				fail("Tehre is not expected results");
			}
			int i = 0;
			for (String mutantResult : mutants) {
				assertEquals(expectedResult.get(i++), mutantResult);			
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			fail("Get exception");
		}
	}
	
	@Test
	public void aLotOfIfdefAndIfndefNested() {
		File fileToTest = new File("inputTests/RIDC/aLotOfIfdefAndIfndefNested.c");
		File outputToTest = new File("outputTests/RIDC/aLotOfIfdefAndIfndefNested");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new RIDC());
			List<String> expectedResult = util.getContentFromFolder(outputToTest);
			if (mutants.isEmpty()) {
				fail("There is not mutants");
			} else if (expectedResult.isEmpty()) {
				fail("Tehre is not expected results");
			}
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
