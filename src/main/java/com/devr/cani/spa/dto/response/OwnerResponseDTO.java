package com.devr.cani.spa.dto.response;

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetResponseDTO {
        private Long id;
        private String name;
        private String birthDate;
        private String image;
    }
}
