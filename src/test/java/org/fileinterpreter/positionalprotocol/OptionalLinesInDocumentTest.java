package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.junit.Test;

public class OptionalLinesInDocumentTest {
	@Test
	public void whenALineIsOptionalAndItsNotInTheContentShouldNotFillTheObject() throws MisconfiguredDocumentException {
		DocumentWithOptionalLinesTest document = new DocumentWithOptionalLinesTest();
        DocumentParser<DocumentWithOptionalLinesTest> parser = new DocumentParser<>(document);
        parser.parse("AA1-00                JOHN DOE                      \r\nCC3-00                BILL WARD                     ");
        
        assertThat(document.line1.userID.trim(), is("AA1-00"));
        assertThat(document.line2.userID.trim(), isEmptyString());
        assertThat(document.line3.userID.trim(), is("CC3-00"));
	}

	@Test(expected = MisfilledDocumentException.class)
	public void whenALineIsNotOptionalAndItsNotInTheContentShouldThrowAnException() throws MisconfiguredDocumentException {
		DocumentWithOptionalLinesTest document = new DocumentWithOptionalLinesTest();
        DocumentParser<DocumentWithOptionalLinesTest> parser = new DocumentParser<>(document);
        parser.parse("BB2-00                JOHN DOE                      \r\nCC3-00                BILL WARD                     ");
	}
	
	@Test
	public void whenALineIsOptionalAndTheObjectIsNotFilledShouldNotFillInContent() throws MisconfiguredDocumentException {
		DocumentWithOptionalLinesTest document = new DocumentWithOptionalLinesTest();
        document.line1.userID = "AA1-00";
        document.line1.name = "JOHN DOE";
        
        document.line3.userID = "CC3-00";
        document.line3.name = "BILL WARD";

        DocumentParser<DocumentWithOptionalLinesTest> parser = new DocumentParser<>(document);
        
        assertThat(parser.toContent(), is("AA1-00                JOHN DOE                      \r\nCC3-00                BILL WARD                     "));
    }

	@Test(expected = MisfilledDocumentException.class)
	public void whenALineIsNotOptionalAndTheObjectIsNotFilledShouldNotThrowAnException() throws MisconfiguredDocumentException {
		DocumentWithOptionalLinesTest document = new DocumentWithOptionalLinesTest();
        document.line2.userID = "BB1-00";
        document.line2.name = "JOE BLACK";
        
        document.line3.userID = "CC3-00";
        document.line3.name = "BILL WARD";

        DocumentParser<DocumentWithOptionalLinesTest> parser = new DocumentParser<>(document);
        
        parser.toContent();
	}
	
	@Document
	public class DocumentWithOptionalLinesTest {
		@PositionalLine
        public PositionalLineSimpleSample line1;
        
        @PositionalLine(optional = true)
        public PositionalLineSimpleSample line2;
        
        @PositionalLine
        public PositionalLineSimpleSample line3;
        
        public DocumentWithOptionalLinesTest() {
            line1 = new PositionalLineSimpleSample();
            line2 = new PositionalLineSimpleSample();
            line3 = new PositionalLineSimpleSample();
        }
	}
	
	public class PositionalLineSimpleSample {
		@PositionalField(name = "User ID", startIndex = 1, size = 20)
		public String userID;
		@PositionalField(name = "User Name", startIndex = 21, size = 30)
		public String name;
	}
}
