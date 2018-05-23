package org.fileinterpreter.parser;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fileinterpreter.annotation.Document;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;

import com.google.common.base.Supplier;

public class DocumentParser<T> {
    private Class<T> templateClass;

    public DocumentParser(Class<T> templateClass) {
        this.templateClass = Objects.requireNonNull(templateClass);
    }

    public static String toContent(Object document) throws MisconfiguredDocumentException, MisfilledDocumentException {
        List<String> lines = new ArrayList<>();

        Field[] fields = document.getClass().getFields();

        for (Field field : fields) {
            try {
            	Object line = field.get(document);

            	if (line instanceof Collection) {
            		for (Object lineItem : (Collection<?>)line) {
            			String value = getValueFrom(field, lineItem);

                        if (!isNullOrEmpty(value))
                        	lines.add(value);
            		}
            	} else {
	                String value = getValueFrom(field, line);
	
	                if (!isNullOrEmpty(value))
	                	lines.add(value);
            	}
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // FIXME: Empty catch
                e.printStackTrace();
            }
        }

        return lines.stream()
                    .collect(Collectors.joining(getLineDelimiter(document)));
    }

    public T parse(String content) throws MisconfiguredDocumentException, MisfilledDocumentException {
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
        
        int i = 0;
        for (Field field : fields) {
            PositionalLine positionalLine = getPositionalLineFrom(field);
            
            try {
            	Object line = field.get(parsedObject);
            	
            	if (line == null) {
            		Class<?> fieldType = field.getType();
            		
            		if (fieldType == Collection.class) {
            			line = new ArrayList<>();
            		} else {
            			line = fieldType.newInstance();
            		}
            		
            		field.set(parsedObject, line);
            	}

            	if (line instanceof Collection) {
                    ParameterizedType lineType = (ParameterizedType) field.getGenericType();
                    Class<?> lineClass = (Class<?>) lineType.getActualTypeArguments()[0];
                    
                    if (!linesText.get().skip(i).findFirst().get().matches(positionalLine.pattern()) && !positionalLine.optional())
                        throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but its content is not filled out.", field.getName()));
                    
                    while (linesText.get().skip(i).findFirst().get().matches(positionalLine.pattern())) {
                        String contentLine = linesText.get().skip(i).findFirst().get();
                        
                        Object lineItem = lineClass.newInstance();
                        positionalLine.parser().newInstance().parse(contentLine, lineItem);
                        
                        ((Collection) line).add(lineItem);
                        
                        i++;
                    }
            	} else {
	            	String contentLine = getContentLine(linesText.get(), i, positionalLine.pattern());
	            	
	            	if (isNullOrEmpty(contentLine) && !positionalLine.optional())
	            		throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but its content is not filled out.", field.getName()));
	            	
	            	positionalLine.parser().newInstance().parse(contentLine, line);
            	}
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                // FIXME: Empty catch
                e.printStackTrace();
            }
            
            i++;
        }

        return parsedObject;
    }
    
	public static String getValueFrom(Field field, Object line) throws MisconfiguredDocumentException, IllegalAccessException, MisfilledDocumentException {
		PositionalLine positionalLine = getPositionalLineFrom(field);

		String value = "";

		try {
			value = positionalLine.parser().newInstance().toContent(line);
		
			if (isNullOrEmpty(value.trim())) {
				if (!positionalLine.optional())
					throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but has no value.", field.getName()));
			}
		} catch (InstantiationException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}    

    private String getContentLine(Stream<String> linesText, int index, String pattern) {
        if (!isNullOrEmpty(pattern)){
            Optional<String> lineContent = linesText.filter(l -> Pattern.matches(pattern, l))
                                                    .findAny();

            return lineContent.orElse(null);
        }

        return linesText.skip(index)
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
