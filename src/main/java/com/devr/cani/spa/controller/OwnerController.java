package com.devr.cani.spa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devr.cani.spa.Entity.Owner;
import com.devr.cani.spa.service.OwnerService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




//@RestController
//@RequestMapping("/api/v1/owners")

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class OwnerController {

    @Autowired
    private OwnerService ownerService;
    
    @GetMapping    
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long id) {
        Optional<Owner> owner = ownerService.getOwnerById(id);
        if (owner.isPresent()) {
            return ResponseEntity.ok(owner.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found with id: " + id);
        }   
    }

    @PostMapping
    public ResponseEntity<?> createOwner(@RequestBody Owner owner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.createOwner(owner));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        try {
            return ResponseEntity.ok(ownerService.updateOwner(id, owner));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        try {
            ownerService.deleteOwner(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
   
}
