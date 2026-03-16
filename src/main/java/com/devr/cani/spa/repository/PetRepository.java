package com.devr.cani.spa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devr.cani.spa.Entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
    
}
