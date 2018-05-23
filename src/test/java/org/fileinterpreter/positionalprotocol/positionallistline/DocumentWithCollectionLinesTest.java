package org.fileinterpreter.positionalprotocol.positionallistline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.fileinterpreter.positionalprotocol.optionallines.PositionalLineSimpleSample;
import org.junit.Test;

public class DocumentWithCollectionLinesTest {

	@Test
	public void whenALineIsACollectionShouldFillTheObjectWithAllLinesFromThatPatternInTheContent() throws MisconfiguredDocumentException, MisfilledDocumentException {
        DocumentParser<DocumentWithCollectionLinesSample> parser = new DocumentParser<>(DocumentWithCollectionLinesSample.class);
        DocumentWithCollectionLinesSample document = parser.parse("AA1-00              JOHN DOE                      \r\nBB2-00              OZZY HUNT                     \r\nBB3-00              ANTHONY B                     \r\nBB4-00              MARK HUNT                     \r\nCC5-00              BILL WARD                     ");
        
        assertThat(document.line1.userID.trim(), is("AA1-00"));
        assertThat(document.line2.size(), is(3));
        assertThat(document.line2.stream().findFirst().get().userID.trim(), is("BB2-00"));
        assertThat(document.line2.stream().skip(1).findFirst().get().userID.trim(), is("BB3-00"));
        assertThat(document.line2.stream().skip(2).findFirst().get().userID.trim(), is("BB4-00"));
        assertThat(document.line3.userID.trim(), is("CC5-00"));
	}
	
	@Test
	public void whenALineIsACollectionShouldFillTheContentWithEachItemFromTheList() throws MisconfiguredDocumentException, MisfilledDocumentException {
		DocumentWithCollectionLinesSample document = new DocumentWithCollectionLinesSample();
		document.line1 = new PositionalLineSimpleSample();
		document.line1.userID = "AA1-00";
        document.line1.name = "JOHN DOE";
        
        document.line2 = new ArrayList<>();
        PositionalLineSimpleSample line2A = new PositionalLineSimpleSample();
        line2A.userID = "BB2-00";
        line2A.name = "OZZY HUNT";
        document.line2.add(line2A);
        
        PositionalLineSimpleSample line2B = new PositionalLineSimpleSample();
        line2B.userID = "BB3-00";
        line2B.name = "MARK HUNT";
        document.line2.add(line2B);
        
        document.line3 = new PositionalLineSimpleSample();
        document.line3.userID = "CC4-00";
        document.line3.name = "BILL WARD";
        
        assertThat(DocumentParser.toContent(document), is("AA1-00              JOHN DOE                      \r\nBB2-00              OZZY HUNT                     \r\nBB3-00              MARK HUNT                     \r\nCC4-00              BILL WARD                     "));
	}
	
	@Test
	public void whenALineIsACollectionAndOptionalAndIsNotFilledShouldNotFillInTheContent() {
		fail();
	}
	
	@Test
	public void whenALineIsACollectionAndOptionalAndIsNotFilledInTheContentShouldNotFillInTheObject() {
		fail();
	}
}
