package org.fileinterpreter.core;

public abstract class Line {
    public abstract void add(Field field);

    protected abstract void textToObject();

    protected abstract void objectToText();

    protected String value;

    public String getValue() {
        objectToText();
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        textToObject();
    }
}
