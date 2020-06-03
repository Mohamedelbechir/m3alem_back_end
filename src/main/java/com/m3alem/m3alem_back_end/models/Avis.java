package com.m3alem.m3alem_back_end.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Avis {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Utilisateur driver;
  
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Utilisateur passager;
  private Integer nbEtoile = 0;
  private String message;
  private LocalDateTime dateAvis = LocalDateTime.now();

}