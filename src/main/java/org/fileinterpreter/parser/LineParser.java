package org.fileinterpreter.parser;

public abstract class LineParser {
    public abstract void parse(String content, Object object);
    public abstract String toContent(Object object);
}
