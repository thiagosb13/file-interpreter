package org.fileinterpreter.positionalline;

import org.fileinterpreter.core.PositionalField;
import org.fileinterpreter.core.PositionalLine;

class PositionalLineSample extends PositionalLine {
    public PositionalField userID;
    public PositionalField name;

    public PositionalLineSample() {
        userID = new PositionalField(this);
        userID.name = "User ID";
        userID.initialPos = 1;
        userID.size = 20;

        name = new PositionalField(this);
        name.name = "User Name";
        name.initialPos = 21;
        name.size = 30;
        name.defaultValue = "NC";
    }
}