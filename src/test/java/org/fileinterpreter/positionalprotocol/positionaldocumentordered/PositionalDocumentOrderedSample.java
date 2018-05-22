package org.fileinterpreter.positionalprotocol.positionaldocumentordered;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.positionalprotocol.positionaldocument.PositionalLineSample;

@Document
public class PositionalDocumentOrderedSample {
    @PositionalLine
    public PositionalLineSample line3;

    @PositionalLine
    public PositionalLineSample line2;

    @PositionalLine
    public PositionalLineSample line1;
}
