package com.clover.disasterrelief.service;

import com.clover.disasterrelief.domain.Help;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Help}.
 */
public interface HelpService {
    /**
     * Save a help.
     *
     * @param help the entity to save.
     * @return the persisted entity.
     */
    Help save(Help help);

    /**
     * Partially updates a help.
     *
     * @param help the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Help> partialUpdate(Help help);

    /**
     * Get all the helps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Help> findAll(Pageable pageable);

    /**
     * Get the "id" help.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Help> findOne(String id);

    /**
     * Delete the "id" help.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
