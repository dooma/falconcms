package com.falcon.cms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.falcon.cms.service.OrganizerService;
import com.falcon.cms.web.rest.util.HeaderUtil;
import com.falcon.cms.service.dto.OrganizerDTO;
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
 * REST controller for managing Organizer.
 */
@RestController
@RequestMapping("/api")
public class OrganizerResource {

    private final Logger log = LoggerFactory.getLogger(OrganizerResource.class);

    private static final String ENTITY_NAME = "organizer";

    private final OrganizerService organizerService;

    public OrganizerResource(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    /**
     * POST  /organizers : Create a new organizer.
     *
     * @param organizerDTO the organizerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizerDTO, or with status 400 (Bad Request) if the organizer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organizers")
    @Timed
    public ResponseEntity<OrganizerDTO> createOrganizer(@Valid @RequestBody OrganizerDTO organizerDTO) throws URISyntaxException {
        log.debug("REST request to save Organizer : {}", organizerDTO);
        if (organizerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new organizer cannot already have an ID")).body(null);
        }
        OrganizerDTO result = organizerService.save(organizerDTO);
        return ResponseEntity.created(new URI("/api/organizers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizers : Updates an existing organizer.
     *
     * @param organizerDTO the organizerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizerDTO,
     * or with status 400 (Bad Request) if the organizerDTO is not valid,
     * or with status 500 (Internal Server Error) if the organizerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organizers")
    @Timed
    public ResponseEntity<OrganizerDTO> updateOrganizer(@Valid @RequestBody OrganizerDTO organizerDTO) throws URISyntaxException {
        log.debug("REST request to update Organizer : {}", organizerDTO);
        if (organizerDTO.getId() == null) {
            return createOrganizer(organizerDTO);
        }
        OrganizerDTO result = organizerService.save(organizerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizers : get all the organizers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organizers in body
     */
    @GetMapping("/organizers")
    @Timed
    public List<OrganizerDTO> getAllOrganizers() {
        log.debug("REST request to get all Organizers");
        return organizerService.findAll();
    }

    /**
     * GET  /organizers/:id : get the "id" organizer.
     *
     * @param id the id of the organizerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organizers/{id}")
    @Timed
    public ResponseEntity<OrganizerDTO> getOrganizer(@PathVariable Long id) {
        log.debug("REST request to get Organizer : {}", id);
        OrganizerDTO organizerDTO = organizerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organizerDTO));
    }

    /**
     * DELETE  /organizers/:id : delete the "id" organizer.
     *
     * @param id the id of the organizerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organizers/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        log.debug("REST request to delete Organizer : {}", id);
        organizerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
