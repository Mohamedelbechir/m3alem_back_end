package com.m3alem.m3alem_back_end.service;

import com.m3alem.m3alem_back_end.daos.CarteCreditDao;
import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.dto.CarteCreditInputDTO;
import com.m3alem.m3alem_back_end.dto.CarteCreditListingDTO;
import com.m3alem.m3alem_back_end.exceptions.UserNotFoundException;
import com.m3alem.m3alem_back_end.models.CarteCredit;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarteCreditService {
    private CarteCreditDao carteCreditDao;
    private UtilisateurDao utilisateurDao;

    @Transactional
    public CarteCreditListingDTO save(CarteCreditInputDTO entity) {
        CarteCredit carteCredit = new CarteCredit(null, entity.getCardNumber(), entity.getExpiryDate(),
                entity.getCardHolderName(), entity.getCodeInternet(),
                utilisateurDao.findById(entity.getIdUser()).orElseThrow(UserNotFoundException::new));

        return getListingDTO(carteCreditDao.saveAndFlush(carteCredit));

    }

    private CarteCreditListingDTO getListingDTO(CarteCredit entity) {
        return new CarteCreditListingDTO(entity.getId(), entity.getCardNumber(), entity.getExpiryDate(), entity.getCardHolderName(),
                entity.getCodeInternet(), entity.getUtilisateur().getCin());
    }
}