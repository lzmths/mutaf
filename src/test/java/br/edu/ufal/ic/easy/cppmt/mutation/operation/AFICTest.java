package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for AFIC
 * @author Luiz Carvalho
 *
 */
public class AFICTest {

	UsefulForTesting util;
	
	@Before
	public void init() {
		this.util = new UsefulForTesting();
	}
	
	@Test
	public void oneDefined() {
		File fileToTest = new File("inputTests/AFIC/oneDefined.c");
		File outputToTest = new File("outputTests/AFIC/oneDefined/");
		try {
			List<String> mutants = util.runMutationOperator(fileToTest, new AFIC());
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
