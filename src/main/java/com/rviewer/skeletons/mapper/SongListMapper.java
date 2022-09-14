package com.rviewer.skeletons.mapper;

import org.springframework.stereotype.Component;

import com.rviewer.skeletons.model.SongList;
import com.rviewer.skeletons.service.songlist.SongListDTO;

@Component
public class SongListMapper {
	public SongListDTO toSongListDTO(SongList songList) {
		return new SongListDTO(songList.getId(), songList.getName(),songList.getSongs());
	}
	
	public SongList fromSongListDTO(SongListDTO songListDTO) {
		SongList songList = new SongList();
		songList.setId(songListDTO.getId());
		songList.setSongs(songListDTO.getSongs());
		songList.setName(songListDTO.getName());
		return songList;
	}
}
