package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Luiz Carvalho
 *
 * Test for RFIC
 *
 */
public class RFICTest {

UsefulForTesting util;
	
	@Before
	public void init() {
		this.util = new UsefulForTesting();
	}
	
	@Test
	public void oneAndThreeDefined() {
		File fileToTest = new File("inputTests/RFIC/oneAndThreeDefined.c");
		File outputToTest = new File("outputTests/RFIC/");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new RFIC());
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
