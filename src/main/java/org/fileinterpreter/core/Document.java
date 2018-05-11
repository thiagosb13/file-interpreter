package org.fileinterpreter.core;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Document {
	private String lineDelimiter;
	protected List<Line> lines;

	protected String text;

	public String getText() {
		objectToText();
		return text;
	}

	public void setText(String value) {
		text = value;
		textToObject();
	}

	public void textToObject() {
		String[] linesText = text.split(getLineDelimiter());

		List<Line> line = getLines().collect(toList());
        IntStream.range(0, linesText.length)
                 .forEach(idx -> line.get(idx).setValue(linesText[idx]));
	}

	public void objectToText() {
	    text = lines.stream()
                    .map(Line::getValue)
                    .collect(Collectors.joining(getLineDelimiter()));
	}

	private String getLineDelimiter() {
		return lineDelimiter != null ? lineDelimiter : "\r\n";
	}

	public void setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
	}

	public Stream<Line> getLines() {
        return Optional.ofNullable(lines).map(List::stream).orElseGet(Stream::empty);
	}
}
