package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class PositionalLineWithRTLFieldsTest {
    @Test
    public void ifAFieldIsConfiguredToRTLShouldWriteItRightAligned() throws InstantiationException, IllegalAccessException, MisconfiguredDocumentException {
        PositionalLineRTLSample sample = new PositionalLineRTLSample();
        sample.name = "JOHN DOE";
        sample.userID = "1-00";
        
        PositionalLineParser parser = new PositionalLineParser();
        assertThat(parser.toContent(sample), is("1-00                                      JOHN DOE"));
    }
    
	public class PositionalLineRTLSample {
	    @PositionalField(name = "User ID", startIndex = 1, size = 20)
	    public String userID;
	    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", rtl = true)
	    public String name;
	}
}
