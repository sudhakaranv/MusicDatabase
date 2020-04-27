package com.lexisnexis.assessment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Album;
import com.lexisnexis.assessment.model.Genre;
import com.lexisnexis.assessment.service.AlbumService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AlbumService albumService;

	@Test
	public void getAllAlbumsByArtistIdTest() throws Exception {

		List<Album> albums = new ArrayList<>();

		Album album = new Album();

		album.setId(1L);
		album.setName("New Album");
		album.setYearOfRelease(2000);

		Set<Genre> genres = new HashSet<>();
		Genre genre = new Genre();

		genre.setName("New Genre");

		genres.add(genre);

		album.setGenres(genres);

		albums.add(album);

		Mockito.when(albumService.getAllAlbumsbyArtistId(Mockito.anyLong(), Mockito.isNull(), Mockito.isNull()))
				.thenReturn(albums);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/artists/1/albums")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("New Album"))).andExpect(jsonPath("$[0].yearOfRelease", is(2000)));

		// Exception

		Mockito.when(albumService.getAllAlbumsbyArtistId(Mockito.anyLong(), Mockito.isNull(), Mockito.isNull()))
				.thenThrow(new ResourceNotFoundException("artist not found"));

		mvc.perform(requestBuilder).andExpect(status().isNotFound());
	}

	@Test
	public void addAlbumTest() throws Exception {

		Album album = new Album();

		album.setId(2L);
		album.setName("New Album2");
		album.setYearOfRelease(2000);

		Set<Genre> genres = new HashSet<>();
		Genre genre = new Genre();

		genre.setName("New Genre2");

		genres.add(genre);

		album.setGenres(genres);

		Mockito.when(albumService.addAlbum(Mockito.anyLong(), Mockito.any(Album.class))).thenReturn(album);

		String reqBody = new ObjectMapper().writeValueAsString(album);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/artists/1/albums")
				.contentType(MediaType.APPLICATION_JSON).content(reqBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isCreated());

		// Exception

		Mockito.when(albumService.addAlbum(Mockito.anyLong(), Mockito.any(Album.class)))
				.thenThrow(new ResourceNotFoundException("no album found for given artist and album id"));

		mvc.perform(requestBuilder).andExpect(status().isNotFound());

	}

	@Test
	public void updateAlbumByIdAndArtistIdTest() throws Exception {

		Album album = new Album();

		album.setId(3L);
		album.setName("New Album3");
		album.setYearOfRelease(2000);

		Set<Genre> genres = new HashSet<>();
		Genre genre = new Genre();

		genre.setName("New Genre3");

		genres.add(genre);

		album.setGenres(genres);

		Mockito.when(
				albumService.updateAlbumByIdAndArtistId(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Album.class)))
				.thenReturn(album);

		String reqBody = new ObjectMapper().writeValueAsString(album);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/artists/1/albums/1")
				.contentType(MediaType.APPLICATION_JSON).content(reqBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	public void getAlbumByIdAndArtistIdTest() throws Exception {

		Album album = new Album();

		album.setId(4L);
		album.setName("New Album4");
		album.setYearOfRelease(2000);

		Set<Genre> genres = new HashSet<>();
		Genre genre = new Genre();

		genre.setName("New Genre4");

		genres.add(genre);

		album.setGenres(genres);

		Mockito.when(albumService.getAlbumByIdAndArtistId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(album);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/artists/1/albums/1")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(4)))
				.andExpect(jsonPath("$.name", is("New Album4"))).andExpect(jsonPath("$.yearOfRelease", is(2000)));

	}
}
