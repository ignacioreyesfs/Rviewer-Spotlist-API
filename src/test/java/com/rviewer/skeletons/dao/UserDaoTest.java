package com.rviewer.skeletons.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rviewer.skeletons.model.Song;
import com.rviewer.skeletons.model.SongList;
import com.rviewer.skeletons.model.User;

@DataJpaTest
@ActiveProfiles("test")
public class UserDaoTest {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private SongListRepository songListRepo;
	
	@Test
	public void persistAnUser() {
		User user = new User();
		user.setUsername("test");
		user.setPassword("test");
		user = userRepo.save(user);
		assertThat(userRepo.count()).isEqualTo(1);
	}
	
	@Test
	public void persistAnUserWithSongList() {
		Song song = new Song("Dillom", "220");
		SongList songList = new SongList();
		songList.addSong(song);
		User user = new User();
		user.setUsername("test");
		user.setPassword("test");
		user.addSongList(songList);
		userRepo.save(user);
		assertThat(songListRepo.count()).isEqualTo(1);
	}

}
