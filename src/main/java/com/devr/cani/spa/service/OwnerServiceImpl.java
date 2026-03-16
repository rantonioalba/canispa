package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Entity.Owner;
import com.devr.cani.spa.repository.OwnerRepository;

import lombok.SneakyThrows;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<Owner> getAllOwners() {        
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);                
    }

    @Override
    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);        
    }

    @Override
    @SneakyThrows
    public Owner updateOwner(Long id, Owner owner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + id));
        existingOwner.setName(owner.getName());
        existingOwner.setEmail(owner.getEmail());
        existingOwner.setPhoneNumber(owner.getPhoneNumber());
        existingOwner.setAddress(owner.getAddress());
        return ownerRepository.save(existingOwner);
    }

    @Override
    public void deleteOwner(Long id) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + id));
        ownerRepository.delete(existingOwner);        
    }

}
