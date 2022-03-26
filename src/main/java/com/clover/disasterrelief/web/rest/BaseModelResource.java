package com.clover.disasterrelief.web.rest;

import com.clover.disasterrelief.domain.BaseModel;
import com.clover.disasterrelief.repository.BaseModelRepository;
import com.clover.disasterrelief.service.BaseModelService;
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
 * REST controller for managing {@link com.clover.disasterrelief.domain.BaseModel}.
 */
@RestController
@RequestMapping("/api")
public class BaseModelResource {

    private final Logger log = LoggerFactory.getLogger(BaseModelResource.class);

    private static final String ENTITY_NAME = "baseModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseModelService baseModelService;

    private final BaseModelRepository baseModelRepository;

    public BaseModelResource(BaseModelService baseModelService, BaseModelRepository baseModelRepository) {
        this.baseModelService = baseModelService;
        this.baseModelRepository = baseModelRepository;
    }

    /**
     * {@code POST  /base-models} : Create a new baseModel.
     *
     * @param baseModel the baseModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseModel, or with status {@code 400 (Bad Request)} if the baseModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/base-models")
    public ResponseEntity<BaseModel> createBaseModel(@RequestBody BaseModel baseModel) throws URISyntaxException {
        log.debug("REST request to save BaseModel : {}", baseModel);
        if (baseModel.getId() != null) {
            throw new BadRequestAlertException("A new baseModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseModel result = baseModelService.save(baseModel);
        return ResponseEntity
            .created(new URI("/api/base-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /base-models/:id} : Updates an existing baseModel.
     *
     * @param id the id of the baseModel to save.
     * @param baseModel the baseModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseModel,
     * or with status {@code 400 (Bad Request)} if the baseModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/base-models/{id}")
    public ResponseEntity<BaseModel> updateBaseModel(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody BaseModel baseModel
    ) throws URISyntaxException {
        log.debug("REST request to update BaseModel : {}, {}", id, baseModel);
        if (baseModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, baseModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!baseModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BaseModel result = baseModelService.save(baseModel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseModel.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /base-models/:id} : Partial updates given fields of an existing baseModel, field will ignore if it is null
     *
     * @param id the id of the baseModel to save.
     * @param baseModel the baseModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseModel,
     * or with status {@code 400 (Bad Request)} if the baseModel is not valid,
     * or with status {@code 404 (Not Found)} if the baseModel is not found,
     * or with status {@code 500 (Internal Server Error)} if the baseModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/base-models/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BaseModel> partialUpdateBaseModel(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody BaseModel baseModel
    ) throws URISyntaxException {
        log.debug("REST request to partial update BaseModel partially : {}, {}", id, baseModel);
        if (baseModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, baseModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!baseModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BaseModel> result = baseModelService.partialUpdate(baseModel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseModel.getId())
        );
    }

    /**
     * {@code GET  /base-models} : get all the baseModels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseModels in body.
     */
    @GetMapping("/base-models")
    public ResponseEntity<List<BaseModel>> getAllBaseModels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BaseModels");
        Page<BaseModel> page = baseModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /base-models/:id} : get the "id" baseModel.
     *
     * @param id the id of the baseModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/base-models/{id}")
    public ResponseEntity<BaseModel> getBaseModel(@PathVariable String id) {
        log.debug("REST request to get BaseModel : {}", id);
        Optional<BaseModel> baseModel = baseModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(baseModel);
    }

    /**
     * {@code DELETE  /base-models/:id} : delete the "id" baseModel.
     *
     * @param id the id of the baseModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/base-models/{id}")
    public ResponseEntity<Void> deleteBaseModel(@PathVariable String id) {
        log.debug("REST request to delete BaseModel : {}", id);
        baseModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
