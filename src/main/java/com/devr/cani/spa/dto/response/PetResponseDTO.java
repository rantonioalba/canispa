package com.devr.cani.spa.dto.response;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDTO {
    private Long id;
    private String name;    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String breed;
    private String color;
    private String image;
    private Long ownerId;
    private String ownerName;
}
