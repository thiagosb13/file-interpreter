package org.fileinterpreter.parser;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.exception.MisconfiguredDocumentException;
import org.fileinterpreter.exception.MisfilledDocumentException;
import org.pmw.tinylog.Logger;

import com.google.common.base.Supplier;

public class ContentToDocument<T> {
	private Class<T> templateClass;
	
	public ContentToDocument(Class<T> templateClass) {
		this.templateClass = Objects.requireNonNull(templateClass);
	}
	
	public T parse(String content) throws MisconfiguredDocumentException, MisfilledDocumentException {
		Objects.requireNonNull(content);

		T parsedObject = null;

		try {
			parsedObject = templateClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			Logger.error(e);
		}

		String delimiter = DocumentCommons.getLineDelimiter(parsedObject);
		Supplier<Stream<String>> linesText = () -> Pattern.compile(delimiter).splitAsStream(content);

		Field[] fields = parsedObject.getClass().getFields();

		int i = 0;
		for (Field field : fields) {
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

					if (!linesText.get().skip(i).findFirst().get().matches(positionalLine.pattern())
							&& !positionalLine.optional())
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
						throw new MisfilledDocumentException(String
								.format("Line '%s' is mandatory but its content is not filled out.", field.getName()));

					positionalLine.parser().newInstance().parse(contentLine, line);
				}
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
				Logger.error(e);
			}

			i++;
		}

		return parsedObject;
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
