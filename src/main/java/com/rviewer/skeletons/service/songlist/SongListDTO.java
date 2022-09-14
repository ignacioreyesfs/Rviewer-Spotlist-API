package com.rviewer.skeletons.service.songlist;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rviewer.skeletons.model.Song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongListDTO {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	@NotEmpty
	private String name;
	private List<Song> songs;
}
