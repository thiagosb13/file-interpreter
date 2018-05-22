package org.fileinterpreter.positionalprotocol.positionaldocument;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PositionalDocumentTest {
    @Test
    public void shouldNotBeNeededToInstanceObject() throws MisconfiguredDocumentException, MisfilledDocumentException {
        DocumentParser<PositionalDocumentSample> parser = new DocumentParser<>(PositionalDocumentSample.class);
        PositionalDocumentSample document = parser.parse("1-00                JOHN DOE                      \r\n2-00                JOE BLACK                     ");

        assertThat(document.line1, is(notNullValue()));
        assertThat(document.line2, is(notNullValue()));
    }
}
