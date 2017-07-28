package org.vino.restful.Messenger.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8214645939471400557L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
