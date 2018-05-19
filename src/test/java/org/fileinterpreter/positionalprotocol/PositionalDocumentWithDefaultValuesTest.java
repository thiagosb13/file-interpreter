package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class PositionalDocumentWithDefaultValuesTest {

	@Test
	public void whenValueIsNotFilledShouldUseDefaultValue() throws InstantiationException, IllegalAccessException, MisconfiguredDocumentException {
		PositionalLineSample sample = new PositionalLineSample();
		sample.userID = "1-00";

		PositionalLineParser parser = new PositionalLineParser();
		assertThat(parser.toContent(sample), is("1-00                NC                            "));
	}
}
