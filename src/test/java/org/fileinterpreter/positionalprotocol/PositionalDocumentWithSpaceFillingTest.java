package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class PositionalDocumentWithSpaceFillingTest {
	@Test
	public void shouldCompleteFieldValueWithDefinedSpaceFilling() throws InstantiationException, IllegalAccessException, MisconfiguredDocumentException {
		PositionalLineWithDefaultFillingSample sample = new PositionalLineWithDefaultFillingSample();
		sample.name = "JOHN DOE";
		sample.userID = "1-00";

		PositionalLineParser parser = new PositionalLineParser();
		assertThat(parser.toContent(sample), is("1-00################JOHN DOE**********************"));
	}
	
	public class PositionalLineWithDefaultFillingSample {
	    @PositionalField(name = "User ID", startIndex = 1, size = 20, spaceFilling = '#')
	    public String userID;
	    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", spaceFilling = '*')
	    public String name;
	}
}
