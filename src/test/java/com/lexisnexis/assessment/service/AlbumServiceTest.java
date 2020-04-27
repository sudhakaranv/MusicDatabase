
package com.lexisnexis.assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
import com.lexisnexis.assessment.model.Album;
import com.lexisnexis.assessment.model.Artist;
import com.lexisnexis.assessment.model.Genre;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
public class AlbumServiceTest {

	@Autowired
	private AlbumService albumService;

	@Test
	@Order(1)
	public void getAllAlbumsbyArtistIdTest() {

		List<Album> albumList = albumService.getAllAlbumsbyArtistId(2L, null, null);

		Album album = albumList.get(0);
		Artist artist = album.getArtist();

		assertEquals(1, albumList.size());
		assertEquals("My World", album.getName());
		assertEquals(2009, album.getYearOfRelease());
		assertEquals(2, artist.getId());
		assertEquals("Justin Bieber", artist.getName());

	}

	@Test
	@Order(2)
	public void getAlbumByIdAndArtistIdTest() {

		Album album = albumService.getAlbumByIdAndArtistId(1L,1L);
		Artist artist = album.getArtist();

		assertEquals("Immortal", album.getName());
		assertEquals(1997, album.getYearOfRelease());
		assertEquals(1, artist.getId());
		assertEquals("Michael Jackson", artist.getName());

		Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
			albumService.getAlbumByIdAndArtistId(1L, 10L);
		});
		assertEquals("No Album found for given Artist Id:1 and Album Id:10", exception.getMessage());

	}

	@Test
	@Order(3)
	public void addAlbumTest() {

		Album album = new Album();
		album.setName("Test Album");

		Set<Genre> genres = new HashSet<>();
		Genre genre = new Genre();

		genre.setName("Test Genre");

		genres.add(genre);

		album.setGenres(genres);

		// positive case

		album.setYearOfRelease(2001);

		Album newAlbum = albumService.addAlbum(1L, album);

		assertNotNull(newAlbum);
		assertEquals(5, newAlbum.getId());
		assertEquals(newAlbum, albumService.getAlbumByIdAndArtistId(1L,5L));

		Album album2 = new Album();
		// exceptions---missing one of mandatory attribute

		// arguments check
		Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
			albumService.addAlbum(1L, album2);
		});

		boolean isExpectedException = exception.getMessage().contains("name should not be empty");

		assertTrue(isExpectedException);

		Throwable exception2 = assertThrows(ResourceNotFoundException.class, () -> {
			albumService.addAlbum(100L, album);
		});
		assertEquals("No Artist exist for given id:100", exception2.getMessage());

	}

	@Test
	@Order(4)
	public void updateAlbumByIdAndArtistIdTest() {

		Album album = new Album();

		long albumId = 1, artistId = 1;

		album.setName("Update Album");
		album.setYearOfRelease(1995);

		Set<Genre> genres = new HashSet<>();
		Genre genre = new Genre();

		genre.setName("New Genre");

		genres.add(genre);

		album.setGenres(genres);

		Album updatedAlbum = albumService.updateAlbumByIdAndArtistId(artistId, albumId, album);

		assertEquals(updatedAlbum, albumService.getAlbumByIdAndArtistId(albumId, albumId));

		Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
			albumService.updateAlbumByIdAndArtistId(1L, 10L, album);
		});
		assertEquals("No Album found for given Artist Id:1 and Album Id:10", exception.getMessage());

		Album album2 = new Album();

		// arguments check
		Throwable exception2 = assertThrows(ConstraintViolationException.class, () -> {
			albumService.addAlbum(1L, album2);
		});

		boolean isExpectedException = exception2.getMessage().contains("yearOfRelease should not be empty");

		assertTrue(isExpectedException);

	}

}
