package org.fileinterpreter.parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;

public class DocumentParser<T> {
    private T templateClass;

    public DocumentParser(T templateClass) {
        this.templateClass = Objects.requireNonNull(templateClass);
    }

    public String toContent() throws MisconfiguredDocumentException {
        List<String> lines = new ArrayList<>();

        Field[] fields = templateClass.getClass().getFields();

        for (Field field : fields) {
            try {
                PositionalLine positionalLine = getPositionalLineFrom(field);

                Object line = field.get(templateClass);

                String value = positionalLine.parser().newInstance().toContent(line);

                lines.add(value);
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                // FIXME: Empty catch
                e.printStackTrace();
            }
        }

        return lines.stream()
                    .collect(Collectors.joining(getLineDelimiter()));
    }

    public void parse(String content) throws MisconfiguredDocumentException {
        Objects.requireNonNull(content);

        String[] linesText = content.split(getLineDelimiter());
        int linesContentSize = linesText.length;

        Field[] fields = templateClass.getClass().getFields();
        
        for (int i = 0; i < fields.length; i++) {
            String contentLine = i < linesContentSize ? linesText[i] : null;
            
            PositionalLine positionalLine = getPositionalLineFrom(fields[i]);

            try {
                Object line = fields[i].get(templateClass);
                positionalLine.parser().newInstance().parse(contentLine, line);
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                // FIXME: Empty catch
                e.printStackTrace();
            }
        }        
    }
    
    private PositionalLine getPositionalLineFrom(Field field) throws MisconfiguredDocumentException {
    	PositionalLine positionalLine = field.getDeclaredAnnotation(PositionalLine.class);
    	
    	if (positionalLine == null)
    		throw new MisconfiguredDocumentException(String.format("Line '%s' is not annotated correctly.", field.getName()));
    	
    	return positionalLine;
    }
    
    private String getLineDelimiter() {
        String lineDelimiter = "";
        
        try {
            Document document = templateClass.getClass().getDeclaredAnnotation(Document.class);
            
            lineDelimiter = document.lineDelimiter();
        } catch (IllegalArgumentException e) {
            // FIXME: Empty catch
            e.printStackTrace();
        }
        
        return lineDelimiter;
    }
}
