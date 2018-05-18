package org.fileinterpreter.positionalprotocol;

import java.util.ArrayList;

import org.fileinterpreter.core.Document;

class PositionalDocumentWithDefaultFillingSample extends Document {
    public PositionalDocumentWithDefaultFillingSample() {
        lines = new ArrayList<>();
        
        PositionalLineWithDefaultFillingSample line1 = new PositionalLineWithDefaultFillingSample();
        lines.add(line1);
        
        PositionalLineWithDefaultFillingSample line2 = new PositionalLineWithDefaultFillingSample();
        lines.add(line2);
    }
}