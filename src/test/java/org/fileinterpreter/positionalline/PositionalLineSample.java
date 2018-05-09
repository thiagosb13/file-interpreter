package org.fileinterpreter.positionalline;

import org.fileinterpreter.core.PositionalField;
import org.fileinterpreter.core.PositionalLine;

class PositionalLineSample extends PositionalLine {
    public PositionalField userID;
    public PositionalField name;

    public PositionalLineSample() {
        userID = new PositionalField(this).named("User ID")
                                          .startingAt(1)
                                          .withSize(20);

        name = new PositionalField(this).named("User Name")
                                        .startingAt(21)
                                        .withSize(30)
                                        .withDefaultValue("NC");
    }
}