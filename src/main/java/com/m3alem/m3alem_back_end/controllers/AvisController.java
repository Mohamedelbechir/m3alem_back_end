package com.m3alem.m3alem_back_end.controllers;

import com.m3alem.m3alem_back_end.dto.AvisInputDTO;
import com.m3alem.m3alem_back_end.dto.AvisListingDTO;
import com.m3alem.m3alem_back_end.exceptions.AvisNotFoundExeption;
import com.m3alem.m3alem_back_end.exceptions.UserNotFoundException;
import com.m3alem.m3alem_back_end.service.AvisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvisController {
    @Autowired
    private AvisService avisService;

    @GetMapping(value = "/avis")
    public ResponseEntity<Iterable<AvisListingDTO>> findAll() {
        final Iterable<AvisListingDTO> avis = avisService.findAll();
        return new ResponseEntity<Iterable<AvisListingDTO>>(avis, HttpStatus.OK);
    }

    @PostMapping(value = "/avis")
    public ResponseEntity<AvisListingDTO> save(@RequestBody final AvisInputDTO dto) throws UserNotFoundException {
        try {
            final AvisListingDTO avisAdded = avisService.save(dto);
            return new ResponseEntity<AvisListingDTO>(avisAdded, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    @PutMapping(value = "avis/{id}")
    public ResponseEntity<AvisListingDTO> update(@PathVariable final Long id,
            @RequestBody final AvisInputDTO avisInputDTO) {

        AvisListingDTO currentAvis = avisService.update(id, avisInputDTO);
        return new ResponseEntity<AvisListingDTO>(currentAvis, HttpStatus.OK);
    }

    @DeleteMapping(value = "avis/{id}")
    public ResponseEntity<Long> delete(@PathVariable final Long id) {
        try {
            avisService.delete(id);
            return new ResponseEntity<Long>(id, HttpStatus.CREATED);
        } catch (AvisNotFoundExeption e) {
            throw e;
        }

    }

}