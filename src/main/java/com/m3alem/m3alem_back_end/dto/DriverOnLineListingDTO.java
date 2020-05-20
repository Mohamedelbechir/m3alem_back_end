package com.m3alem.m3alem_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverOnLineListingDTO {

    private long cin;
    private String nom;
    private Double rating;
    private Double temps;
    private String typeVoiture;


}
