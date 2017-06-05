package com.falcon.cms.service.impl;

import com.falcon.cms.service.ParticipantService;
import com.falcon.cms.domain.Participant;
import com.falcon.cms.repository.ParticipantRepository;
import com.falcon.cms.service.dto.ParticipantDTO;
import com.falcon.cms.service.mapper.ParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Participant.
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService{

    private final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    private final ParticipantRepository participantRepository;

    private final ParticipantMapper participantMapper;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    /**
     * Save a participant.
     *
     * @param participantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParticipantDTO save(ParticipantDTO participantDTO) {
        log.debug("Request to save Participant : {}", participantDTO);
        Participant participant = participantMapper.toEntity(participantDTO);
        participant = participantRepository.save(participant);
        return participantMapper.toDto(participant);
    }

    /**
     *  Get all the participants.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParticipantDTO> findAll() {
        log.debug("Request to get all Participants");
        return participantRepository.findAll().stream()
            .map(participantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one participant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParticipantDTO findOne(Long id) {
        log.debug("Request to get Participant : {}", id);
        Participant participant = participantRepository.findOne(id);
        return participantMapper.toDto(participant);
    }

    /**
     *  Delete the  participant by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participant : {}", id);
        participantRepository.delete(id);
    }
}
