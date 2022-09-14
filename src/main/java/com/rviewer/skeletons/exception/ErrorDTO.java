package com.rviewer.skeletons.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
	private String description;
	private List<String> reasons;
}
