package com.falcon.cms.service;

import com.falcon.cms.service.dto.ParticipantDTO;
import java.util.List;

/**
 * Service Interface for managing Participant.
 */
public interface ParticipantService {

    /**
     * Save a participant.
     *
     * @param participantDTO the entity to save
     * @return the persisted entity
     */
    ParticipantDTO save(ParticipantDTO participantDTO);

    /**
     *  Get all the participants.
     *
     *  @return the list of entities
     */
    List<ParticipantDTO> findAll();

    /**
     *  Get the "id" participant.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ParticipantDTO findOne(Long id);

    /**
     *  Delete the "id" participant.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
