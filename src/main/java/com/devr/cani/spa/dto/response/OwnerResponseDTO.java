package com.devr.cani.spa.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean active; // Default to true for new owners
    @JsonProperty("joinedAt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt; // Optional field for createdAt, can be set by the service layer

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetResponseDTO {
        private Long id;
        private String name;
        private String birthDate;
        private String image;
        private boolean active;
        private LocalDateTime createdAt;
    }
}
