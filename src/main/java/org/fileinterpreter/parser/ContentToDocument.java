package org.fileinterpreter.parser;

import static org.fileinterpreter.commons.Strings.isNullOrEmpty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;

public class ContentToDocument<T> {
	private Class<T> templateClass;
	private T parsedObject;
	private Supplier<Stream<String>> contentLines;
	private int currentLineIndex;
	
	public ContentToDocument(Class<T> templateClass) {
		this.templateClass = Objects.requireNonNull(templateClass);
	}
	
	public T parse(String content) throws MisconfiguredDocumentException, MisfilledDocumentException {
		Objects.requireNonNull(content);

		createANewParsedObject();

		splitContentInLines(content);

		parseContentToFields();

		return parsedObject;
	}

	private void parseContentToFields() throws MisconfiguredDocumentException, MisfilledDocumentException {
		Field[] fields = parsedObject.getClass().getFields();

		for (Field field : fields) {
			parseContentTo(field);
		}
	}

	private void parseContentTo(Field field) throws MisconfiguredDocumentException, MisfilledDocumentException {
		PositionalLine positionalLine = PositionalLineParser.getConfigFrom(field);

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

				if (!contentLines.get().skip(currentLineIndex).findFirst().get().matches(positionalLine.pattern()) && !positionalLine.optional())
					throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but its content is not filled out.", field.getName()));

				while (contentLines.get().skip(currentLineIndex).findFirst().get().matches(positionalLine.pattern())) {
					String contentLine = contentLines.get().skip(currentLineIndex).findFirst().get();

					Object lineItem = lineClass.newInstance();
					positionalLine.parser().newInstance().parse(contentLine, lineItem);

					((Collection) line).add(lineItem);

					currentLineIndex++;
				}
			} else {
				String contentLine = getContentLine(contentLines.get(), currentLineIndex, positionalLine.pattern());

				if (isNullOrEmpty(contentLine) && !positionalLine.optional())
					throw new MisfilledDocumentException(String.format("Line '%s' is mandatory but its content is not filled out.", field.getName()));

				positionalLine.parser().newInstance().parse(contentLine, line);
			}
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			Logger.getLogger("org.fileinterpreter.parser.ContentToDocument")
				  .severe(e.getStackTrace().toString());
		}
		
		currentLineIndex++;
	}

	private void createANewParsedObject() {
		parsedObject = null;

		try {
			parsedObject = templateClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			Logger.getLogger("org.fileinterpreter.parser.ContentToDocument")
				  .severe(e.getStackTrace().toString());
		}
	}
	
	private void splitContentInLines(String content) {
		String delimiter = DocumentCommons.getLineDelimiter(parsedObject);
		contentLines = () -> Pattern.compile(delimiter).splitAsStream(content);
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
}
