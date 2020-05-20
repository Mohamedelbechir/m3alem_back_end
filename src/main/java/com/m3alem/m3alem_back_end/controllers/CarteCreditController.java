package com.m3alem.m3alem_back_end.controllers;

import com.m3alem.m3alem_back_end.dto.CarteCreditInputDTO;
import com.m3alem.m3alem_back_end.dto.CarteCreditListingDTO;
import com.m3alem.m3alem_back_end.service.CarteCreditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarteCreditController {
    @Autowired
    private CarteCreditService carteCreditService;

    @PostMapping(value = "/carte-credit")
    private ResponseEntity<CarteCreditListingDTO> setOnLine(
            @RequestBody final CarteCreditInputDTO carteCreditInputDTO) {

        final CarteCreditListingDTO carteAdded = carteCreditService.save(carteCreditInputDTO);

        if (carteAdded == null)
            return ResponseEntity.noContent().build();
        return new ResponseEntity<CarteCreditListingDTO>(carteAdded, HttpStatus.CREATED);
    }
}