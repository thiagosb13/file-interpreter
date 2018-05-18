package org.fileinterpreter.core;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fileinterpreter.parser.LineParser;

public class DocumentParser_OLD {
	private static final String BREAK_LINE = "\r\n";
    private String lineDelimiter;
	protected List<LineParser> lines;

	public void parse(String content) {
		String[] linesText = content.split(getLineDelimiter());
		int linesContentSize = linesText.length;

		List<LineParser> lineList = getLines().collect(toList());
		
		for (int i = 0; i < lineList.size(); i++) {
		    String contentLine = i < linesContentSize ? linesText[i] : null;
            //lineList.get(i).parse(contentLine);
		}
	}

	public String toContent() {
	    return ""; //lines.stream()
//                    .map(LineParser::toContent)
//                    .collect(Collectors.joining(getLineDelimiter()));
	}
	
	public String toContent(List<Object> lines) {
	    return "";
	}

	private String getLineDelimiter() {
		return Optional.ofNullable(lineDelimiter).orElse(BREAK_LINE);
	}

	public void setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
	}

	public Stream<LineParser> getLines() {
        return Optional.ofNullable(lines).map(List::stream).orElseGet(Stream::empty);
	}
}
