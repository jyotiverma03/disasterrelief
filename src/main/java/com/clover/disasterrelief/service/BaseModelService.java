package com.clover.disasterrelief.service;

import com.clover.disasterrelief.domain.BaseModel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BaseModel}.
 */
public interface BaseModelService {
    /**
     * Save a baseModel.
     *
     * @param baseModel the entity to save.
     * @return the persisted entity.
     */
    BaseModel save(BaseModel baseModel);

    /**
     * Partially updates a baseModel.
     *
     * @param baseModel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BaseModel> partialUpdate(BaseModel baseModel);

    /**
     * Get all the baseModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BaseModel> findAll(Pageable pageable);

    /**
     * Get the "id" baseModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BaseModel> findOne(String id);

    /**
     * Delete the "id" baseModel.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
