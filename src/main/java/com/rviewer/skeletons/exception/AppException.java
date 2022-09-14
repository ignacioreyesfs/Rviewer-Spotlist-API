package com.rviewer.skeletons.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AppException extends RuntimeException{
	private HttpStatus status;
	private String description;
	private List<String> reasons;
}
