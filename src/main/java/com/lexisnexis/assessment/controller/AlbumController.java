package com.lexisnexis.assessment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Album;
import com.lexisnexis.assessment.service.AlbumService;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Rest controller to handle request for Album resources 
 *
 */
@RestController
@RequestMapping("/artists/{artistId}/albums")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	/**
	 * @param artistId
	 * @param albumNameSortOrder
	 * @param releaseYearSortOrder
	 * @return
	 * 
	 * Endpoint for retrieving all album resources for given artist id
	 */
	@GetMapping
	public ResponseEntity<List<Album>> getAllAlbumsByArtistId(@PathVariable Long artistId,
			@RequestParam(required = false) String albumNameSortOrder,
			@RequestParam(required = false) String releaseYearSortOrder) {
		List<Album> albums = albumService.getAllAlbumsbyArtistId(artistId, albumNameSortOrder, releaseYearSortOrder);

		return new ResponseEntity<List<Album>>(albums, new HttpHeaders(), HttpStatus.OK);

	}

	/**
	 * @param artistId
	 * @param album
	 * @return
	 * @throws ResourceNotFoundException
	 * 
	 * Endpoint for creating new album resource
	 */
	@PostMapping
	public ResponseEntity<Album> addAlbum(@PathVariable Long artistId, @Valid @RequestBody Album album)
			throws ResourceNotFoundException {

		return new ResponseEntity<Album>(albumService.addAlbum(artistId, album), new HttpHeaders(), HttpStatus.CREATED);
	}

	/**
	 * @param artistId
	 * @param albumId
	 * @param album
	 * @return
	 * @throws ResourceNotFoundException
	 * 
	 * Endpoint to update album for given artistId and albumId
	 */
	@PutMapping("{albumId}")
	public ResponseEntity<Album> updateAlbumByIdAndArtistId(@PathVariable(value = "artistId") Long artistId,
			@PathVariable(value = "albumId") Long albumId, @Valid @RequestBody Album album)
			throws ResourceNotFoundException {
		return new ResponseEntity<Album>(albumService.updateAlbumByIdAndArtistId(artistId, albumId, album),
				new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * @param artistId
	 * @param albumId
	 * @return
	 * 
	 * Endpoint to fetch album data for given artistId and albumId
	 */
	@GetMapping("{albumId}")
	public ResponseEntity<Album> getAlbumByIdAndArtistId(@PathVariable(value = "artistId") Long artistId,
			@PathVariable(value = "albumId") Long albumId) {
		return new ResponseEntity<Album>(albumService.getAlbumByIdAndArtistId(artistId, albumId), new HttpHeaders(),
				HttpStatus.OK);
	}

}
