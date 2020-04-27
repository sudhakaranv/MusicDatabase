package com.lexisnexis.assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Artist;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class) 
public class ArtistServiceTest {

	@Autowired
	private ArtistService artistService;

	List<String> artistList = new ArrayList<>();
	List<Artist> artistListFromService;
	
	

	@BeforeAll
	public void getInitialArtistData() {
		
		artistList.add("Michael Jackson");
		artistList.add("Justin Bieber");
		artistList.add("Elvis Presley");
			
		//Get all artists 
		artistListFromService=artistService.getAllArtists(null, null, 0, 10);
		
	}

	@Test
	@Order(1)
	public void getAllArtistsTest() {

		List<String> actualArtistList = artistListFromService.stream()
				.map(artist -> artist.getName()).collect(Collectors.toList());

		assertNotNull(artistListFromService);
		assertEquals(3, artistListFromService.size());
		assertEquals(artistList, actualArtistList);

		// Pagination
		//pagelist with pageNo-1, pageSize-2
		List<Artist> pagingList=artistService.getAllArtists(null, null, 1, 2);
		
		assertEquals(1, pagingList.size());

		// value based on index in diff pages;
		assertEquals("Elvis Presley", pagingList.get(0).getName());

		// Sorting
		List<Artist> sortList=artistService.getAllArtists(null, "asc", 0,10);
		
		assertEquals("Elvis Presley", sortList.get(0).getName());
		

		// Filtering
		List<Artist> filerList=artistService.getAllArtists("elv", null, 0, 10);
		assertEquals(1, filerList.size());
		assertEquals("Elvis Presley", filerList.get(0).getName());

	}

	
	@Test
	@Order(2)
	public void addArtistTest() {
		

		Artist artist = new Artist();
		
		//missing mandatory body attribute
		Throwable exception = assertThrows(ConstraintViolationException.class, () -> {artistService.addArtist(artist);});
	    assertEquals("name should not be empty", exception.getMessage());
	    
	    
		artist.setName("Test Artist");
		
		List<String> updatedArtistList=new ArrayList<String>();
		
		updatedArtistList.addAll(artistList);
		updatedArtistList.add("Test Artist");
		
		assertEquals("Test Artist",artistService.addArtist(artist).getName());
		assertEquals(4,artistService.getAllArtists(null, null, 0, 10).size());
		
		List<String> actualArtistList = artistService.getAllArtists(null, null, 0, 10).stream()
				.map(a -> a.getName()).collect(Collectors.toList());
		
		assertEquals(updatedArtistList, actualArtistList);
		
		
	
	}
	
	@Test
	@Order(3)
	public void updateArtistTest() {

		long id=3;
		
		Artist artist = new Artist();
		
		//missing mandatory body attribute
		Throwable exception = assertThrows(ConstraintViolationException.class, () -> {artistService.updateArtist(id,artist);});
	    assertEquals("name should not be empty", exception.getMessage());
		
		artist.setName("Newtest Artist");
		
		assertEquals("Newtest Artist", artistService.updateArtist(id, artist).getName());
		
		assertEquals("Newtest Artist",artistService.getAllArtists(null, null, 2, 1).get(0).getName());
		
		long wrongId=10;
		
		Throwable exception2 = assertThrows(ResourceNotFoundException.class, () -> {artistService.updateArtist(wrongId, artist);});
	    assertEquals("No Artist exist for given id:10", exception2.getMessage());
		
	}
	
	

}
