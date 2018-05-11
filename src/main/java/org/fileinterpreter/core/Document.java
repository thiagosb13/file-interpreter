package org.fileinterpreter.core;

import com.google.common.base.Joiner;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
                 .forEach(idx -> line.get(idx).setLineValue(linesText[idx]));
	}

	public void objectToText() {
        text = Joiner.on(getLineDelimiter())
                     .join(lines.stream()
                                .map(Line::getLineValue)
                                .toArray());
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
