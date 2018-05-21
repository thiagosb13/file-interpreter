package org.fileinterpreter.positionalprotocol;


import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document(lineDelimiter = "~")
public class PositionalDocumentWithLineDelimiterSample {
    @PositionalLine
    public PositionalLineSample line1;

    @PositionalLine(optional = true)
    public PositionalLineSample line2;

    public PositionalDocumentWithLineDelimiterSample() {
        line1 = new PositionalLineSample();
        line2 = new PositionalLineSample();
    }
}
