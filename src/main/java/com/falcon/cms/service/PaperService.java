package com.falcon.cms.service;

import com.falcon.cms.service.dto.PaperDTO;
import java.util.List;

/**
 * Service Interface for managing Paper.
 */
public interface PaperService {

    /**
     * Save a paper.
     *
     * @param paperDTO the entity to save
     * @return the persisted entity
     */
    PaperDTO save(PaperDTO paperDTO);

    /**
     *  Get all the papers.
     *
     *  @return the list of entities
     */
    List<PaperDTO> findAll();

    /**
     *  Get the "id" paper.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaperDTO findOne(Long id);

    /**
     *  Delete the "id" paper.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
