package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverListingDTO {
    private Long cin;
    private String nom;
    private String prenom;
    private LocalDateTime dateNaissance;
    private LocalDateTime dateDemande;
    private Long tel;
    private String etatCompte;
    private String etatInscription;
}