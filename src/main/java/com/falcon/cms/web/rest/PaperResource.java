package com.falcon.cms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.falcon.cms.service.PaperService;
import com.falcon.cms.web.rest.util.HeaderUtil;
import com.falcon.cms.service.dto.PaperDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Paper.
 */
@RestController
@RequestMapping("/api")
public class PaperResource {

    private final Logger log = LoggerFactory.getLogger(PaperResource.class);

    private static final String ENTITY_NAME = "paper";

    private final PaperService paperService;

    public PaperResource(PaperService paperService) {
        this.paperService = paperService;
    }

    /**
     * POST  /papers : Create a new paper.
     *
     * @param paperDTO the paperDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paperDTO, or with status 400 (Bad Request) if the paper has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/papers")
    @Timed
    public ResponseEntity<PaperDTO> createPaper(@Valid @RequestBody PaperDTO paperDTO) throws URISyntaxException {
        log.debug("REST request to save Paper : {}", paperDTO);
        if (paperDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paper cannot already have an ID")).body(null);
        }
        PaperDTO result = paperService.save(paperDTO);
        return ResponseEntity.created(new URI("/api/papers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /papers : Updates an existing paper.
     *
     * @param paperDTO the paperDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paperDTO,
     * or with status 400 (Bad Request) if the paperDTO is not valid,
     * or with status 500 (Internal Server Error) if the paperDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/papers")
    @Timed
    public ResponseEntity<PaperDTO> updatePaper(@Valid @RequestBody PaperDTO paperDTO) throws URISyntaxException {
        log.debug("REST request to update Paper : {}", paperDTO);
        if (paperDTO.getId() == null) {
            return createPaper(paperDTO);
        }
        PaperDTO result = paperService.save(paperDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paperDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /papers : get all the papers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of papers in body
     */
    @GetMapping("/papers")
    @Timed
    public List<PaperDTO> getAllPapers() {
        log.debug("REST request to get all Papers");
        return paperService.findAll();
    }

    /**
     * GET  /papers/:id : get the "id" paper.
     *
     * @param id the id of the paperDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paperDTO, or with status 404 (Not Found)
     */
    @GetMapping("/papers/{id}")
    @Timed
    public ResponseEntity<PaperDTO> getPaper(@PathVariable Long id) {
        log.debug("REST request to get Paper : {}", id);
        PaperDTO paperDTO = paperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paperDTO));
    }

    /**
     * DELETE  /papers/:id : delete the "id" paper.
     *
     * @param id the id of the paperDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/papers/{id}")
    @Timed
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        log.debug("REST request to delete Paper : {}", id);
        paperService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
