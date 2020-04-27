package com.lexisnexis.assessment.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lexisnexis.assessment.model.Genre;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Repository to perform crud operation on genre table
 *
 */
@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
	
	public Optional<Genre> findByName(String name);

}
