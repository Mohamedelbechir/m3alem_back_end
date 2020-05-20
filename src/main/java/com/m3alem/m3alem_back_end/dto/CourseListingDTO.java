package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.m3alem.m3alem_back_end.models.Utilisateur;

import org.springframework.format.annotation.DateTimeFormat;
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