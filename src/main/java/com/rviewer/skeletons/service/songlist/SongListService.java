package com.rviewer.skeletons.service.songlist;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rviewer.skeletons.dao.SongListRepository;
import com.rviewer.skeletons.dao.UserRepository;
import com.rviewer.skeletons.exception.APIError;
import com.rviewer.skeletons.exception.ResourceNotFoundException;
import com.rviewer.skeletons.mapper.SongListMapper;
import com.rviewer.skeletons.model.Song;
import com.rviewer.skeletons.model.SongList;
import com.rviewer.skeletons.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongListService {
	private SongListRepository songListRepo;
	private UserRepository userRepo;
	private SongListMapper songListMapper;
	
	@Transactional
	public SongListDTO save(Long userId, SongListDTO songListDTO) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(APIError.USER_NOT_FOUND));
		SongList songList = songListMapper.fromSongListDTO(songListDTO);
		songList.setUser(user);
		
		return songListMapper.toSongListDTO(songListRepo.save(songList));
	}

	public List<SongListDTO> retrieveAll(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(APIError.USER_NOT_FOUND));
		return user.getSongLists().stream().map(songListMapper::toSongListDTO).toList();
	}

	public SongListDTO retrieve(Long userId, Long listId) {
		return songListMapper.toSongListDTO(findSongList(userId, listId));
	}

	public Song saveSong(Long userId, Long listId, Song song) {
		SongList songList = findSongList(userId, listId);
		
		songList.addSong(song);
		songListRepo.save(songList);
		
		return song;
	}
	
	private SongList findSongList(Long userId, Long listId) {
		SongList songList = songListRepo.findById(listId)
				.orElseThrow(() -> new ResourceNotFoundException(APIError.SONGLIST_NOT_FOUND));
		if(!songList.getUser().getId().equals(userId)) {
			throw new ResourceNotFoundException(APIError.SONGLIST_NOT_FOUND);
		}
		
		return songList;
	}
}
