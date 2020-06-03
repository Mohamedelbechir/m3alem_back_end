package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueCourseDTO {
    private Long id;
    private String nomDriver;
    private String nomPassager;
    private LocalDateTime dateCourse;
    private Double distance;
    private Double prixCourse;
    private String depart;
    private String arrivee;
}