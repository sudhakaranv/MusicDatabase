package com.lexisnexis.assessment.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lexisnexis.assessment.exception.ResourceNotFoundException;
import com.lexisnexis.assessment.model.Artist;
import com.lexisnexis.assessment.repository.ArtistRepository;
import com.lexisnexis.assessment.specification.ArtistNameSpecification;
import com.lexisnexis.assessment.util.MusicDatabaseUtils;

@Service
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistRepository artistRepository;

	@Override
	public List<Artist> getAllArtists(String name, String sortOrder, int pageNo, int pageSize) {

		Sort sort = Sort.unsorted();
		Order nameSortOrder=MusicDatabaseUtils.getOrder("name", sortOrder);

		if (!StringUtils.isEmpty(nameSortOrder)) {
				sort = Sort.by(nameSortOrder);	
		}

		Pageable paging = PageRequest.of(pageNo, pageSize, sort);
		Page<Artist> pagedResult = artistRepository.findAll(paging);

		if (!StringUtils.isEmpty(name)) {
			Specification<Artist> spec = Specification.where(new ArtistNameSpecification(name));
			pagedResult = artistRepository.findAll(spec, paging);
		}

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Artist>();
		}
	}

	@Override
	public Artist updateArtist(Long artistId, Artist newArtist) throws ResourceNotFoundException {

		return artistRepository.findById(artistId).map(artist -> {
			artist.setName(newArtist.getName());
			return artistRepository.save(artist);
		}).orElseThrow(() -> new ResourceNotFoundException(ARTIST_NOT_FOUND + artistId));

	}

	@Override
	public Artist addArtist(Artist artistEntity) {
		return artistRepository.save(artistEntity);
	}

}
