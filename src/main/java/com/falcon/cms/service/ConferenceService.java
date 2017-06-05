package com.falcon.cms.service;

import com.falcon.cms.service.dto.ConferenceDTO;
import java.util.List;

/**
 * Service Interface for managing Conference.
 */
public interface ConferenceService {

    /**
     * Save a conference.
     *
     * @param conferenceDTO the entity to save
     * @return the persisted entity
     */
    ConferenceDTO save(ConferenceDTO conferenceDTO);

    /**
     *  Get all the conferences.
     *
     *  @return the list of entities
     */
    List<ConferenceDTO> findAll();

    /**
     *  Get the "id" conference.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConferenceDTO findOne(Long id);

    /**
     *  Delete the "id" conference.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
