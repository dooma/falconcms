package com.falcon.cms.service;

import com.falcon.cms.service.dto.OrganizerDTO;
import java.util.List;

/**
 * Service Interface for managing Organizer.
 */
public interface OrganizerService {

    /**
     * Save a organizer.
     *
     * @param organizerDTO the entity to save
     * @return the persisted entity
     */
    OrganizerDTO save(OrganizerDTO organizerDTO);

    /**
     *  Get all the organizers.
     *
     *  @return the list of entities
     */
    List<OrganizerDTO> findAll();

    /**
     *  Get the "id" organizer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrganizerDTO findOne(Long id);

    /**
     *  Delete the "id" organizer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
