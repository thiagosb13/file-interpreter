package org.fileinterpreter.positionalprotocol.positionaldocument;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class WritingPositionalLineTest {
	@Test
	public void shouldBuildTheObjectBasedOnFieldsDefinition() throws MisconfiguredDocumentException {
		PositionalLineSample sample = new PositionalLineSample();
		sample.name = "JOHN DOE";
		sample.userID = "1-00";
		
		PositionalLineParser parser = new PositionalLineParser();
        assertThat(parser.toContent(sample), is("1-00                JOHN DOE                      "));
	}

    @Test
    public void shouldTruncFieldsToSizeDefinition() throws MisconfiguredDocumentException {
        PositionalLineSample sample = new PositionalLineSample();
        sample.name = "JOHN DOE 999999999999999999999999999999";
        sample.userID = "1-00 99999999999999999999999999999999";

        PositionalLineParser parser = new PositionalLineParser();
        assertThat(parser.toContent(sample), is("1-00 999999999999999JOHN DOE 999999999999999999999"));
    }
}
