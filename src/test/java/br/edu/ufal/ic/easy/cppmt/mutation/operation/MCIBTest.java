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
 * MCIB mutation operation test
 *
 */
public class MCIBTest {

	UsefulForTesting util;
	
	@Before
	public void init() {
		this.util = new UsefulForTesting();
	}
	
	@Test
	public void theSimplestPossibleTest() {
		File fileToTest = new File("inputTests/MCIB/testWithIfdefIfndefandIfDefined.c");
		File outputToTest = new File("outputTests/MCIB/testWithIfdefIfndefandIfDefined");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new MCIB());
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
