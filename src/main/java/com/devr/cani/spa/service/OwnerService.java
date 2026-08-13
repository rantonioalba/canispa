package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import com.devr.cani.spa.Entity.Owner;
import com.devr.cani.spa.dto.OwnerRequestDTO;
import com.devr.cani.spa.dto.response.OwnerResponseDTO;

public interface OwnerService {
    List<OwnerResponseDTO> getAllOwners();
    Optional<Owner> getOwnerById(Long id);
    OwnerResponseDTO createOwner(OwnerRequestDTO owner);
    Owner updateOwner(Long id, Owner owner);
    void deleteOwner(Long id);
}
