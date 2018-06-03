package org.fileinterpreter.parser;

public interface LineParser {
    void parse(String content, Object object);
    String toContent(Object object);
}
