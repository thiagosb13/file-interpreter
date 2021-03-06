package org.fileinterpreter.positionalprotocol.optionallines;

import org.fileinterpreter.DocumentParser;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

public class OptionalLinesInDocumentTest {
	@Test
	public void whenALineIsOptionalAndItsNotInTheContentShouldNotFillTheObject() throws MisconfiguredDocumentException, MisfilledDocumentException {
        DocumentParser<DocumentWithOptionalLines> parser = new DocumentParser<>(DocumentWithOptionalLines.class);
        DocumentWithOptionalLines document = parser.parse("AA1-00              JOHN DOE                      \r\nCC3-00              BILL WARD                     ");
        
        assertThat(document.line1.userID.trim(), is("AA1-00"));
        assertThat(document.line2.userID.trim(), isEmptyString());
        assertThat(document.line3.userID.trim(), is("CC3-00"));
	}

	@Test(expected = MisfilledDocumentException.class)
	public void whenALineIsNotOptionalAndItsNotInTheContentShouldThrowAnException() throws MisconfiguredDocumentException, MisfilledDocumentException {
        DocumentParser<DocumentWithOptionalLines> parser = new DocumentParser<>(DocumentWithOptionalLines.class);
        parser.parse("BB2-00              JOHN DOE                      \r\nCC3-00              BILL WARD                     ");
	}
	
	@Test
	public void whenALineIsOptionalAndTheObjectIsNotFilledShouldNotFillInContent() throws MisconfiguredDocumentException, MisfilledDocumentException {
		DocumentWithOptionalLines document = new DocumentWithOptionalLines();
		document.line1 = new PositionalLineSimpleSample();
		document.line1.userID = "AA1-00";
        document.line1.name = "JOHN DOE";
        
        document.line3 = new PositionalLineSimpleSample();
        document.line3.userID = "CC3-00";
        document.line3.name = "BILL WARD";
        
        assertThat(DocumentParser.toContent(document), is("AA1-00              JOHN DOE                      \r\nCC3-00              BILL WARD                     "));
    }

	@Test(expected = MisfilledDocumentException.class)
	public void whenALineIsNotOptionalAndTheObjectIsNotFilledShouldNotThrowAnException() throws MisconfiguredDocumentException, MisfilledDocumentException {
		DocumentWithOptionalLines document = new DocumentWithOptionalLines();
		document.line2 = new PositionalLineSimpleSample();
        document.line2.userID = "BB1-00";
        document.line2.name = "JOE BLACK";
        
        document.line3 = new PositionalLineSimpleSample();
        document.line3.userID = "CC3-00";
        document.line3.name = "BILL WARD";

        DocumentParser.toContent(document);
	}
}
