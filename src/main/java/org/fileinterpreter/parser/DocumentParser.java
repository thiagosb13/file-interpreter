package org.fileinterpreter.parser;

import java.util.Objects;

public class DocumentParser<T> {
    private T templateClass;

    public DocumentParser(T templateClass) {
        this.templateClass = Objects.requireNonNull(templateClass);
    }

    public String toContent() {
        return "";
    }
    
    public T parse(String content) {
        Objects.requireNonNull(content);
        
        return null;
    }
}
