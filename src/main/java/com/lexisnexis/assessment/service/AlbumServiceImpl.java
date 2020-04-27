package com.lexisnexis.assessment.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lexisnexis.assessment.client.DiscogsApiClient;
import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Album;
import com.lexisnexis.assessment.model.Genre;
import com.lexisnexis.assessment.repository.AlbumRepository;
import com.lexisnexis.assessment.repository.ArtistRepository;
import com.lexisnexis.assessment.repository.GenreRepository;
import com.lexisnexis.assessment.util.MusicDatabaseUtils;

import static com.lexisnexis.assessment.service.ArtistService.ARTIST_NOT_FOUND;;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private DiscogsApiClient client;

	@Override
	public List<Album> getAllAlbumsbyArtistId(Long artistId, String albumNameSortOrder, String releaseYearSortOrder) {
		
		artistRepository.findById(artistId).orElseThrow(() -> new ResourceNotFoundException(ARTIST_NOT_FOUND+artistId));

		Sort sort = Sort.unsorted();
		Order albumNameOrder = MusicDatabaseUtils.getOrder("name", albumNameSortOrder);
		Order releaseYearOrder = MusicDatabaseUtils.getOrder("yearOfRelease", releaseYearSortOrder);

		if (!StringUtils.isEmpty(albumNameOrder) && !StringUtils.isEmpty(releaseYearOrder)) {
			sort = Sort.by(releaseYearOrder,albumNameOrder);
		} else {
			if (!StringUtils.isEmpty(albumNameOrder)) {
				sort = Sort.by(albumNameOrder);
			} else if (!StringUtils.isEmpty(releaseYearOrder)) {
				sort = Sort.by(releaseYearOrder);
			}
		}

		return albumRepository.findByArtistId(sort, artistId);
	}

	@Override
	public Album updateAlbumByIdAndArtistId(Long artistId, Long albumId, Album album) throws ResourceNotFoundException {

		return albumRepository.findByIdAndArtistId(albumId, artistId).map(newAlbum -> {

			Set<Genre> newGenres = getUpdatedGenreSet(album);

			genreRepository.saveAll(newGenres);

			newAlbum.setGenres(newGenres);
			newAlbum.setName(album.getName());
			newAlbum.setYearOfRelease(album.getYearOfRelease());

			return albumRepository.save(newAlbum);

		}).orElseThrow(() -> new ResourceNotFoundException(ALBUM_NOT_FOUND_1 + artistId + ALBUM_NOT_FOUND_2 + albumId));
	}

	@Override
	public Album addAlbum(Long artistId, Album album) {

		return artistRepository.findById(artistId).map(artist -> {

			Set<Genre> newGenres = getUpdatedGenreSet(album);

			genreRepository.saveAll(newGenres);

			album.setArtist(artist);
			album.setGenres(newGenres);

			return albumRepository.save(album);
			
		}).orElseThrow(() -> new ResourceNotFoundException(ARTIST_NOT_FOUND + artistId));
	}

	@Override
	public Album getAlbumByIdAndArtistId(Long artistId, Long albumId) throws ResourceNotFoundException {

		return albumRepository.findByIdAndArtistId(albumId, artistId).map(album -> {

			String artistName = artistRepository.findById(artistId).get().getName();
			String albumName = album.getName();

			String searchQuery = MusicDatabaseUtils.getQueryString(artistName, albumName);

			album.setTrackList(client.getAlbumTrackListFromDiscogs(searchQuery, albumName));

			return album;

		}).orElseThrow(() -> new ResourceNotFoundException(ALBUM_NOT_FOUND_1 + artistId + ALBUM_NOT_FOUND_2 + albumId));

	}

	public Set<Genre> getUpdatedGenreSet(Album album) {

		Set<Genre> newGenres = new HashSet<>();

		for (Genre genre : album.getGenres()) {
			String genreName = genre.getName();
			Optional<Genre> genre_new = genreRepository.findByName(genreName);
			Set<Album> albumSetForGenre = null;

			if (genre_new.isPresent()) {
				Genre oldGenre = genre_new.get();
				albumSetForGenre = oldGenre.getAlbums();
				albumSetForGenre.add(album);
				genre.setId(oldGenre.getId());
			} else {
				albumSetForGenre = new HashSet<>();
				albumSetForGenre.add(album);
			}
			genre.setAlbums(albumSetForGenre);
			newGenres.add(genre);
		}
		
		return newGenres;
	}
}
