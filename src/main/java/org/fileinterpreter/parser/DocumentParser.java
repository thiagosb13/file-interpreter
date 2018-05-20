package org.fileinterpreter.parser;

import com.google.common.base.Supplier;
import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;

public class DocumentParser<T> {
    private Class<T> templateClass;

    public DocumentParser(Class<T> templateClass) {
        this.templateClass = Objects.requireNonNull(templateClass);
    }

    public static String toContent(Object document) throws MisconfiguredDocumentException {
        List<String> lines = new ArrayList<>();

        Field[] fields = document.getClass().getFields();

        for (Field field : fields) {
            try {
                PositionalLine positionalLine = getPositionalLineFrom(field);

                Object line = field.get(document);

                String value = positionalLine.parser().newInstance().toContent(line);
                if (isNullOrEmpty(value.trim())){
                    continue;
                }

                lines.add(value);
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                // FIXME: Empty catch
                e.printStackTrace();
            }
        }

        return lines.stream()
                    .collect(Collectors.joining(getLineDelimiter(document)));
    }

    public T parse(String content) throws MisconfiguredDocumentException {
        Objects.requireNonNull(content);

        T parsedObject = null;

        try {
            parsedObject = templateClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // FIXME: Empty catch
            e.printStackTrace();
        }

        String delimiter = getLineDelimiter(parsedObject);
        Supplier<Stream<String>> linesText = () -> Pattern.compile(delimiter).splitAsStream(content);

        Field[] fields = parsedObject.getClass().getFields();
        
        for (int i = 0; i < fields.length; i++) {
            PositionalLine positionalLine = getPositionalLineFrom(fields[i]);

            String contentLine = getContentLine(linesText.get(), i, positionalLine.pattern());

            try {
                Object line = fields[i].get(parsedObject);
                positionalLine.parser().newInstance().parse(contentLine, line);
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                // FIXME: Empty catch
                e.printStackTrace();
            }
        }

        return parsedObject;
    }

    private String getContentLine(Stream<String> linesText, int i, String pattern) {
        if (!isNullOrEmpty(pattern)){
            Optional<String> lineContent = linesText.filter(l -> Pattern.matches(pattern, l))
                                                    .findAny();

            return lineContent.orElse(null);
        }

        return linesText.skip(i)
                        .findFirst()
                        .orElse(null);
    }

    private static PositionalLine getPositionalLineFrom(Field field) throws MisconfiguredDocumentException {
    	PositionalLine positionalLine = field.getDeclaredAnnotation(PositionalLine.class);
    	
    	if (positionalLine == null)
    		throw new MisconfiguredDocumentException(String.format("Line '%s' is not annotated correctly.", field.getName()));
    	
    	return positionalLine;
    }
    
    private static String getLineDelimiter(Object documentTemplate) {
        String lineDelimiter = "";
        
        try {
            Document document = documentTemplate.getClass().getDeclaredAnnotation(Document.class);
            
            lineDelimiter = document.lineDelimiter();
        } catch (IllegalArgumentException e) {
            // FIXME: Empty catch
            e.printStackTrace();
        }
        
        return lineDelimiter;
    }
}
