package com.m3alem.m3alem_back_end.dto;

import lombok.Data;

@Data
public class CarteCreditListingDTO extends CarteCreditInputDTO {
    private long id;

    public CarteCreditListingDTO(long id, String cardNumber, String expiryDate, String cardHolderName, String codeInternet,
            long idUser) {
        super(cardNumber, expiryDate, cardHolderName, codeInternet, idUser);
        this.id = id;
    }
    

}