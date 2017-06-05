package com.falcon.cms.repository;

import com.falcon.cms.domain.Section;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Section entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionRepository extends JpaRepository<Section,Long> {

}
