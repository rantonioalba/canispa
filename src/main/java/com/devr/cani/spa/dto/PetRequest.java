package com.devr.cani.spa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {
    private String name;
    private String birthDate;
    private String image;
    private Long ownerId;
}
