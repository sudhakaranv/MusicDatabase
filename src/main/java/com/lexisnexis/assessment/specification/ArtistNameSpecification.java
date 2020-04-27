package com.lexisnexis.assessment.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.lexisnexis.assessment.model.Artist;

import lombok.AllArgsConstructor;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Specification for filtering Artist table based on name
 *
 */
@AllArgsConstructor
public class ArtistNameSpecification implements Specification<Artist> {

	private static final long serialVersionUID = -5874763601656806754L;

	private String name;

	@Override
	public Predicate toPredicate(Root<Artist> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub

		if (StringUtils.isEmpty(name)) {
			return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
		}

		return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + this.name.toLowerCase() + "%");

	}

}
