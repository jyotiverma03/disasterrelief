package com.clover.disasterrelief.service.impl;

import com.clover.disasterrelief.domain.Assistance;
import com.clover.disasterrelief.repository.AssistanceRepository;
import com.clover.disasterrelief.service.AssistanceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Assistance}.
 */
@Service
public class AssistanceServiceImpl implements AssistanceService {

    private final Logger log = LoggerFactory.getLogger(AssistanceServiceImpl.class);

    private final AssistanceRepository assistanceRepository;

    public AssistanceServiceImpl(AssistanceRepository assistanceRepository) {
        this.assistanceRepository = assistanceRepository;
    }

    @Override
    public Assistance save(Assistance assistance) {
        log.debug("Request to save Assistance : {}", assistance);
        return assistanceRepository.save(assistance);
    }

    @Override
    public Optional<Assistance> partialUpdate(Assistance assistance) {
        log.debug("Request to partially update Assistance : {}", assistance);

        return assistanceRepository
            .findById(assistance.getId())
            .map(existingAssistance -> {
                if (assistance.getName() != null) {
                    existingAssistance.setName(assistance.getName());
                }
                if (assistance.getDescription() != null) {
                    existingAssistance.setDescription(assistance.getDescription());
                }

                return existingAssistance;
            })
            .map(assistanceRepository::save);
    }

    @Override
    public Page<Assistance> findAll(Pageable pageable) {
        log.debug("Request to get all Assistances");
        return assistanceRepository.findAll(pageable);
    }

    @Override
    public Optional<Assistance> findOne(String id) {
        log.debug("Request to get Assistance : {}", id);
        return assistanceRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Assistance : {}", id);
        assistanceRepository.deleteById(id);
    }
}
