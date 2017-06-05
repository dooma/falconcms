package com.falcon.cms.service;

import com.falcon.cms.service.dto.SectionDTO;
import java.util.List;

/**
 * Service Interface for managing Section.
 */
public interface SectionService {

    /**
     * Save a section.
     *
     * @param sectionDTO the entity to save
     * @return the persisted entity
     */
    SectionDTO save(SectionDTO sectionDTO);

    /**
     *  Get all the sections.
     *
     *  @return the list of entities
     */
    List<SectionDTO> findAll();

    /**
     *  Get the "id" section.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SectionDTO findOne(Long id);

    /**
     *  Delete the "id" section.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
