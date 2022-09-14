package com.rviewer.skeletons.service.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
	@NotEmpty
	private String name;
	@NotBlank(message="required")
	@Size(min = 8, max = 16, message="length between 8-16")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$", 
		message="At least one uppercase letter and one number")
	private String password;
}
