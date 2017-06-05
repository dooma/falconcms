package com.falcon.cms.service.impl;

import com.falcon.cms.service.PaperAuthorService;
import com.falcon.cms.domain.PaperAuthor;
import com.falcon.cms.repository.PaperAuthorRepository;
import com.falcon.cms.service.dto.PaperAuthorDTO;
import com.falcon.cms.service.mapper.PaperAuthorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PaperAuthor.
 */
@Service
@Transactional
public class PaperAuthorServiceImpl implements PaperAuthorService{

    private final Logger log = LoggerFactory.getLogger(PaperAuthorServiceImpl.class);

    private final PaperAuthorRepository paperAuthorRepository;

    private final PaperAuthorMapper paperAuthorMapper;

    public PaperAuthorServiceImpl(PaperAuthorRepository paperAuthorRepository, PaperAuthorMapper paperAuthorMapper) {
        this.paperAuthorRepository = paperAuthorRepository;
        this.paperAuthorMapper = paperAuthorMapper;
    }

    /**
     * Save a paperAuthor.
     *
     * @param paperAuthorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaperAuthorDTO save(PaperAuthorDTO paperAuthorDTO) {
        log.debug("Request to save PaperAuthor : {}", paperAuthorDTO);
        PaperAuthor paperAuthor = paperAuthorMapper.toEntity(paperAuthorDTO);
        paperAuthor = paperAuthorRepository.save(paperAuthor);
        return paperAuthorMapper.toDto(paperAuthor);
    }

    /**
     *  Get all the paperAuthors.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaperAuthorDTO> findAll() {
        log.debug("Request to get all PaperAuthors");
        return paperAuthorRepository.findAll().stream()
            .map(paperAuthorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one paperAuthor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaperAuthorDTO findOne(Long id) {
        log.debug("Request to get PaperAuthor : {}", id);
        PaperAuthor paperAuthor = paperAuthorRepository.findOne(id);
        return paperAuthorMapper.toDto(paperAuthor);
    }

    /**
     *  Delete the  paperAuthor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaperAuthor : {}", id);
        paperAuthorRepository.delete(id);
    }
}
