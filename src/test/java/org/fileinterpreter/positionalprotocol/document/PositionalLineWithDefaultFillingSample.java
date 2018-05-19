package org.fileinterpreter.positionalprotocol.document;

import org.fileinterpreter.annotation.PositionalField;

public class PositionalLineWithDefaultFillingSample {
    @PositionalField(name = "User ID", startIndex = 1, size = 20, spaceFilling = '#')
    public String userID;
    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", spaceFilling = '*')
    public String name;
}