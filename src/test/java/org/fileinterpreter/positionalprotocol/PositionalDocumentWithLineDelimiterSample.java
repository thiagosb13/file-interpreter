package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.annotation.Document;

@Document(lineDelimiter = "~")
class PositionalDocumentWithLineDelimiterSample {
    PositionalLineSample line1;
    PositionalLineSample line2;
}