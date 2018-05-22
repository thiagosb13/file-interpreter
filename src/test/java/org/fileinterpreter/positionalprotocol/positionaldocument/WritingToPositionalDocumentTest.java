package org.fileinterpreter.positionalprotocol.positionaldocument;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WritingToPositionalDocumentTest {

    @Test
    public void shouldTransfomLineIntoPlainText() throws MisconfiguredDocumentException, MisfilledDocumentException {
        PositionalDocumentSample document = new PositionalDocumentSample();
        document.line1 = new PositionalLineSample();
        document.line1.userID = "1-00";
        document.line1.name = "JOHN DOE";

        document.line2 = new PositionalLineSample();
        document.line2.userID = "2-00";
        document.line2.name = "JOE BLACK";
        
        assertThat(DocumentParser.toContent(document), is("1-00                JOHN DOE                      \r\n2-00                JOE BLACK                     "));
    }
}
