package com.rviewer.skeletons.exception;

public class ResourceNotFoundException extends AppException{
	public ResourceNotFoundException(APIError error) {
		super(error.getHttpStatus(), error.getMessage(), null);
	}
}
