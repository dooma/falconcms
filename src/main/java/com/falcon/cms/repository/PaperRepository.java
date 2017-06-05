package com.falcon.cms.repository;

import com.falcon.cms.domain.Paper;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Paper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaperRepository extends JpaRepository<Paper,Long> {

}
