package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import com.devr.cani.spa.Entity.Owner;

public interface OwnerService {
    List<Owner> getAllOwners();
    Optional<Owner> getOwnerById(Long id);
    Owner createOwner(Owner owner);
    Owner updateOwner(Long id, Owner owner);
    void deleteOwner(Long id);
}
