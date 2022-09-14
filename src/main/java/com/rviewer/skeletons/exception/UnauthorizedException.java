package com.rviewer.skeletons.exception;

public class UnauthorizedException extends AppException{
	public UnauthorizedException(APIError error) {
		super(error.getHttpStatus(), error.getMessage(), null);
	}
}
