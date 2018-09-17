package com.course.online.exception;

public class AuthenticationInvalidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationInvalidException(String message) {
		super(message);
	}

}
