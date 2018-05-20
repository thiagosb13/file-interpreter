package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.junit.Test;

public class PositionalDocumentOrderedFieldsTest {
    
    @Test
    public void fieldsShouldBeProcessedInOrderOfDeclaration() throws MisconfiguredDocumentException {
        DocumentParser<PositionalDocumentOrderedSample> parser = new DocumentParser<>(PositionalDocumentOrderedSample.class);
        PositionalDocumentOrderedSample document = parser.parse("1-00                JOHN DOE                      \r\n2-00                JOE BLACK                     \r\n3-00                BILL WARD                     ");
        
        assertThat(document.line3.userID.trim(), is("1-00"));
        assertThat(document.line2.userID.trim(), is("2-00"));
        assertThat(document.line1.userID.trim(), is("3-00"));
    }
}
