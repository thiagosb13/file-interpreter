package org.fileinterpreter.core;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

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

		for (int i = 0; i < getLines().size(); i++) {
			Line line = getLines().get(i);
			line.setLineValue(linesText[i]);
		}
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

	public List<Line> getLines() {
		return lines != null ? lines : new ArrayList<>();
	}
}
