package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.fileinterpreter.positionalprotocol.document.PositionalDocumentOrderedSample;
import org.fileinterpreter.positionalprotocol.document.PositionalDocumentWithLineDelimiterSample;
import org.junit.Test;

public class ReadingFromPositionalDocumentTest {

    @Test
    public void shouldFillAllOfPropertiesOfTheObject() throws MisconfiguredDocumentException {
        PositionalDocumentWithLineDelimiterSample document = new PositionalDocumentWithLineDelimiterSample();
        DocumentParser<PositionalDocumentWithLineDelimiterSample> parser = new DocumentParser<>(document);
        parser.parse("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
        
        assertThat(document.line1.userID.trim(), is("1-00"));
        assertThat(document.line1.name.trim(), is("JOHN DOE"));
        
        assertThat(document.line2.userID.trim(), is("2-00"));
        assertThat(document.line2.name.trim(), is("JOE BLACK"));
    }

    @Test
    public void shouldFillOnlyTheFirstLineWithTheText() throws MisconfiguredDocumentException {
        PositionalDocumentWithLineDelimiterSample document = new PositionalDocumentWithLineDelimiterSample();
        DocumentParser<PositionalDocumentWithLineDelimiterSample> parser = new DocumentParser<>(document);
        parser.parse("1-00                JOHN DOE                      ");
        
        assertThat(document.line1.userID.trim(), is("1-00"));
        assertThat(document.line1.name.trim(), is("JOHN DOE"));
        
        assertThat(document.line2.userID, isEmptyString());
        assertThat(document.line2.name, is(equalTo("NC")));
    }
    
    @Test
    public void fieldsShouldBeProcessedInOrderOfDeclaration() throws MisconfiguredDocumentException {
        PositionalDocumentOrderedSample document = new PositionalDocumentOrderedSample();
        DocumentParser<PositionalDocumentOrderedSample> parser = new DocumentParser<>(document);
        parser.parse("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ~3-00                BILL WARD                     ");
        
        assertThat(document.line3.userID.trim(), is("1-00"));
        assertThat(document.line2.userID.trim(), is("2-00"));
        assertThat(document.line1.userID.trim(), is("3-00"));
    }
}
