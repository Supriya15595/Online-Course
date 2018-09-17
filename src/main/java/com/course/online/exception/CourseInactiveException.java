package com.course.online.exception;

public class CourseInactiveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CourseInactiveException(String message) {
		super(message);
	}
	
}
