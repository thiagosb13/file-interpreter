package org.fileinterpreter.positionalprotocol.document;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document(lineDelimiter = "~")
public class PositionalDocumentWithLineDelimiterSample {
    @PositionalLine
    public PositionalLineSample line1;
    
    @PositionalLine
    public PositionalLineSample line2;

    public PositionalDocumentWithLineDelimiterSample() {
        line1 = new PositionalLineSample();
        line2 = new PositionalLineSample();
    }
}