package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import com.devr.cani.spa.Entity.Pet;

public interface PetService {
    List<Pet> getAllPets();
    Optional<Pet> getPetById(Long id);
    Pet createPet(Pet pet);
    Pet updatePet(Long id, Pet pet);
    void deletePet(Long id);
}
