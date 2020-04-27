package com.lexisnexis.assessment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.lexisnexis.assessment.model.Artist;
import com.lexisnexis.assessment.service.ArtistService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ArtistService artistService;

	@Test
	public void getAllArtistsTest() throws Exception {

		List<Artist> artists = new ArrayList<>();

		Artist artist = new Artist();
		artist.setId(1L);
		artist.setName("Test Artist");

		artists.add(artist);

		Mockito.when(artistService.getAllArtists(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(artists);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/artists").accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("Test Artist")));

	}

	@Test
	public void addArtistTest() throws Exception {

		Artist artist = new Artist();
		artist.setId(3L);
		artist.setName("Test Artist3");

		Mockito.when(artistService.addArtist(Mockito.any(Artist.class))).thenReturn(artist);

		String reqBody = new ObjectMapper().writeValueAsString(artist);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/artists").contentType(MediaType.APPLICATION_JSON)
				.content(reqBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void updateArtistTest() throws Exception {

		Artist artist = new Artist();
		artist.setId(3L);
		artist.setName("Newtest Artist3");

		Mockito.when(artistService.updateArtist(Mockito.anyLong(), Mockito.any(Artist.class))).thenReturn(artist);

		String reqBody = new ObjectMapper().writeValueAsString(artist);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/artists/3").contentType(MediaType.APPLICATION_JSON)
				.content(reqBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		Artist artist2 = new Artist();
		artist2.setId(10L);
		artist2.setName("Newtest Artist10");

		String reqBody2 = new ObjectMapper().writeValueAsString(artist2);

		// Exception
		Mockito.when(artistService.updateArtist(Mockito.anyLong(), Mockito.any(Artist.class)))
				.thenThrow(new ResourceNotFoundException("artist not found"));

		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put("/artists/10")
				.contentType(MediaType.APPLICATION_JSON).content(reqBody2).accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder2).andExpect(status().isNotFound());

	}
}
