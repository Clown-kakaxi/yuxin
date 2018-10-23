package com.ytec.mdm.base.exception;

public class RequestIOException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequestIOException() {
		super();
	}

	public RequestIOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RequestIOException(final String message) {
		super(message);
	}

	public RequestIOException(final Throwable cause) {
		super(cause);
	}
}
