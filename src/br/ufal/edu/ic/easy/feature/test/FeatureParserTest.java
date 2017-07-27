package br.ufal.edu.ic.easy.feature.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import br.ufal.edu.ic.easy.feature.FeatureParser;
import br.ufal.edu.ic.easy.io.xml.IOXML;

public class FeatureParserTest {

	@Test
	public void getFeatureListFromDefine() {
		File fileToTest = new File("inputTests/inputFeatureTest.c");
		IOXML io = new IOXML();
		FeatureParser fParser = new FeatureParser();
		try {
			Document doc = io.read(fileToTest);
			List<String> features = fParser.parser(doc);
			if (features.isEmpty()) fail("Empty feature list");
			assertTrue(features.contains("A"));
			assertTrue(features.contains("B"));
		} catch (IOException e) {
			fail("Fail becausa get a Exception");
		}
	}

}
