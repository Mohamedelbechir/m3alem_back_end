package com.m3alem.m3alem_back_end.models;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Utilisateur
 */
@Entity
public class Utilisateur {
    @Id
    private long cin;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    private String tel;
    private String adresse;
    @NotEmpty
    private String email;
    private String sexe;
    private String password;

    @NotEmpty
    private String typeUtilisateur;
    private String etatInscription;
    private String etatCompte;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateDemande;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photoPermis;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photoAssurance;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photoCarteGrise;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photoPhotoExterieur;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photoPieceIdentite;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photoIdentite;


    public Utilisateur() {
        super();
    }

    public Utilisateur(long cin, @NotNull String nom, @NotNull String prenom, Date dateNaissance, String tel,
            String adresse, @NotEmpty String email, String sexe, String password, @NotEmpty String typeUtilisateur,
            String etatInscription, String etatCompte, Date dateDemande, byte[] photoPermis, byte[] photoAssurance,
            byte[] photoCarteGrise, byte[] photoPhotoExterieur, byte[] photoPieceIdentite, byte[] photoIdentite) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.adresse = adresse;
        this.email = email;
        this.sexe = sexe;
        this.password = password;
        this.typeUtilisateur = typeUtilisateur;
        this.etatInscription = etatInscription;
        this.etatCompte = etatCompte;
        this.dateDemande = dateDemande;
        this.photoPermis = photoPermis;
        this.photoAssurance = photoAssurance;
        this.photoCarteGrise = photoCarteGrise;
        this.photoPhotoExterieur = photoPhotoExterieur;
        this.photoPieceIdentite = photoPieceIdentite;
        this.photoIdentite = photoIdentite;
    }

    public long getCin() {
        return cin;
    }

    public void setCin(long cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public String getEtatInscription() {
        return etatInscription;
    }

    public void setEtatInscription(String etatInscription) {
        this.etatInscription = etatInscription;
    }

    public String getEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(String etatCompte) {
        this.etatCompte = etatCompte;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public byte[] getPhotoPermis() {
        return photoPermis;
    }

    public void setPhotoPermis(byte[] photoPermis) {
        this.photoPermis = photoPermis;
    }

    public byte[] getPhotoAssurance() {
        return photoAssurance;
    }

    public void setPhotoAssurance(byte[] photoAssurance) {
        this.photoAssurance = photoAssurance;
    }

    public byte[] getPhotoCarteGrise() {
        return photoCarteGrise;
    }

    public void setPhotoCarteGrise(byte[] photoCarteGrise) {
        this.photoCarteGrise = photoCarteGrise;
    }

    public byte[] getPhotoPhotoExterieur() {
        return photoPhotoExterieur;
    }

    public void setPhotoPhotoExterieur(byte[] photoPhotoExterieur) {
        this.photoPhotoExterieur = photoPhotoExterieur;
    }

    public byte[] getPhotoPieceIdentite() {
        return photoPieceIdentite;
    }

    public void setPhotoPieceIdentite(byte[] photoPieceIdentite) {
        this.photoPieceIdentite = photoPieceIdentite;
    }

    public byte[] getPhotoIdentite() {
        return photoIdentite;
    }

    public void setPhotoIdentite(byte[] photoIdentite) {
        this.photoIdentite = photoIdentite;
    }
    

}