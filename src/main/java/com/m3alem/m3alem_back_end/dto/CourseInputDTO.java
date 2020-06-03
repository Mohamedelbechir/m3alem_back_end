package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInputDTO {

    @NotNull
   /* @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")*/
    private LocalDateTime dateCourse;
    private Double distance;
    private Double prixCourse;
    @NotBlank
    private String latLngDepart; // concatenation de laltitude et longitude avec <<;>>
    @NotBlank
    private String latLngArrivee; 
    @NotBlank
    private String depart; // concatenation de laltitude et longitude avec <<;>>
    @NotBlank
    private String arrivee;
    private Long idDriver;
    private Long idPassager;

}