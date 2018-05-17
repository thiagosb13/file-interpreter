package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.core.Campo;
import org.fileinterpreter.core.PositionalLine;

public class PositionalLineWithDefaultFillingSample extends PositionalLine {
    @PositionalField(name = "User ID", startIndex = 1, size = 20, spaceFilling = '#')
    public Campo userID;
    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", spaceFilling = '*')
    public Campo name;
    
    public PositionalLineWithDefaultFillingSample() {
        this.userID = new Campo();
        this.name = new Campo();
    }
}