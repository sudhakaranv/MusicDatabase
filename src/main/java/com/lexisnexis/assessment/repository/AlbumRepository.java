package com.lexisnexis.assessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lexisnexis.assessment.model.Album;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Repository to perform crud operations on album table
 *
 */
@Repository
public interface AlbumRepository extends PagingAndSortingRepository<Album,Long> {

	List<Album> findByArtistId(Sort sort,Long artistId);
	Optional<Album> findByIdAndArtistId(Long id, Long artistId);
}
