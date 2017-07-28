package br.edu.ufal.ic.easy.cppmt.mutation.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import br.edu.ufal.ic.easy.cppmt.io.xml.IOXML;

/**
 * 
 * @author Luiz Carvalho
 *
 */
public class AICCTest {

	@Test
	public void theSimplestPossibleTest() {
		File fileToTest = new File("inputTests/AICC/theSimplestPossibleTest.c");
		IOXML io = new IOXML();
		AICC aicc = new AICC();
		try {
			List<Document> mts = aicc.run(io.read(fileToTest));
			int i = 0;
			for (Document mutation : mts) {
				File expectedFile = new File("outputTests/AICC/theSimplestPossibleTest-" + i + ".c");
				File mtResultFile = io.write(fileToTest, mutation);
				try {
					BufferedReader brMt = new BufferedReader(new FileReader(mtResultFile));
					BufferedReader brExpected = new BufferedReader(new FileReader(expectedFile));
					String line, mtStr, expectedStr;
					mtStr = "";
					while ((line = brMt.readLine()) != null) {
						mtStr = mtStr + line;
					}
					expectedStr = "";
					while ((line = brExpected.readLine()) != null) {
						expectedStr = expectedStr + line;
					}
					assertEquals(expectedStr, mtStr);
					brMt.close();
					brExpected.close();
				} catch (IOException e) {
					fail("IOException");
				}
				mtResultFile.deleteOnExit();
				++i;
			}
		} catch (IOException e) {
			fail("Get Exception");
		}
	}

}
