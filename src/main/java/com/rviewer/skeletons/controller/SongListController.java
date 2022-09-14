package com.rviewer.skeletons.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rviewer.skeletons.exception.APIError;
import com.rviewer.skeletons.exception.UnauthorizedException;
import com.rviewer.skeletons.model.Song;
import com.rviewer.skeletons.model.User;
import com.rviewer.skeletons.service.songlist.SongListDTO;
import com.rviewer.skeletons.service.songlist.SongListService;
import com.rviewer.skeletons.service.user.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SongListController {
	private SongListService songListService;
	private UserService userService;
	
	@PostMapping("/users/{userId}/lists")
	public ResponseEntity<SongListDTO> createSongList(@PathVariable Long userId, 
			@Valid @RequestBody SongListDTO songList, Principal principal){
		User user = userService.findById(userId);
		
		if(!user.getUsername().equals(principal.getName())) {
			throw new UnauthorizedException(APIError.OTHER_USER_MODIFICATION);
		}
		
		SongListDTO saved = songListService.save(userId, songList);
		
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{userId}/lists")
	public List<SongListDTO> retrieveAll(@PathVariable Long userId){
		return songListService.retrieveAll(userId);
	}
	
	@GetMapping("/users/{userId}/lists/{listId}")
	public SongListDTO retrieve(@PathVariable Long userId, @PathVariable Long listId){
		return songListService.retrieve(userId, listId);
	}
	
	@PostMapping("/users/{userId}/lists/{listId}/songs")
	public ResponseEntity<Song> addSong(@PathVariable Long userId, @PathVariable Long listId,
			@RequestBody Song song, Principal principal){
		User user = userService.findById(userId);
		
		if(!user.getUsername().equals(principal.getName())) {
			throw new UnauthorizedException(APIError.OTHER_USER_MODIFICATION);
		}
		
		songListService.saveSong(userId, listId, song);
		return new ResponseEntity<>(song, HttpStatus.CREATED);
	}
}
