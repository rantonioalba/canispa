package com.devr.cani.spa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String email;
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    private String phoneNumber;
    private String address;
    
}
