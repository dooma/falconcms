package com.falcon.cms.repository;

import com.falcon.cms.domain.PaperAuthor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaperAuthor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaperAuthorRepository extends JpaRepository<PaperAuthor,Long> {

}
