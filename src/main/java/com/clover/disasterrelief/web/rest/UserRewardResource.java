package com.clover.disasterrelief.web.rest;

import com.clover.disasterrelief.domain.UserReward;
import com.clover.disasterrelief.repository.UserRewardRepository;
import com.clover.disasterrelief.service.UserRewardService;
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
 * REST controller for managing {@link com.clover.disasterrelief.domain.UserReward}.
 */
@RestController
@RequestMapping("/api")
public class UserRewardResource {

    private final Logger log = LoggerFactory.getLogger(UserRewardResource.class);

    private static final String ENTITY_NAME = "userReward";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRewardService userRewardService;

    private final UserRewardRepository userRewardRepository;

    public UserRewardResource(UserRewardService userRewardService, UserRewardRepository userRewardRepository) {
        this.userRewardService = userRewardService;
        this.userRewardRepository = userRewardRepository;
    }

    /**
     * {@code POST  /user-rewards} : Create a new userReward.
     *
     * @param userReward the userReward to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userReward, or with status {@code 400 (Bad Request)} if the userReward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-rewards")
    public ResponseEntity<UserReward> createUserReward(@RequestBody UserReward userReward) throws URISyntaxException {
        log.debug("REST request to save UserReward : {}", userReward);
        if (userReward.getId() != null) {
            throw new BadRequestAlertException("A new userReward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserReward result = userRewardService.save(userReward);
        return ResponseEntity
            .created(new URI("/api/user-rewards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-rewards/:id} : Updates an existing userReward.
     *
     * @param id the id of the userReward to save.
     * @param userReward the userReward to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userReward,
     * or with status {@code 400 (Bad Request)} if the userReward is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userReward couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-rewards/{id}")
    public ResponseEntity<UserReward> updateUserReward(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UserReward userReward
    ) throws URISyntaxException {
        log.debug("REST request to update UserReward : {}, {}", id, userReward);
        if (userReward.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userReward.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRewardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserReward result = userRewardService.save(userReward);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userReward.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-rewards/:id} : Partial updates given fields of an existing userReward, field will ignore if it is null
     *
     * @param id the id of the userReward to save.
     * @param userReward the userReward to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userReward,
     * or with status {@code 400 (Bad Request)} if the userReward is not valid,
     * or with status {@code 404 (Not Found)} if the userReward is not found,
     * or with status {@code 500 (Internal Server Error)} if the userReward couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-rewards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserReward> partialUpdateUserReward(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UserReward userReward
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserReward partially : {}, {}", id, userReward);
        if (userReward.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userReward.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRewardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserReward> result = userRewardService.partialUpdate(userReward);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userReward.getId())
        );
    }

    /**
     * {@code GET  /user-rewards} : get all the userRewards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRewards in body.
     */
    @GetMapping("/user-rewards")
    public ResponseEntity<List<UserReward>> getAllUserRewards(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserRewards");
        Page<UserReward> page = userRewardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-rewards/:id} : get the "id" userReward.
     *
     * @param id the id of the userReward to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userReward, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-rewards/{id}")
    public ResponseEntity<UserReward> getUserReward(@PathVariable String id) {
        log.debug("REST request to get UserReward : {}", id);
        Optional<UserReward> userReward = userRewardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userReward);
    }

    /**
     * {@code DELETE  /user-rewards/:id} : delete the "id" userReward.
     *
     * @param id the id of the userReward to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-rewards/{id}")
    public ResponseEntity<Void> deleteUserReward(@PathVariable String id) {
        log.debug("REST request to delete UserReward : {}", id);
        userRewardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
