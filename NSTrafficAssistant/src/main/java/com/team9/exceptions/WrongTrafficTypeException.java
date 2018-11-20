package com.team9.exceptions;

public class WrongTrafficTypeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongTrafficTypeException() { super(); }
	public WrongTrafficTypeException(String message) { super(message); }
	public WrongTrafficTypeException(String message, Throwable cause) { super(message, cause); }
	public WrongTrafficTypeException(Throwable cause) { super(cause); }
}
