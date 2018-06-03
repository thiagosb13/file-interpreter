package org.fileinterpreter.exception;

public class MisconfiguredDocumentException extends RuntimeException {

	private static final long serialVersionUID = 5299887745561084102L;

	public MisconfiguredDocumentException(String message) {
		super(message);
	}
}
