package org.fileinterpreter.positionalprotocol.misconfiguredpositionaldocument;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.positionalprotocol.positionaldocument.PositionalLineSample;

@Document
public class PositionalDocumentMisconfiguredSample {
    @PositionalLine
    public PositionalLineSample line1;

    public PositionalLineSample line2;
}
