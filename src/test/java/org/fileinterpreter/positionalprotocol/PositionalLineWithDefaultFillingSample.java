package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.core.PositionalLineParser;

public class PositionalLineWithDefaultFillingSample extends PositionalLineParser {
    @PositionalField(name = "User ID", startIndex = 1, size = 20, spaceFilling = '#')
    public String userID;
    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", spaceFilling = '*')
    public String name;
}