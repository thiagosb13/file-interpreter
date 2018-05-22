package org.fileinterpreter.positionalprotocol.positionaldocumentwithspacefilling;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class PositionalDocumentWithSpaceFillingTest {
	@Test
	public void shouldCompleteFieldValueWithDefinedSpaceFilling() throws MisconfiguredDocumentException {
		PositionalLineWithDefaultFillingSample sample = new PositionalLineWithDefaultFillingSample();
		sample.name = "JOHN DOE";
		sample.userID = "1-00";

		PositionalLineParser parser = new PositionalLineParser();
		assertThat(parser.toContent(sample), is("1-00################JOHN DOE**********************"));
	}

}
