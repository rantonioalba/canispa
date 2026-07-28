package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Entity.Owner;
import com.devr.cani.spa.Entity.Pet;
import com.devr.cani.spa.dto.PetRequestDTO;
import com.devr.cani.spa.dto.response.PetResponseDTO;
import com.devr.cani.spa.exception.ResourceNotFoundException;
import com.devr.cani.spa.mapper.PetMapper;
import com.devr.cani.spa.repository.OwnerRepository;
import com.devr.cani.spa.repository.PetRepository;

import lombok.SneakyThrows;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetMapper petMapper;

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);                
    }

    @Override
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) {
        // Check if Owner exists
        Owner owner = ownerRepository.findById(petRequestDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + petRequestDTO.getOwnerId()));

        // convert PetRequestDTO to Pet entity
        Pet pet = petMapper.toEntity(petRequestDTO);
        pet.setOwner(owner);

        Pet savedPet = petRepository.save(pet);

        PetResponseDTO petResponseDTO = petMapper.toDTO(savedPet);
        return petResponseDTO;
    }

    @Override
    @SneakyThrows
    public Pet updatePet(Long id, Pet pet) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));   
        existingPet.setName(pet.getName());
        existingPet.setBirthDate(pet.getBirthDate());
        existingPet.setImage(pet.getImage());
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePet(Long id) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        petRepository.delete(existingPet);
    }

}
