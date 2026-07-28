package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import com.devr.cani.spa.Entity.Pet;
import com.devr.cani.spa.dto.PetRequestDTO;
import com.devr.cani.spa.dto.response.PetResponseDTO;

public interface PetService {
    List<Pet> getAllPets();
    Optional<Pet> getPetById(Long id);
    PetResponseDTO createPet(PetRequestDTO pet);
    Pet updatePet(Long id, Pet pet);
    void deletePet(Long id);
}
