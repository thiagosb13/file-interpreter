package org.fileinterpreter.commons;

import java.util.Objects;

public class Strings {
	public static final String EMPTY = "";
	
	public static String truncate(CharSequence seq, int maxLength, String truncationIndicator) {
		Objects.requireNonNull(seq);

		int truncationLength = maxLength - truncationIndicator.length();

		if (seq.length() <= maxLength) {
			String string = seq.toString();
			if (string.length() <= maxLength) {
				return string;
			}
			seq = string;
		}

		return new StringBuilder(maxLength).append(seq, 0, truncationLength).append(truncationIndicator).toString();
	}

	public static String padLeft(String string, int minLength, char padChar) {
		Objects.requireNonNull(string);

		if (string.length() >= minLength) {
			return string;
		}
		StringBuilder sb = new StringBuilder(minLength);
		for (int i = string.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		sb.append(string);
		return sb.toString();
	}

	public static String padRight(String string, int minLength, char padChar) {
		Objects.requireNonNull(string);
		if (string.length() >= minLength) {
			return string;
		}
		StringBuilder sb = new StringBuilder(minLength);
		sb.append(string);
		for (int i = string.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		return sb.toString();
	}
	
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().equals(EMPTY);
	}
}
