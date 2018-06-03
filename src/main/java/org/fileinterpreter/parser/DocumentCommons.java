package org.fileinterpreter.parser;

import java.util.logging.Logger;

import org.fileinterpreter.annotation.Document;

public class DocumentCommons {
    public static String getLineDelimiter(Object documentTemplate) {
        String lineDelimiter = "";
        
        try {
            Document document = documentTemplate.getClass().getDeclaredAnnotation(Document.class);
            
            lineDelimiter = document.lineDelimiter();
        } catch (IllegalArgumentException e) {
            Logger.getLogger("org.fileinterpreter.parser.DocumentCommons")
            	  .severe(e.getStackTrace().toString());
        }
        
        return lineDelimiter;
    }
}