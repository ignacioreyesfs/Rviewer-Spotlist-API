package com.rviewer.skeletons.controller.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rviewer.skeletons.service.user.UserDTO;
import com.rviewer.skeletons.service.user.UserRegisterDTO;
import com.rviewer.skeletons.service.user.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	private UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
		UserDTO saved = userService.save(userRegisterDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
}
