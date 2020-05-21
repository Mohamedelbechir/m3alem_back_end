package com.m3alem.m3alem_back_end.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Utilisateur
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    @Id
    private long cin;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

   /* @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")*/
    private LocalDateTime dateNaissance;
    // private Date dateNaissance;

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
    private String etatCompte;

    //@Temporal(TemporalType.TIMESTAMP)
    /*@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")*/
    private LocalDateTime dateDemande =  LocalDateTime.now();    
    
    // @Column( nullable = false,columnDefinition = "boolean default false")
    private Boolean  isOnLine = false;

    @OneToMany
    private Set<Avis> avis;

    @OneToOne
    private CarteCredit carteCredit;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photoPermis;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photoAssurance;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photoCarteGrise;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photoPhotoExterieur;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photoPieceIdentite;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photoIdentite;

}