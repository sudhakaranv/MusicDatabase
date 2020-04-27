package com.lexisnexis.assessment.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lexisnexis.assessment.model.TrackList;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class DiscogsApiClientTest {

	@Autowired
	private DiscogsApiClient client;
	
	@Test
	public void getAlbumTrackListFromDiscogsTest()
	{
		List<TrackList> trackList=client.
				getAlbumTrackListFromDiscogs("Michael-Jackson-Ben", "Ben");
		
		assertTrue(trackList.parallelStream().anyMatch(t -> t.getTitle().
				equalsIgnoreCase("My Girl")));
		
		assertEquals(10, trackList.size());
	}

}
