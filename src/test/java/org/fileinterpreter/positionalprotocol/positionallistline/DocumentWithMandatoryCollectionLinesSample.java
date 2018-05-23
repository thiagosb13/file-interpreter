package org.fileinterpreter.positionalprotocol.positionallistline;

import java.util.Collection;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.positionalprotocol.optionallines.PositionalLineSimpleSample;

@Document
public class DocumentWithMandatoryCollectionLinesSample {
	@PositionalLine(pattern = "AA.*")
    public PositionalLineSimpleSample line1;

    @PositionalLine(pattern = "BB.*")
    public Collection<PositionalLineSimpleSample> line2;

    @PositionalLine(pattern = "CC.*")
    public PositionalLineSimpleSample line3;
}
