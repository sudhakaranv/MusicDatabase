package com.lexisnexis.assessment.service;

import java.util.List;

import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Artist;

public interface ArtistService {
	
	public static final String ARTIST_NOT_FOUND="No Artist exist for given id:";
	
	public List<Artist> getAllArtists(String name, String sortOrder, int pageNo, int pageSize);
	public Artist updateArtist(Long artistId,Artist artist) throws ResourceNotFoundException;
	public Artist addArtist(Artist artist);
}
