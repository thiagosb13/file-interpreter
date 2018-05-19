package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.annotation.PositionalLine;
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

        new DocumentParser<PositionalDocumentMisconfiguredSample>(document).toContent();
	}
	
	@Test(expected = MisconfiguredDocumentException.class)
	public void whenParsingADocumentWithMisconfiguredLineShouldThrowAnException() throws MisconfiguredDocumentException {
		PositionalDocumentMisconfiguredSample document = new PositionalDocumentMisconfiguredSample();
		DocumentParser<PositionalDocumentMisconfiguredSample> parser = new DocumentParser<>(document);
		parser.parse("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
	}
	
	@Document
	private class PositionalDocumentMisconfiguredSample {
	    @PositionalLine
	    public PositionalLineSample line1;
	    
	    public PositionalLineSample line2;
	    
	    public PositionalDocumentMisconfiguredSample() {
	        line1 = new PositionalLineSample();
	        line2 = new PositionalLineSample();
	    }
	}
	
	private class PositionalMisconfiguredLineSample {
	    @PositionalField(name = "User ID", startIndex = 1, size = 20)
	    public String userID;

	    public String name;
	}
}
