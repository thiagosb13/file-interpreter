package org.fileinterpreter.positionalprotocol.positionaldocument;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadingPositionalLineTest {
    
    @Test
    public void shouldSplitFieldsFillInValuesBasedOnConfiguration() throws MisconfiguredDocumentException {
        PositionalLineSample sample = new PositionalLineSample();
        PositionalLineParser parser = new PositionalLineParser();
        parser.parse("1-00                JOHN DOE                      ", sample);
        
        assertThat(sample.userID, is("1-00                "));
        assertThat(sample.name, is("JOHN DOE                      "));
    }

    @Test
    public void whenTextLineSizeIsLessThanPositionalLineSizeObjectShouldFillInFieldsWithEmptyValue() throws MisconfiguredDocumentException {
        PositionalLineSample sample = new PositionalLineSample();
        PositionalLineParser parser = new PositionalLineParser();
        parser.parse("1-00                JOHN DOE      ", sample);
        
        assertThat(sample.userID.trim(), is("1-00"));
        assertThat(sample.name.trim(), is(""));
    }
}
