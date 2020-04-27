package com.lexisnexis.assessment.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lexisnexis.assessment.model.Artist;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Repository to perform crud operations on artist table
 *
 */
@Repository
public interface ArtistRepository extends PagingAndSortingRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {

}
