package com.m3alem.m3alem_back_end.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurListingDTO {
    private long cin;
    private String nom;
    private String prenom;
    private String tel;
    private String adresse;
    private String email;
    private String sexe;
    private String password;
    private LocalDateTime dateDemande;
    private LocalDateTime dateNaissance;
    private String typeUtilisateur;
    private String etatInscription;
    private String etatCompte;
}