package org.fileinterpreter.positionalprotocol.optionallines;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document
public class DocumentWithOptionalLines {
    @PositionalLine(pattern = "AA.*")
    public PositionalLineSimpleSample line1;

    @PositionalLine(pattern = "BB.*", optional = true)
    public PositionalLineSimpleSample line2;

    @PositionalLine(pattern = "CC.*")
    public PositionalLineSimpleSample line3;
}
