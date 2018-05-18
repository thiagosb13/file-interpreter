package org.fileinterpreter.core;

public abstract class Line {
    public abstract void parse(String content);

    public abstract String toContent();
    public abstract String toContent(Object object);
}
