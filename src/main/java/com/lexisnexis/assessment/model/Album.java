package com.lexisnexis.assessment.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Entity for Album data
 *
 */
@Entity
@Getter
@Setter
public class Album {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "name should not be empty")
	private String name;
	@Min(message = "yearOfRelease should not be empty", value = 1)
	private int yearOfRelease;

	@ManyToOne
	@JoinColumn(name = "artist_id")
	@JsonIgnore
	private Artist artist;

	@ManyToMany(fetch = FetchType.LAZY,  cascade = {
            CascadeType.MERGE
        })

	@JoinTable(name = "AlbumGenreRelation", joinColumns = { @JoinColumn(name = "album_id") }, inverseJoinColumns = {
			@JoinColumn(name = "genre_id") })
	private Set<Genre> genres = new HashSet<Genre>();
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TrackList> trackList;

}
