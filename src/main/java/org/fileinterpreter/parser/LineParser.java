package org.fileinterpreter.parser;

import org.fileinterpreter.exception.MisconfiguredDocumentException;

public abstract class LineParser {
    public abstract void parse(String content, Object object) throws MisconfiguredDocumentException;
    public abstract String toContent(Object object) throws MisconfiguredDocumentException;
}
