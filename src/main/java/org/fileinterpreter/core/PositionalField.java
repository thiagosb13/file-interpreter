package org.fileinterpreter.core;

public class PositionalField extends Field {
    public int initialPos;
    public int size;
    public char defaultChar;
    public boolean rtl;

    public PositionalField(ILine line) {
        super(line);
        defaultChar = ' ';
        rtl = false;
    }
}
