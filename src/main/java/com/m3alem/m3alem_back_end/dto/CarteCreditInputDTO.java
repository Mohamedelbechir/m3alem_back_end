package com.m3alem.m3alem_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteCreditInputDTO {
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;
    private String codeInternet;
    private Long idUser;
}