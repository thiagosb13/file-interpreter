package org.fileinterpreter.core;

public abstract class Line {
    public abstract void add(Field field);

    protected abstract void textToObject();

    protected abstract void objectToText();

    protected String lineValue;

    public String getLineValue() {
        objectToText();
        return lineValue;
    }

    public void setLineValue(String value) {
        lineValue = value;
        textToObject();
    }
}
