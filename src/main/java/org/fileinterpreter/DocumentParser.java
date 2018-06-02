package org.fileinterpreter;

import java.util.Objects;

import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.fileinterpreter.parser.DocumentToContent;
import org.fileinterpreter.parser.ContentToDocument;

public class DocumentParser<T> {
	private Class<T> templateClass;
	
	public DocumentParser(Class<T> templateClass) {
        this.templateClass = Objects.requireNonNull(templateClass);
    }

    public static String toContent(Object document) throws MisconfiguredDocumentException, MisfilledDocumentException {
        return DocumentToContent.parse(document);
    }

    public T parse(String content) throws MisconfiguredDocumentException, MisfilledDocumentException {
        return new ContentToDocument<T>(templateClass).parse(content);
    }
}
