package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Entity.Owner;
import com.devr.cani.spa.dto.OwnerRequestDTO;
import com.devr.cani.spa.dto.response.OwnerResponseDTO;
import com.devr.cani.spa.exception.ResourceNotFoundException;
import com.devr.cani.spa.mapper.OwnerMapper;
import com.devr.cani.spa.repository.OwnerRepository;

import lombok.SneakyThrows;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<Owner> getAllOwners() {        
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);                
    }

    @Override
    public OwnerResponseDTO createOwner(OwnerRequestDTO ownerRequestDTO) {
        Owner owner = ownerMapper.map(ownerRequestDTO, Owner.class);    

        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.map(savedOwner, OwnerResponseDTO.class); 
    }

    @Override
    @SneakyThrows
    public Owner updateOwner(Long id, Owner owner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        existingOwner.setName(owner.getName());
        existingOwner.setEmail(owner.getEmail());
        existingOwner.setPhoneNumber(owner.getPhoneNumber());
        existingOwner.setAddress(owner.getAddress());
        return ownerRepository.save(existingOwner);
    }

    @Override
    public void deleteOwner(Long id) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        ownerRepository.delete(existingOwner);        
    }

}
