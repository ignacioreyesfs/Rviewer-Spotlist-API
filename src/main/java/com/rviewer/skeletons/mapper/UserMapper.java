package com.rviewer.skeletons.mapper;

import org.springframework.stereotype.Component;

import com.rviewer.skeletons.model.User;
import com.rviewer.skeletons.service.user.UserDTO;
import com.rviewer.skeletons.service.user.UserRegisterDTO;

@Component
public class UserMapper {
	public UserDTO toUserDTO(User user) {
		return new UserDTO(user.getId(), user.getUsername());
	}
	
	public User fromUserDTO(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getName());
		user.setId(userDTO.getId());
		return user;
	}
	
	public User fromUserRegisterDTO(UserRegisterDTO userRegisterDTO) {
		User user = new User();
		user.setUsername(userRegisterDTO.getName());
		user.setPassword(userRegisterDTO.getPassword());
		return user;
	}
}
