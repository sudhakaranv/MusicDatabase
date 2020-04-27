package com.lexisnexis.assessment.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Entity for Genre data
 *
 */
@Entity
@Getter
@Setter
public class Genre {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@Column
	@NotBlank(message="Genre Name should not be empty")
	private String name;

	  @JsonIgnore
	  @ManyToMany(fetch = FetchType.LAZY, mappedBy =
	 "genres")	  
	  private Set<Album> albums = new HashSet<Album>();
	 

}
