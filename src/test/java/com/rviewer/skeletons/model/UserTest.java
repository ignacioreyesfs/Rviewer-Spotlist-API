package com.rviewer.skeletons.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UserTest {
	@Test
	public void addSongsToUserPlaylist() {
		User user = new User();
		SongList songList = new SongList();
		Song backInBlack = new Song("ACDC", "Back in Black");
		Song kashmir = new Song("Led Zepelling", "Kashmir");
		songList.addSong(backInBlack);
		songList.addSong(kashmir);
		user.addSongList(songList);
		assertThat(user.getSongLists().get(0).getSongs().size()).isEqualTo(2);
	}
}
