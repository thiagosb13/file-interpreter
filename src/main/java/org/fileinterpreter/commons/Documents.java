package org.fileinterpreter.commons;

import java.util.logging.Logger;

import org.fileinterpreter.annotation.Document;

public class Documents {
    public static String getLineDelimiter(Object documentTemplate) {
        String lineDelimiter = "";
        
        try {
            Document document = documentTemplate.getClass().getDeclaredAnnotation(Document.class);
            
            lineDelimiter = document.lineDelimiter();
        } catch (IllegalArgumentException e) {
            Logger.getLogger(Documents.class.getCanonicalName())
            	  .severe(e.getMessage());
        }
        
        return lineDelimiter;
    }
}