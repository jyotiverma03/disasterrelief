package com.clover.disasterrelief.web.rest;

import com.clover.disasterrelief.domain.Assistance;
import com.clover.disasterrelief.repository.AssistanceRepository;
import com.clover.disasterrelief.service.AssistanceService;
import com.clover.disasterrelief.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.clover.disasterrelief.domain.Assistance}.
 */
@RestController
@RequestMapping("/api")
public class AssistanceResource {

    private final Logger log = LoggerFactory.getLogger(AssistanceResource.class);

    private static final String ENTITY_NAME = "assistance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssistanceService assistanceService;

    private final AssistanceRepository assistanceRepository;

    public AssistanceResource(AssistanceService assistanceService, AssistanceRepository assistanceRepository) {
        this.assistanceService = assistanceService;
        this.assistanceRepository = assistanceRepository;
    }

    /**
     * {@code POST  /assistances} : Create a new assistance.
     *
     * @param assistance the assistance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assistance, or with status {@code 400 (Bad Request)} if the assistance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assistances")
    public ResponseEntity<Assistance> createAssistance(@RequestBody Assistance assistance) throws URISyntaxException {
        log.debug("REST request to save Assistance : {}", assistance);
        if (assistance.getId() != null) {
            throw new BadRequestAlertException("A new assistance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assistance result = assistanceService.save(assistance);
        return ResponseEntity
            .created(new URI("/api/assistances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /assistances/:id} : Updates an existing assistance.
     *
     * @param id the id of the assistance to save.
     * @param assistance the assistance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assistance,
     * or with status {@code 400 (Bad Request)} if the assistance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assistance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assistances/{id}")
    public ResponseEntity<Assistance> updateAssistance(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Assistance assistance
    ) throws URISyntaxException {
        log.debug("REST request to update Assistance : {}, {}", id, assistance);
        if (assistance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assistance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assistanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Assistance result = assistanceService.save(assistance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assistance.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /assistances/:id} : Partial updates given fields of an existing assistance, field will ignore if it is null
     *
     * @param id the id of the assistance to save.
     * @param assistance the assistance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assistance,
     * or with status {@code 400 (Bad Request)} if the assistance is not valid,
     * or with status {@code 404 (Not Found)} if the assistance is not found,
     * or with status {@code 500 (Internal Server Error)} if the assistance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/assistances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Assistance> partialUpdateAssistance(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Assistance assistance
    ) throws URISyntaxException {
        log.debug("REST request to partial update Assistance partially : {}, {}", id, assistance);
        if (assistance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assistance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assistanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Assistance> result = assistanceService.partialUpdate(assistance);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assistance.getId())
        );
    }

    /**
     * {@code GET  /assistances} : get all the assistances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assistances in body.
     */
    @GetMapping("/assistances")
    public ResponseEntity<List<Assistance>> getAllAssistances(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Assistances");
        Page<Assistance> page = assistanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assistances/:id} : get the "id" assistance.
     *
     * @param id the id of the assistance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assistance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assistances/{id}")
    public ResponseEntity<Assistance> getAssistance(@PathVariable String id) {
        log.debug("REST request to get Assistance : {}", id);
        Optional<Assistance> assistance = assistanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assistance);
    }

    /**
     * {@code DELETE  /assistances/:id} : delete the "id" assistance.
     *
     * @param id the id of the assistance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assistances/{id}")
    public ResponseEntity<Void> deleteAssistance(@PathVariable String id) {
        log.debug("REST request to delete Assistance : {}", id);
        assistanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
