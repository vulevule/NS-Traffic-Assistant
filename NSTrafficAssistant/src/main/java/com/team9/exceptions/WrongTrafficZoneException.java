package com.team9.exceptions;

public class WrongTrafficZoneException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongTrafficZoneException() { super(); }
	public WrongTrafficZoneException(String message) { super(message); }
	public WrongTrafficZoneException(String message, Throwable cause) { super(message, cause); }
	public WrongTrafficZoneException(Throwable cause) { super(cause); }

}
