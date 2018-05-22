package org.fileinterpreter.positionalprotocol.misconfiguredpositionaldocument;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.positionalprotocol.positionaldocument.PositionalLineSample;

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
