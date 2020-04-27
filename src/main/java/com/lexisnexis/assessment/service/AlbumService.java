package com.lexisnexis.assessment.service;

import java.util.List;

import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Album;


public interface AlbumService {
	
	public static final String ALBUM_NOT_FOUND_1="No Album found for given Artist Id:";
	public static final String ALBUM_NOT_FOUND_2=" and Album Id:";
	
	public List<Album> getAllAlbumsbyArtistId(Long artistId,String albumNameSortOrder,String releaseYearSortOrder);
	public Album updateAlbumByIdAndArtistId(Long artistId, Long albumId, Album album) throws ResourceNotFoundException;
	public Album addAlbum(Long artistId,Album album);
	public Album getAlbumByIdAndArtistId(Long artistId, Long albumId) throws ResourceNotFoundException;
}
