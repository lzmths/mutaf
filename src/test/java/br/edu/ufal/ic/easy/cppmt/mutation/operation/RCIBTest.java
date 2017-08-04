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
 * Test for RCIB
 *
 */
public class RCIBTest {

	UsefulForTesting util;
	
	@Before
	public void init() {
		this.util = new UsefulForTesting();
	}
	
	@Test
	public void theSimplestTest() {
		File fileToTest = new File("inputTests/RCIB/theSimplestTest.c");
		File outputToTest = new File("outputTests/RCIB/theSimplestTest/");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new RCIB());
			List<String> expectedResult = util.getContentFromFolder(outputToTest);
			if (mutants.isEmpty()) fail("There is not mutants");
			if (expectedResult.isEmpty()) fail("There is not results");
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
	public void ifdefNested() {
		File fileToTest = new File("inputTests/RCIB/ifdefNested.c");
		File outputToTest = new File("outputTests/RCIB/ifdefNested/");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new RCIB());
			List<String> expectedResult = util.getContentFromFolder(outputToTest);
			if (mutants.isEmpty()) fail("There is not mutants");
			if (expectedResult.isEmpty()) fail("There is not results");
			int i = 0;
			for (String mutantResult : mutants) {
				assertEquals(expectedResult.get(i++), mutantResult);			
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			fail("Get exception");
		}
	}
	
	/**
	 * Undisciplined case
	 */
	@Test
	public void undisciplined() {
		File fileToTest = new File("inputTests/RCIB/undisciplined.c");
		File outputToTest = new File("outputTests/RCIB/undisciplined/");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new RCIB());
			List<String> expectedResult = util.getContentFromFolder(outputToTest);
			if (mutants.isEmpty()) fail("There is not mutants");
			if (expectedResult.isEmpty()) fail("There is not results");
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
