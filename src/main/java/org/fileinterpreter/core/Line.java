package org.fileinterpreter.core;

public abstract class Line {
    public abstract void parse(String content);

    public abstract String toContent();
}
