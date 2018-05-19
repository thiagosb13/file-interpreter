package org.fileinterpreter.positionalprotocol.document;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document(lineDelimiter = "~")
public class PositionalDocumentOrderedSample {
    @PositionalLine
    public PositionalLineSample line3;
    
    @PositionalLine
    public PositionalLineSample line2;
    
    @PositionalLine
    public PositionalLineSample line1;
    
    public PositionalDocumentOrderedSample() {
        line1 = new PositionalLineSample();
        line2 = new PositionalLineSample();
        line3 = new PositionalLineSample();
    }
}