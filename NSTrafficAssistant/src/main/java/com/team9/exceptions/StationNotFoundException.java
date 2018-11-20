package com.team9.exceptions;

public class StationNotFoundException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StationNotFoundException() { super(); }
	public StationNotFoundException(String message) { super(message); }
	public StationNotFoundException(String message, Throwable cause) { super(message, cause); }
	public StationNotFoundException(Throwable cause) { super(cause); }
}
