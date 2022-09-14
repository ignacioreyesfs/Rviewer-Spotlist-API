package com.rviewer.skeletons.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rviewer.skeletons.dao.UserRepository;
import com.rviewer.skeletons.service.user.UserRegisterDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void createUser() throws Exception {
		UserRegisterDTO userRegister = new UserRegisterDTO();
		userRegister.setName("user");
		userRegister.setPassword("Validpassword1");
		mockMvc
			.perform(post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsBytes(userRegister)))
			.andExpect(status().isCreated());
		assertEquals(userRepo.count(), 1, "User was not persisted");
		assertNotNull(userRepo.findByUsername("user"), "Cannot find saved user by username");
	}
	
	@Test
	public void createInvalidUserThrowsBadResponse() throws JsonProcessingException, Exception {
		UserRegisterDTO userRegister = new UserRegisterDTO();
		mockMvc
			.perform(post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsBytes(userRegister)))
			.andExpect(status().isBadRequest());
	}
	
}
