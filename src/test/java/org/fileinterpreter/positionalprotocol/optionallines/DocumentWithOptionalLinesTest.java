package org.fileinterpreter.positionalprotocol.optionallines;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;

@Document
public class DocumentWithOptionalLinesTest {
    @PositionalLine(pattern = "AA.*")
    public PositionalLineSimpleSample line1;

    @PositionalLine(pattern = "BB.*", optional = true)
    public PositionalLineSimpleSample line2;

    @PositionalLine(pattern = "CC.*")
    public PositionalLineSimpleSample line3;

    public DocumentWithOptionalLinesTest() {
        line1 = new PositionalLineSimpleSample();
        line2 = new PositionalLineSimpleSample();
        line3 = new PositionalLineSimpleSample();
    }
}
