package com.course.online.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class PasswordIncorrectException extends RuntimeException {

	
	public PasswordIncorrectException(String message) {
		super(message);
	}



}
