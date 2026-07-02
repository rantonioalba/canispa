package com.devr.cani.spa.Entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 
 * Owner entity class representing the owner of pets.
 * This class can be expanded with fields such as name, contact information, etc.
 * For now, it serves as a placeholder for future development.
 */

@Data
@Entity
@Table(name = "owners")
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;        
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean active = true; // Default to true for new owners
    @JsonProperty("joinedAt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Pet>  pets = new ArrayList<>();

     // Helper methods
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }
    
    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setOwner(null);
    }
}


