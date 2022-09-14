package com.rviewer.skeletons.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rviewer.skeletons.model.SongList;

public interface SongListRepository extends JpaRepository<SongList, Long>{
	public Optional<SongList> findById(Long songListId);
}
