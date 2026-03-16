package com.devr.cani.spa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Entity.Pet;
import com.devr.cani.spa.repository.PetRepository;

import lombok.SneakyThrows;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);                
    }

    @Override
    public Pet createPet(Pet pet) {
       return petRepository.save(pet);        
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
