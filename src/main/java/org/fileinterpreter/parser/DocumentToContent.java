package org.fileinterpreter.parser;

import static org.fileinterpreter.commons.Strings.isNullOrEmpty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.commons.Documents;
import org.fileinterpreter.commons.Strings;
import org.fileinterpreter.exception.MisfilledDocumentException;

public class DocumentToContent {
	public static String parse(Object document) {
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
                Logger.getLogger("org.fileinterpreter.parser.DocumentToContent")
                	  .severe(e.getMessage());
            }
        }

        return lines.stream()
                    .collect(Collectors.joining(Documents.getLineDelimiter(document)));
		
	}
    
	public static String getValueFrom(Field field, Object line) {
		PositionalLine positionalLine = PositionalLineParser.getConfigFrom(field);

		String value = Strings.EMPTY;

		try {
			value = positionalLine.parser().newInstance().toContent(line);
		
			if (isNullOrEmpty(value.trim())) {
				if (!positionalLine.optional())
					throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but has no value.", field.getName()));
			}
		} catch (InstantiationException | IllegalAccessException e) {
            Logger.getLogger("org.fileinterpreter.parser.DocumentToContent")
            	  .severe(e.getMessage());
		}
		
		return value;
	}    
}
