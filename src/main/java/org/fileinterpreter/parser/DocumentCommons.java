package org.fileinterpreter.parser;

import org.fileinterpreter.annotation.Document;
import org.pmw.tinylog.Logger;

public class DocumentCommons {
    public static String getLineDelimiter(Object documentTemplate) {
        String lineDelimiter = "";
        
        try {
            Document document = documentTemplate.getClass().getDeclaredAnnotation(Document.class);
            
            lineDelimiter = document.lineDelimiter();
        } catch (IllegalArgumentException e) {
            Logger.error(e);
        }
        
        return lineDelimiter;
    }
}