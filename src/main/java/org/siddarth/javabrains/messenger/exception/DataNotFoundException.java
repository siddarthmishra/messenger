package org.siddarth.javabrains.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1895605098221042105L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
