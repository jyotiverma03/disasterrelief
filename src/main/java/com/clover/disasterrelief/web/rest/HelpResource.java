package com.clover.disasterrelief.web.rest;

import com.clover.disasterrelief.domain.Help;
import com.clover.disasterrelief.repository.HelpRepository;
import com.clover.disasterrelief.service.HelpService;
import com.clover.disasterrelief.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.clover.disasterrelief.domain.Help}.
 */
@RestController
@RequestMapping("/api")
public class HelpResource {

    private final Logger log = LoggerFactory.getLogger(HelpResource.class);

    private static final String ENTITY_NAME = "help";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HelpService helpService;

    private final HelpRepository helpRepository;

    public HelpResource(HelpService helpService, HelpRepository helpRepository) {
        this.helpService = helpService;
        this.helpRepository = helpRepository;
    }

    /**
     * {@code POST  /helps} : Create a new help.
     *
     * @param help the help to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new help, or with status {@code 400 (Bad Request)} if the help has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/helps")
    public ResponseEntity<Help> createHelp(@Valid @RequestBody Help help) throws URISyntaxException {
        log.debug("REST request to save Help : {}", help);
        if (help.getId() != null) {
            throw new BadRequestAlertException("A new help cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Help result = helpService.save(help);
        return ResponseEntity
            .created(new URI("/api/helps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /helps/:id} : Updates an existing help.
     *
     * @param id the id of the help to save.
     * @param help the help to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated help,
     * or with status {@code 400 (Bad Request)} if the help is not valid,
     * or with status {@code 500 (Internal Server Error)} if the help couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/helps/{id}")
    public ResponseEntity<Help> updateHelp(@PathVariable(value = "id", required = false) final String id, @Valid @RequestBody Help help)
        throws URISyntaxException {
        log.debug("REST request to update Help : {}, {}", id, help);
        if (help.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, help.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!helpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Help result = helpService.save(help);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, help.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /helps/:id} : Partial updates given fields of an existing help, field will ignore if it is null
     *
     * @param id the id of the help to save.
     * @param help the help to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated help,
     * or with status {@code 400 (Bad Request)} if the help is not valid,
     * or with status {@code 404 (Not Found)} if the help is not found,
     * or with status {@code 500 (Internal Server Error)} if the help couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/helps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Help> partialUpdateHelp(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Help help
    ) throws URISyntaxException {
        log.debug("REST request to partial update Help partially : {}, {}", id, help);
        if (help.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, help.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!helpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Help> result = helpService.partialUpdate(help);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, help.getId()));
    }

    /**
     * {@code GET  /helps} : get all the helps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of helps in body.
     */
    @GetMapping("/helps")
    public ResponseEntity<List<Help>> getAllHelps(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Helps");
        Page<Help> page = helpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /helps/:id} : get the "id" help.
     *
     * @param id the id of the help to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the help, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/helps/{id}")
    public ResponseEntity<Help> getHelp(@PathVariable String id) {
        log.debug("REST request to get Help : {}", id);
        Optional<Help> help = helpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(help);
    }

    /**
     * {@code DELETE  /helps/:id} : delete the "id" help.
     *
     * @param id the id of the help to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/helps/{id}")
    public ResponseEntity<Void> deleteHelp(@PathVariable String id) {
        log.debug("REST request to delete Help : {}", id);
        helpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
