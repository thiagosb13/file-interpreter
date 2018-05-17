package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.core.Campo;
import org.fileinterpreter.core.PositionalLine;

public class PositionalLineRTLSample extends PositionalLine {
    @PositionalField(name = "User ID", startIndex = 1, size = 20)
    public Campo userID;
    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", rtl = true)
    public Campo name;
    
    public PositionalLineRTLSample() {
        this.userID = new Campo();
        this.name = new Campo();
    }
}