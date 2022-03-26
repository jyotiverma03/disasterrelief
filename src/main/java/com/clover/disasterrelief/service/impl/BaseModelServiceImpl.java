package com.clover.disasterrelief.service.impl;

import com.clover.disasterrelief.domain.BaseModel;
import com.clover.disasterrelief.repository.BaseModelRepository;
import com.clover.disasterrelief.service.BaseModelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link BaseModel}.
 */
@Service
public class BaseModelServiceImpl implements BaseModelService {

    private final Logger log = LoggerFactory.getLogger(BaseModelServiceImpl.class);

    private final BaseModelRepository baseModelRepository;

    public BaseModelServiceImpl(BaseModelRepository baseModelRepository) {
        this.baseModelRepository = baseModelRepository;
    }

    @Override
    public BaseModel save(BaseModel baseModel) {
        log.debug("Request to save BaseModel : {}", baseModel);
        return baseModelRepository.save(baseModel);
    }

    @Override
    public Optional<BaseModel> partialUpdate(BaseModel baseModel) {
        log.debug("Request to partially update BaseModel : {}", baseModel);

        return baseModelRepository
            .findById(baseModel.getId())
            .map(existingBaseModel -> {
                if (baseModel.getCreate() != null) {
                    existingBaseModel.setCreate(baseModel.getCreate());
                }
                if (baseModel.getModified() != null) {
                    existingBaseModel.setModified(baseModel.getModified());
                }
                if (baseModel.getDeleted() != null) {
                    existingBaseModel.setDeleted(baseModel.getDeleted());
                }
                if (baseModel.getUpdated() != null) {
                    existingBaseModel.setUpdated(baseModel.getUpdated());
                }

                return existingBaseModel;
            })
            .map(baseModelRepository::save);
    }

    @Override
    public Page<BaseModel> findAll(Pageable pageable) {
        log.debug("Request to get all BaseModels");
        return baseModelRepository.findAll(pageable);
    }

    @Override
    public Optional<BaseModel> findOne(String id) {
        log.debug("Request to get BaseModel : {}", id);
        return baseModelRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete BaseModel : {}", id);
        baseModelRepository.deleteById(id);
    }
}
