package com.m3alem.m3alem_back_end.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private String latLngArrivee; // concatenation de laltitude et longitude avec <<;>>
    @NotBlank
    private String depart; // chaine du nom de lieu
    @NotBlank
    private String arrivee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_driver")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cin")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("idDriver")
    private Utilisateur driver;

    @ManyToOne(fetch = FetchType.LAZY /* optional = false */)
    @JoinColumn(name = "id_passager")
    @OnDelete(action = OnDeleteAction.CASCADE)
   // @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    /*
     * Pour vous assurer que le project id sera correctement inclus en tant que
     * propriété de course pendant la sérialisation, ajoutez l' annotation
     */
  //  @JsonIdentityReference(alwaysAsId = true)
    /*
     * Pour aider à désérialiser taskcorrectement l' obje
     */
   // @JsonProperty("idPassager")
    private Utilisateur passager;

}