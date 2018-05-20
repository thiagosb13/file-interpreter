package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

public class PositionalDocumentWithLineDelimiterTest {
    @Test
    public void shouldFillAllOfPropertiesOfTheObject() throws MisconfiguredDocumentException {
        DocumentParser<PositionalDocumentWithLineDelimiterSample> parser = new DocumentParser<>(PositionalDocumentWithLineDelimiterSample.class);
        PositionalDocumentWithLineDelimiterSample document = parser.parse("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
        
        assertThat(document.line1.userID.trim(), is("1-00"));
        assertThat(document.line1.name.trim(), is("JOHN DOE"));
        
        assertThat(document.line2.userID.trim(), is("2-00"));
        assertThat(document.line2.name.trim(), is("JOE BLACK"));
    }

    @Test
    public void shouldFillOnlyTheFirstLineWithTheText() throws MisconfiguredDocumentException {
        DocumentParser<PositionalDocumentWithLineDelimiterSample> parser = new DocumentParser<>(PositionalDocumentWithLineDelimiterSample.class);
        PositionalDocumentWithLineDelimiterSample document = parser.parse("1-00                JOHN DOE                      ");
        
        assertThat(document.line1.userID.trim(), is("1-00"));
        assertThat(document.line1.name.trim(), is("JOHN DOE"));
        
        assertThat(document.line2.userID, isEmptyString());
        assertThat(document.line2.name, is(equalTo("NC")));
    }
}
