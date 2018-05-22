package org.fileinterpreter.positionalprotocol.misconfiguredpositionaldocument;

import org.fileinterpreter.annotation.PositionalField;

public class PositionalMisconfiguredLineSample {
    @PositionalField(name = "User ID", startIndex = 1, size = 20)
    public String userID;

    public String name;
}
