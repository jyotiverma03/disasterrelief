package com.clover.disasterrelief.service.impl;

import com.clover.disasterrelief.domain.Help;
import com.clover.disasterrelief.repository.HelpRepository;
import com.clover.disasterrelief.service.HelpService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Help}.
 */
@Service
public class HelpServiceImpl implements HelpService {

    private final Logger log = LoggerFactory.getLogger(HelpServiceImpl.class);

    private final HelpRepository helpRepository;

    public HelpServiceImpl(HelpRepository helpRepository) {
        this.helpRepository = helpRepository;
    }

    @Override
    public Help save(Help help) {
        log.debug("Request to save Help : {}", help);
        return helpRepository.save(help);
    }

    @Override
    public Optional<Help> partialUpdate(Help help) {
        log.debug("Request to partially update Help : {}", help);

        return helpRepository
            .findById(help.getId())
            .map(existingHelp -> {
                if (help.getFirstName() != null) {
                    existingHelp.setFirstName(help.getFirstName());
                }
                if (help.getLastName() != null) {
                    existingHelp.setLastName(help.getLastName());
                }
                if (help.getMobileNo() != null) {
                    existingHelp.setMobileNo(help.getMobileNo());
                }

                return existingHelp;
            })
            .map(helpRepository::save);
    }

    @Override
    public Page<Help> findAll(Pageable pageable) {
        log.debug("Request to get all Helps");
        return helpRepository.findAll(pageable);
    }

    @Override
    public Optional<Help> findOne(String id) {
        log.debug("Request to get Help : {}", id);
        return helpRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Help : {}", id);
        helpRepository.deleteById(id);
    }
}
