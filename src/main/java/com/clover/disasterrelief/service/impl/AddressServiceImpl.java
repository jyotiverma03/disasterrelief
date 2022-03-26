package com.clover.disasterrelief.service.impl;

import com.clover.disasterrelief.domain.Address;
import com.clover.disasterrelief.repository.AddressRepository;
import com.clover.disasterrelief.service.AddressService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> partialUpdate(Address address) {
        log.debug("Request to partially update Address : {}", address);

        return addressRepository
            .findById(address.getId())
            .map(existingAddress -> {
                if (address.getStreet1() != null) {
                    existingAddress.setStreet1(address.getStreet1());
                }
                if (address.getStreet2() != null) {
                    existingAddress.setStreet2(address.getStreet2());
                }
                if (address.getCity() != null) {
                    existingAddress.setCity(address.getCity());
                }
                if (address.getState() != null) {
                    existingAddress.setState(address.getState());
                }
                if (address.getPinCode() != null) {
                    existingAddress.setPinCode(address.getPinCode());
                }
                if (address.getCountry() != null) {
                    existingAddress.setCountry(address.getCountry());
                }
                if (address.getLatitude() != null) {
                    existingAddress.setLatitude(address.getLatitude());
                }
                if (address.getLongitude() != null) {
                    existingAddress.setLongitude(address.getLongitude());
                }

                return existingAddress;
            })
            .map(addressRepository::save);
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll(pageable);
    }

    @Override
    public Optional<Address> findOne(String id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
