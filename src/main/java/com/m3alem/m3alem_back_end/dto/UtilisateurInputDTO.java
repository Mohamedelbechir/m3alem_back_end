package com.m3alem.m3alem_back_end.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurInputDTO {
    @NotNull
    private Long cin;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    private String tel;
    private String adresse;
    @NotBlank
    @Email
    private String email;
    private String sexe;
    private String password;
    @NotEmpty
    private String typeUtilisateur;
    private String etatInscription;

}