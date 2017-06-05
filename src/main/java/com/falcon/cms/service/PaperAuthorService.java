package com.falcon.cms.service;

import com.falcon.cms.service.dto.PaperAuthorDTO;
import java.util.List;

/**
 * Service Interface for managing PaperAuthor.
 */
public interface PaperAuthorService {

    /**
     * Save a paperAuthor.
     *
     * @param paperAuthorDTO the entity to save
     * @return the persisted entity
     */
    PaperAuthorDTO save(PaperAuthorDTO paperAuthorDTO);

    /**
     *  Get all the paperAuthors.
     *
     *  @return the list of entities
     */
    List<PaperAuthorDTO> findAll();

    /**
     *  Get the "id" paperAuthor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaperAuthorDTO findOne(Long id);

    /**
     *  Delete the "id" paperAuthor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
