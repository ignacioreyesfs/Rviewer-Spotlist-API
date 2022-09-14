package com.rviewer.skeletons.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rviewer.skeletons.model.SongList;
import com.rviewer.skeletons.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUsername(String username);
	public User findBySongListsContaining(SongList songList);
}
