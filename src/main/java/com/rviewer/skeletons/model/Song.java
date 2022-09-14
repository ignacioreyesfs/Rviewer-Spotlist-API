package com.rviewer.skeletons.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="song")
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore(value = true)
	private Long id;
	@NotEmpty
	private String artist;
	@NotEmpty
	private String title;
	
	public Song(String artist, String title) {
		this.artist = artist;
		this.title = title;
	}
}
