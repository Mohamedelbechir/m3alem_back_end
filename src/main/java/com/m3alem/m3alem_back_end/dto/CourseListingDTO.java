package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import com.m3alem.m3alem_back_end.models.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseListingDTO {
    private Long id;
    private LocalDateTime dateCourse;
    private Double distance;
    @NotBlank
    private String depart; // concatenation de laltitude et longitude avec <<;>>
    @NotBlank
    private String arrivee;
    private Utilisateur passager;
    private Utilisateur driver;
}