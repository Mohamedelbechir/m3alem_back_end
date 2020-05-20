package com.m3alem.m3alem_back_end.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteCredit {
    @Id
    private Long id;
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;
    private String codeInternet;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utilisateur utilisateur;

}