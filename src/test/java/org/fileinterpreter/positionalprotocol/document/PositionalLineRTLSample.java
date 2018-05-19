package org.fileinterpreter.positionalprotocol.document;

import org.fileinterpreter.annotation.PositionalField;

public class PositionalLineRTLSample {
    @PositionalField(name = "User ID", startIndex = 1, size = 20)
    public String userID;
    @PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC", rtl = true)
    public String name;
}