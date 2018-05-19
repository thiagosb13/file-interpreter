package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.annotation.PositionalField;

public class PositionalLineSample {
	@PositionalField(name = "User ID", startIndex = 1, size = 20)
	public String userID;
	@PositionalField(name = "User Name", startIndex = 21, size = 30, defaultValue = "NC")
	public String name;
}