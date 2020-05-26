package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvisListingDTO {
    private Long id;
    private String fullNamePassager;
    private String fullNameDriver;
    private LocalDateTime dateAvis;
    private Integer nbEtoile;
    private String message;

}