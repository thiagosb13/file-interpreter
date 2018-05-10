package org.fileinterpreter.positionalprotocol;

import java.util.ArrayList;

import org.fileinterpreter.core.Document;

class PositionalDocumentSample extends Document {
    public PositionalDocumentSample() {
        lines = new ArrayList<>();
        
        PositionalLineSample line1 = new PositionalLineSample();
        lines.add(line1);
        
        PositionalLineSample line2 = new PositionalLineSample();
        lines.add(line2);
    }
}