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
	private static List<String> lines;
	
	public static String parse(Object document) {
    	lines = new ArrayList<>();

        parseDocumentToContent(document);

        return lines.stream()
                    .collect(Collectors.joining(Documents.getLineDelimiter(document)));
	}

	private static void parseDocumentToContent(Object document) {
		Field[] fields = document.getClass().getFields();

        for (Field field : fields) {
            parseFieldToContent(field, document);
        }
	}

	private static void parseFieldToContent(Field field, Object document) {
		try {
			Object line = field.get(document);

			if (line instanceof Collection) {
				for (Object lineItem : (Collection<?>)line) {
					parseFieldToLineContent(field, lineItem);
				}
			} else {
		        parseFieldToLineContent(field, line);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
		    Logger.getLogger(DocumentToContent.class.getCanonicalName())
		    	  .severe(e.getMessage());
		}
	}

	private static void parseFieldToLineContent(Field field, Object line) {
		String value = getValueFrom(field, line);

		if (!isNullOrEmpty(value))
			lines.add(value);
	}
    
	public static String getValueFrom(Field field, Object line) {
		PositionalLine positionalLine = PositionalLineParser.getConfigFrom(field);

		String value = Strings.EMPTY;

		try {
			value = positionalLine.parser().newInstance().toContent(line);
		
			if (isNullOrEmpty(value.trim()) && !positionalLine.optional())
				throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but has no value.", field.getName()));
		} catch (InstantiationException | IllegalAccessException e) {
            Logger.getLogger(DocumentToContent.class.getCanonicalName())
            	  .severe(e.getMessage());
		}
		
		return value;
	}    
}
