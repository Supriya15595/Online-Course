package com.course.online.exception;

import java.time.LocalDate;

public class ErrorDetails {

	private LocalDate timeStamp;
	private String message;
	private String details;

	public ErrorDetails(LocalDate timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

}
