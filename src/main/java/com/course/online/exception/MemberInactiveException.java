package com.course.online.exception;

public class MemberInactiveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberInactiveException(String message) {
		super(message);
	}
	
}
