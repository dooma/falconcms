package com.falcon.cms.service.impl;

import com.falcon.cms.service.PaperService;
import com.falcon.cms.domain.Paper;
import com.falcon.cms.repository.PaperRepository;
import com.falcon.cms.service.dto.PaperDTO;
import com.falcon.cms.service.mapper.PaperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Paper.
 */
@Service
@Transactional
public class PaperServiceImpl implements PaperService{

    private final Logger log = LoggerFactory.getLogger(PaperServiceImpl.class);

    private final PaperRepository paperRepository;

    private final PaperMapper paperMapper;

    public PaperServiceImpl(PaperRepository paperRepository, PaperMapper paperMapper) {
        this.paperRepository = paperRepository;
        this.paperMapper = paperMapper;
    }

    /**
     * Save a paper.
     *
     * @param paperDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaperDTO save(PaperDTO paperDTO) {
        log.debug("Request to save Paper : {}", paperDTO);
        Paper paper = paperMapper.toEntity(paperDTO);
        paper = paperRepository.save(paper);
        return paperMapper.toDto(paper);
    }

    /**
     *  Get all the papers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaperDTO> findAll() {
        log.debug("Request to get all Papers");
        return paperRepository.findAll().stream()
            .map(paperMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one paper by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaperDTO findOne(Long id) {
        log.debug("Request to get Paper : {}", id);
        Paper paper = paperRepository.findOne(id);
        return paperMapper.toDto(paper);
    }

    /**
     *  Delete the  paper by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paper : {}", id);
        paperRepository.delete(id);
    }
}
