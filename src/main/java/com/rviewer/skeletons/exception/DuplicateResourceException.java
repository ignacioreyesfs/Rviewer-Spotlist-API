package com.rviewer.skeletons.exception;

public class DuplicateResourceException extends AppException {
	public DuplicateResourceException(APIError error) {
		super(error.getHttpStatus(), error.getMessage(), null);
	}
}
