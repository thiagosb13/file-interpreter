package org.fileinterpreter.positionalprotocol.positionallinewithrtlfields;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class PositionalLineWithRTLFieldsTest {
    @Test
    public void ifAFieldIsConfiguredToRTLShouldWriteItRightAligned() throws MisconfiguredDocumentException {
        PositionalLineRTLSample sample = new PositionalLineRTLSample();
        sample.name = "JOHN DOE";
        sample.userID = "1-00";
        
        PositionalLineParser parser = new PositionalLineParser();
        assertThat(parser.toContent(sample), is("1-00                                      JOHN DOE"));
    }
}
