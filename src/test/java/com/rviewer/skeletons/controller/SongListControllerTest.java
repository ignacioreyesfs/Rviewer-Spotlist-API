package com.rviewer.skeletons.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rviewer.skeletons.dao.SongListRepository;
import com.rviewer.skeletons.dao.UserRepository;
import com.rviewer.skeletons.mapper.SongListMapper;
import com.rviewer.skeletons.model.Role;
import com.rviewer.skeletons.model.Song;
import com.rviewer.skeletons.model.SongList;
import com.rviewer.skeletons.model.User;
import com.rviewer.skeletons.service.songlist.SongListDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class SongListControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private SongListRepository songListRepo;
	@Autowired
	private SongListMapper songListMapper;
	private User user;
	private SongListDTO songList;
	private ObjectMapper objectMapper;
	@Autowired
	private PasswordEncoder encoder;
	
	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
		user = createUser("test", "test");
		songList = new SongListDTO();
		Song song = new Song("test", "test");
		List<Song> songs = new ArrayList<>();
		songs.add(song);
		songList.setSongs(songs);
		songList.setName("name");
	}
	
	@Test
	public void createSongListForAnUser() throws JsonProcessingException, Exception {
		user = userRepo.save(user);
		
		MvcResult result = mockMvc.perform(post("/users/{userId}/lists", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(songList))
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("test", "test")))
				.andExpect(status().isCreated()).andReturn();
		SongList content = objectMapper.readValue(result.getResponse().getContentAsString(), SongList.class);	
		
		assertEquals(1, content.getSongs().size());
		assertNotNull(content.getId());
		assertEquals(1, songListRepo.count(), "Song list not saved");
		SongList saved = songListRepo.findAll().get(0);
		assertNotNull(userRepo.findBySongListsContaining(saved));
	}
	
	@Test
	public void createListSongForOtherUserUnauthorized() throws JsonProcessingException, Exception {
		user = userRepo.save(user);
		User otherUser = createUser("fail", "fail");
		otherUser = userRepo.save(otherUser);
		
		mockMvc.perform(post("/users/{userId}/lists", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(songList))
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("fail", "fail")))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void getAllSongListOfUser() throws Exception {
		user.addSongList(songListMapper.fromSongListDTO(songList));
		user.addSongList(new SongList());
		user = userRepo.save(user);
		MvcResult result = mockMvc.perform(get("/users/{userId}/lists", user.getId()))
				.andExpect(status().isOk())
				.andReturn();
		List<SongListDTO> content = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
		assertEquals(2, content.size());
	}
	
	@Test
	public void getSpecificSongListOfUser() throws Exception {
		user = userRepo.save(user);
		SongList playlist = songListMapper.fromSongListDTO(songList);
		playlist.setUser(user);
		playlist = songListRepo.save(playlist);
		
		MvcResult result = mockMvc.perform(get("/users/{userId}/lists/{listId}", user.getId(),
					playlist.getId()))
				.andExpect(status().isOk())
				.andReturn();
		SongListDTO content = objectMapper.readValue(result.getResponse().getContentAsString(), SongListDTO.class);
		assertEquals(1, content.getSongs().size());
	}
	
	@Test
	public void addSongToList() throws Exception {
		user = userRepo.save(user);
		SongList playlist = songListMapper.fromSongListDTO(songList);
		playlist.setUser(user);
		playlist = songListRepo.save(playlist);
		Song dillom = new Song("Dillom", "220");
		int initSongs = playlist.getSongs().size();
		
		mockMvc.perform(post("/users/{userId}/lists/{listId}/songs", user.getId(),
					playlist.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dillom))
					.with(SecurityMockMvcRequestPostProcessors.httpBasic("test", "test")))
				.andExpect(status().isCreated());
		
		assertEquals(initSongs+1,
				songListRepo.findById(playlist.getId()).get().getSongs().size());
	}
	
	@Test
	public void addSongToOtherUserListUnauthorized() throws JsonProcessingException, Exception {
		user = userRepo.save(user);
		SongList playlist = new SongList();
		playlist.setName("test");
		playlist.setUser(user);
		playlist = songListRepo.save(playlist);
		Song newSong = new Song("test", "test");
		User otherUser = createUser("fail", "fail");
		otherUser = userRepo.save(otherUser);
		
		
		mockMvc.perform(post("/users/{userId}/lists/{listId}/songs", user.getId(),
				playlist.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSong))
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("fail", "fail")))
			.andExpect(status().isUnauthorized());
	}
	
	private User createUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		Role role = new Role("ROLE_USER");
		user.setRoles(Arrays.asList(role));
		return user;
	}
	
}
