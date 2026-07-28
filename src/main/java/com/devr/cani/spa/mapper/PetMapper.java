package com.devr.cani.spa.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devr.cani.spa.Entity.Pet;
import com.devr.cani.spa.dto.PetRequestDTO;
import com.devr.cani.spa.dto.response.PetResponseDTO;

@Component
public class PetMapper {
    @Autowired
    private ModelMapper modelMapper;

     public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public PetResponseDTO toDTO(Pet pet) {
        if (pet == null) {
            return null;
        }
        return modelMapper.map(pet, PetResponseDTO.class);
    }

    public Pet toEntity(PetRequestDTO petRequestDTO) {
        if (petRequestDTO == null) {
            return null;
        }
        return modelMapper.map(petRequestDTO, Pet.class);
    }
}
