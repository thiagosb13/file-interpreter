package org.fileinterpreter;

import java.util.Objects;

import org.fileinterpreter.parser.ContentToDocument;
import org.fileinterpreter.parser.DocumentToContent;

public class DocumentParser<T> {
	private Class<T> templateClass;
	
	public DocumentParser(Class<T> templateClass) {
        this.templateClass = Objects.requireNonNull(templateClass);
    }

    public static String toContent(Object document) {
        return DocumentToContent.parse(document);
    }

    public T parse(String content) {
        return new ContentToDocument<T>(templateClass).parse(content);
    }
}
