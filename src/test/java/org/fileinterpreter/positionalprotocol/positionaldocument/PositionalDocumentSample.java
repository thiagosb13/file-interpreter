package org.fileinterpreter.positionalprotocol.positionaldocument;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document
public class PositionalDocumentSample {
    @PositionalLine
    public PositionalLineSample line1;
    
    @PositionalLine
    public PositionalLineSample line2;
}