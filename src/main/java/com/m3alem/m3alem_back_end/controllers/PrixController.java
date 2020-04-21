package com.m3alem.m3alem_back_end.controllers;

import java.util.HashMap;
import java.util.Map;

import com.m3alem.m3alem_back_end.daos.PrixDao;
import com.m3alem.m3alem_back_end.models.Prix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private PrixDao prixDao;

    @PostMapping(value = "/prix")
    public ResponseEntity<Prix> ajouterElection(@RequestBody final Prix prix) throws Exception {
        try {
            final Prix PrixAdded = prixDao.save(prix);
            if (PrixAdded == null)
                return ResponseEntity.noContent().build();
            return new ResponseEntity<Prix>(PrixAdded, HttpStatus.CREATED);

        } catch (final Exception e) {
            throw e;
        }
    }

    @PutMapping(value = "prix/{id}")
    public ResponseEntity<Prix> update(@PathVariable final String id, @RequestBody final Prix prix) {

        final HttpHeaders headers = new HttpHeaders();
        if (prix == null) {
            return new ResponseEntity<Prix>(headers, HttpStatus.BAD_REQUEST);
        }
        final Prix currentPrix = prixDao.findById(Long.parseLong(id));
        if (currentPrix == null) {
            return new ResponseEntity<Prix>(HttpStatus.NOT_FOUND);
        }
        currentPrix.setPrixKm(prix.getPrixKm());
        prixDao.save(currentPrix);

        return new ResponseEntity<Prix>(currentPrix, HttpStatus.OK);
    }

    @GetMapping(value = "/prix/{distance}")
    ResponseEntity<Map<String, Double>> getPrixCourse(@PathVariable Double distance) {
        // prixKm * distance
        Double prix = prixDao.findAll().get(0).getPrixKm() * distance;

        Map<String, Double> result = new HashMap<String, Double>();
        result.put("prix", prix);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}