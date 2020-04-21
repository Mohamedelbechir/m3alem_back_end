package com.m3alem.m3alem_back_end.models;

import lombok.Data;

@Data
public class CourseRequest {
    private Integer cin;
    private String depart;
    private String arrivee;
    private Double distance;

}