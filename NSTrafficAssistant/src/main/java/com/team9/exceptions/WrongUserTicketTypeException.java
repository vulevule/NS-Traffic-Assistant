package com.team9.exceptions;

public class WrongUserTicketTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongUserTicketTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WrongUserTicketTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public WrongUserTicketTypeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongUserTicketTypeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongUserTicketTypeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
