package org.fileinterpreter.core;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Document {
	private static final String BREAK_LINE = "\r\n";
    private String lineDelimiter;
	protected List<Line> lines;

	public void parse(String text) {
		String[] linesText = text.split(getLineDelimiter());

		List<Line> line = getLines().collect(toList());
        IntStream.range(0, linesText.length)
                 .forEach(idx -> line.get(idx).setValue(linesText[idx]));
	}

	public String toText() {
	    return lines.stream()
                    .map(Line::getValue)
                    .collect(Collectors.joining(getLineDelimiter()));
	}

	private String getLineDelimiter() {
		return Optional.ofNullable(lineDelimiter).orElse(BREAK_LINE);
	}

	public void setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
	}

	public Stream<Line> getLines() {
        return Optional.ofNullable(lines).map(List::stream).orElseGet(Stream::empty);
	}
}
