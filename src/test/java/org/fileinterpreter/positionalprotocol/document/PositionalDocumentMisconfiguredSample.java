package org.fileinterpreter.positionalprotocol.document;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document
public class PositionalDocumentMisconfiguredSample {
    @PositionalLine
    public PositionalLineSample line1;
    
    public PositionalLineSample line2;
    
    public PositionalDocumentMisconfiguredSample() {
        line1 = new PositionalLineSample();
        line2 = new PositionalLineSample();
    }
}