package com.devr.cani.spa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devr.cani.spa.Entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
