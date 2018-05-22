package org.fileinterpreter.positionalprotocol.positionaldocumentwithLinedelimiter;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.positionalprotocol.positionaldocument.PositionalLineSample;

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
