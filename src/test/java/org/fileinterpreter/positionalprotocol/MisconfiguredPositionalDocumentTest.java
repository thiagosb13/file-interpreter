package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.parser.DocumentParser;
import org.fileinterpreter.parser.PositionalLineParser;
import org.junit.Test;

public class MisconfiguredPositionalDocumentTest {
	@Test(expected = MisconfiguredDocumentException.class)
	public void whenGettingContentFromAMisconfiguredLineShouldThrowAnException() throws MisconfiguredDocumentException {
		PositionalMisconfiguredLineSample sample = new PositionalMisconfiguredLineSample();
		sample.name = "JOHN DOE";
		sample.userID = "1-00";
		
		new PositionalLineParser().toContent(sample);
	}
	
	@Test(expected = MisconfiguredDocumentException.class)
	public void whenParsingAMisconfiguredLineShouldThrowAnException() throws MisconfiguredDocumentException {
		PositionalMisconfiguredLineSample sample = new PositionalMisconfiguredLineSample();
        PositionalLineParser parser = new PositionalLineParser();
        parser.parse("1-00                JOHN DOE                      ", sample);
	}
	
	@Test(expected = MisconfiguredDocumentException.class)
	public void whenGettingContentFromADocumentWithMisconfiguredLineShouldThrowAnException() throws MisconfiguredDocumentException {
		PositionalDocumentMisconfiguredSample document = new PositionalDocumentMisconfiguredSample();
        document.line1.userID = "1-00";
        document.line1.name = "JOHN DOE";
        
        document.line2.userID = "2-00";
        document.line2.name = "JOE BLACK";

        DocumentParser.toContent(document);
	}
	
	@Test(expected = MisconfiguredDocumentException.class)
	public void whenParsingADocumentWithMisconfiguredLineShouldThrowAnException() throws MisconfiguredDocumentException {
		DocumentParser<PositionalDocumentMisconfiguredSample> parser = new DocumentParser<>(PositionalDocumentMisconfiguredSample.class);
		parser.parse("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
	}

}
