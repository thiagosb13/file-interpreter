package org.fileinterpreter.parser;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.pmw.tinylog.Logger;

public class DocumentToContent {
	public static String parse(Object document) throws MisconfiguredDocumentException, MisfilledDocumentException {
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
                Logger.error(e);
            }
        }

        return lines.stream()
                    .collect(Collectors.joining(DocumentCommons.getLineDelimiter(document)));
		
	}
    
	public static String getValueFrom(Field field, Object line) throws MisconfiguredDocumentException, IllegalAccessException, MisfilledDocumentException {
		PositionalLine positionalLine = PositionalLineParser.getConfigFrom(field);

		String value = "";

		try {
			value = positionalLine.parser().newInstance().toContent(line);
		
			if (isNullOrEmpty(value.trim())) {
				if (!positionalLine.optional())
					throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but has no value.", field.getName()));
			}
		} catch (InstantiationException e) {
            Logger.error(e);
		}
		
		return value;
	}    
}