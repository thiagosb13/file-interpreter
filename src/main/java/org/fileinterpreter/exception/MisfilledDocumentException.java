package org.fileinterpreter.exception;

public class MisfilledDocumentException extends RuntimeException {

	private static final long serialVersionUID = -5540771336277578114L;

	public MisfilledDocumentException(String message) {
		super(message);
	}
}
