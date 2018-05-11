package org.fileinterpreter.core;

public abstract class Line {
    protected abstract void add(Field field);

    public abstract void parse(String content);

    public abstract String toContent();
}
