package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.parser.DocumentParser;
import org.junit.Test;

public class WritingToPositionalDocumentTest {

    @Test
    public void shouldTransfomLineIntoPlainText() {
        PositionalDocumentSample document = new PositionalDocumentSample();
        DocumentParser<PositionalDocumentSample> parser = new DocumentParser<>(document);
        document.line1.userID = "1-00";
        document.line1.name = "JOHN DOE";

        document.line2.userID = "2-00";
        document.line2.name = "JOE BLACK";
        
        assertThat(parser.toContent(), is("1-00                JOHN DOE                      \r\n2-00                JOE BLACK                     "));
    }
}
