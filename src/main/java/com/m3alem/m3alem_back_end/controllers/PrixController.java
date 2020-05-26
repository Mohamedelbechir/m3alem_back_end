package com.m3alem.m3alem_back_end.controllers;

import java.util.HashMap;
import java.util.Map;

import com.m3alem.m3alem_back_end.models.Prix;
import com.m3alem.m3alem_back_end.service.PrixService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrixController {
    @Autowired
    private PrixService prixService;

    @PostMapping(value = "/prix")
    public ResponseEntity<Prix> add(@RequestBody final Prix prix) throws Exception {
        try {
            final Prix PrixAdded = prixService.save(prix);
            return new ResponseEntity<Prix>(PrixAdded, HttpStatus.CREATED);
        } catch (final Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/prix")
    public ResponseEntity<Prix> getPrix() throws Exception {
        try {
            final Prix PrixAdded = prixService.getPrix();
            return new ResponseEntity<Prix>(PrixAdded, HttpStatus.CREATED);
        } catch (final Exception e) {
            throw e;
        }
    }

    @PutMapping(value = "prix/{id}")
    public ResponseEntity<Prix> update(@PathVariable final Long id, @RequestBody final Prix prix) {
        final Prix currentPrix = prixService.update(id, prix);
        return new ResponseEntity<Prix>(currentPrix, HttpStatus.OK);
    }

    @GetMapping(value = "/prix/{distance}")
    ResponseEntity<Map<String, Double>> getPrixCourse(@PathVariable Double distance) {
        Double prix = prixService.getPrixCourse(distance);
        Map<String, Double> result = new HashMap<String, Double>();
        result.put("prix", prix);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}