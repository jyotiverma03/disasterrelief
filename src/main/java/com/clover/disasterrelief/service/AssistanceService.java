package com.clover.disasterrelief.service;

import com.clover.disasterrelief.domain.Assistance;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Assistance}.
 */
public interface AssistanceService {
    /**
     * Save a assistance.
     *
     * @param assistance the entity to save.
     * @return the persisted entity.
     */
    Assistance save(Assistance assistance);

    /**
     * Partially updates a assistance.
     *
     * @param assistance the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Assistance> partialUpdate(Assistance assistance);

    /**
     * Get all the assistances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Assistance> findAll(Pageable pageable);

    /**
     * Get the "id" assistance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Assistance> findOne(String id);

    /**
     * Delete the "id" assistance.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
