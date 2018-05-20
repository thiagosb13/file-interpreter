package org.fileinterpreter.parser;

import org.fileinterpreter.exception.MisconfiguredDocumentException;

public interface LineParser {
    void parse(String content, Object object) throws MisconfiguredDocumentException;
    String toContent(Object object) throws MisconfiguredDocumentException;
}
