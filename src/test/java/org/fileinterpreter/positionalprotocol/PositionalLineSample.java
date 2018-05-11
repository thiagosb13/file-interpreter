package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.core.PositionalField;
import org.fileinterpreter.core.PositionalLine;

class PositionalLineSample extends PositionalLine {
    public PositionalField userID;
    public PositionalField name;

    public PositionalLineSample() {
        userID = PositionalField.create()
                                .named("User ID")
                                .startingAt(1)
                                .withSize(20);
        add(userID);

        name = PositionalField.create()
                              .named("User Name")
                              .startingAt(21)
                              .withSize(30)
                              .withDefaultValue("NC");
        add(name);
    }
}