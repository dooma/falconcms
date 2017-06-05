package com.falcon.cms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.falcon.cms.service.PaperAuthorService;
import com.falcon.cms.web.rest.util.HeaderUtil;
import com.falcon.cms.service.dto.PaperAuthorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PaperAuthor.
 */
@RestController
@RequestMapping("/api")
public class PaperAuthorResource {

    private final Logger log = LoggerFactory.getLogger(PaperAuthorResource.class);

    private static final String ENTITY_NAME = "paperAuthor";

    private final PaperAuthorService paperAuthorService;

    public PaperAuthorResource(PaperAuthorService paperAuthorService) {
        this.paperAuthorService = paperAuthorService;
    }

    /**
     * POST  /paper-authors : Create a new paperAuthor.
     *
     * @param paperAuthorDTO the paperAuthorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paperAuthorDTO, or with status 400 (Bad Request) if the paperAuthor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paper-authors")
    @Timed
    public ResponseEntity<PaperAuthorDTO> createPaperAuthor(@RequestBody PaperAuthorDTO paperAuthorDTO) throws URISyntaxException {
        log.debug("REST request to save PaperAuthor : {}", paperAuthorDTO);
        if (paperAuthorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paperAuthor cannot already have an ID")).body(null);
        }
        PaperAuthorDTO result = paperAuthorService.save(paperAuthorDTO);
        return ResponseEntity.created(new URI("/api/paper-authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paper-authors : Updates an existing paperAuthor.
     *
     * @param paperAuthorDTO the paperAuthorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paperAuthorDTO,
     * or with status 400 (Bad Request) if the paperAuthorDTO is not valid,
     * or with status 500 (Internal Server Error) if the paperAuthorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paper-authors")
    @Timed
    public ResponseEntity<PaperAuthorDTO> updatePaperAuthor(@RequestBody PaperAuthorDTO paperAuthorDTO) throws URISyntaxException {
        log.debug("REST request to update PaperAuthor : {}", paperAuthorDTO);
        if (paperAuthorDTO.getId() == null) {
            return createPaperAuthor(paperAuthorDTO);
        }
        PaperAuthorDTO result = paperAuthorService.save(paperAuthorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paperAuthorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paper-authors : get all the paperAuthors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paperAuthors in body
     */
    @GetMapping("/paper-authors")
    @Timed
    public List<PaperAuthorDTO> getAllPaperAuthors() {
        log.debug("REST request to get all PaperAuthors");
        return paperAuthorService.findAll();
    }

    /**
     * GET  /paper-authors/:id : get the "id" paperAuthor.
     *
     * @param id the id of the paperAuthorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paperAuthorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/paper-authors/{id}")
    @Timed
    public ResponseEntity<PaperAuthorDTO> getPaperAuthor(@PathVariable Long id) {
        log.debug("REST request to get PaperAuthor : {}", id);
        PaperAuthorDTO paperAuthorDTO = paperAuthorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paperAuthorDTO));
    }

    /**
     * DELETE  /paper-authors/:id : delete the "id" paperAuthor.
     *
     * @param id the id of the paperAuthorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paper-authors/{id}")
    @Timed
    public ResponseEntity<Void> deletePaperAuthor(@PathVariable Long id) {
        log.debug("REST request to delete PaperAuthor : {}", id);
        paperAuthorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
