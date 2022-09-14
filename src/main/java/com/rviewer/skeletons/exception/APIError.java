package com.rviewer.skeletons.exception;

import org.springframework.http.HttpStatus;

public enum APIError {
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "There are attributes with wrong values"),
	BAD_FORMAT(HttpStatus.BAD_REQUEST, "The message not have a correct format"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
	USER_WITH_SAME_NAME(HttpStatus.BAD_REQUEST, "User name already exists"),
	SONGLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Song list not found"),
	OTHER_USER_MODIFICATION(HttpStatus.UNAUTHORIZED, "Modification of other user song list is not allowed");
	
	private final HttpStatus httpStatus;
	private final String message;
	
	APIError(HttpStatus httpStatus, String message){
		this.httpStatus = httpStatus;
		this.message = message;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public String getMessage() {
		return message;
	}
}
