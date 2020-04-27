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
import com.lexisnexis.assessment.model.Artist;
import com.lexisnexis.assessment.service.ArtistService;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Rest controller to handle request for artist resources
 *
 */
@RestController
@RequestMapping("/artists")
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	/**
	 * @param name
	 * @param sortOrder
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * 
	 * Endpoint for retrieving all artist resources with pagination and sorting
	 */
	@GetMapping
	public ResponseEntity<List<Artist>> getAllArtists(@RequestParam(required = false) String name,
			@RequestParam(required = false) String sortOrder, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {

		List<Artist> list = artistService.getAllArtists(name, sortOrder, pageNo, pageSize);

		return new ResponseEntity<List<Artist>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * @param artist
	 * @return
	 * @throws ResourceNotFoundException
	 * 
	 * Endpoint to create artist resource
	 */
	@PostMapping
	public ResponseEntity<Artist> addArtist(@Valid @RequestBody Artist artist) throws ResourceNotFoundException {

		return new ResponseEntity<Artist>(artistService.addArtist(artist), new HttpHeaders(), HttpStatus.CREATED);
	}

	/**
	 * @param artistId
	 * @param artist
	 * @return
	 * @throws ResourceNotFoundException
	 * 
	 * Endpoint to update Artist resource
	 */
	@PutMapping("{artistId}")
	public ResponseEntity<Artist> updateArtist(@PathVariable(value = "artistId") Long artistId,
			@Valid @RequestBody Artist artist) throws ResourceNotFoundException {

		return new ResponseEntity<Artist>(artistService.updateArtist(artistId, artist), new HttpHeaders(),
				HttpStatus.OK);
	}

}
