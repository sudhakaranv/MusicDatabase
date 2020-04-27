package com.lexisnexis.assessment.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lexisnexis.assessment.model.TrackList;
import com.lexisnexis.assessment.util.MusicDatabaseUtils;

/**
 * @author Sudhakaran Vasudevan
 * 
 *         This class is to make discogs api call and process the response
 *
 */
@Component
public class DiscogsApiClient {

	public static final String API_KEY = "lZRIWCmDrJtvAqjxzBJT";
	public static final String API_SECRET = "PZgIeCqtvqrsHNJZHEOZGJCKJmBjHChb";
	public static final String DISCOGS_DB_SEARCH_QUERY = "https://api.discogs.com/database/search?";

	/**
	 * @param queryString
	 * @param albumName
	 * @return TrackList
	 * 
	 *         This method is to make a call to discogs api and fetch tracklist for
	 *         given album and query
	 */
	public List<TrackList> getAlbumTrackListFromDiscogs(String queryString, String albumName) {

		RestTemplate restTemplate = new RestTemplate();

		String dicogsSearchURL = MusicDatabaseUtils.buildURL(DISCOGS_DB_SEARCH_QUERY, queryString, albumName, API_KEY,
				API_SECRET);

		String discogsResponse = restTemplate.getForObject(dicogsSearchURL, String.class);

		JsonObject discogsResponseObject = JsonParser.parseString(discogsResponse).getAsJsonObject();

		JsonArray discogsResponseArray = discogsResponseObject.getAsJsonArray("results");

		if (discogsResponseArray == null || discogsResponseArray.size() <= 0)
			return new ArrayList<TrackList>();

		String trackListURL = discogsResponseArray.get(0).getAsJsonObject().get("resource_url").getAsString();

		String trackListURLResponse = restTemplate.getForObject(trackListURL, String.class);
		JsonObject trackListResponseObject = JsonParser.parseString(trackListURLResponse).getAsJsonObject();

		JsonArray trackListArray = trackListResponseObject.get("tracklist").getAsJsonArray();

		return getTrackListFromJsonArray(trackListArray);

	}

	/**
	 * @param trackListArray
	 * @return Tracklist
	 * 
	 *         this method process discogs response JsonArray and fetch tracklist
	 */
	public List<TrackList> getTrackListFromJsonArray(JsonArray trackListArray) {
		List<TrackList> trackLists = new ArrayList<TrackList>();

		for (JsonElement element : trackListArray) {
			TrackList trackList = new TrackList(element.getAsJsonObject().get("position").getAsString(),
					element.getAsJsonObject().get("title").getAsString());

			trackLists.add(trackList);
		}

		return trackLists;
	}

}
