package com.m3alem.m3alem_back_end.controllers;

import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.exceptions.AuthentificationException;
import com.m3alem.m3alem_back_end.models.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;
    @PostMapping(value="/utilisateurs")
    public ResponseEntity<Utilisateur> ajouterElection(@RequestBody Utilisateur utilisateur) {
        try {
            
        Utilisateur utilisateurAdded = utilisateurDao.save(utilisateur);
      
        
        if (utilisateurAdded == null)
             return ResponseEntity.noContent().build();
             
        //  URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        //          .buildAndExpand(electionAdded.getId()).toUri();
          return new ResponseEntity<Utilisateur>(utilisateurAdded, HttpStatus.CREATED);
         
        } catch (Exception e) {
            throw e;
        }
        
    }
    @GetMapping(value="/login/{cin}/{password}")
    public ResponseEntity< Utilisateur> login(@PathVariable String cin, @PathVariable String  password) throws AuthentificationException {
        Utilisateur utilisateur = utilisateurDao.findByCinAndPassword(cin,password);
        if(utilisateur == null)
            throw new AuthentificationException("Utilisateur avec le cin : "+ cin +" est introuvable");
    
        return new ResponseEntity< Utilisateur>(utilisateur, HttpStatus.OK);
    }

    @GetMapping(value="/utilisateurs")
    public ResponseEntity< Iterable<Utilisateur>> getUtilisateurs() {
        Iterable<Utilisateur> utilisateurs = utilisateurDao.findAll();
    
        return new ResponseEntity< Iterable<Utilisateur>>(utilisateurs, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "utilisateurs/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable String id,@RequestBody Utilisateur utilisateur){
      
        HttpHeaders headers = new HttpHeaders();
		if(utilisateur == null){ 
			return new ResponseEntity<Utilisateur>(headers, HttpStatus.BAD_REQUEST);
		}
		Utilisateur currentUtilisateur = this.utilisateurDao.findById(Long.parseLong(id));
		if(currentUtilisateur == null){ 
			return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);
        }
        currentUtilisateur.setAdresse(utilisateur.getAdresse());
        currentUtilisateur.setCin(utilisateur.getCin());
        currentUtilisateur.setDateNaissance(utilisateur.getDateNaissance());
        currentUtilisateur.setDateDemande(utilisateur.getDateDemande());
        currentUtilisateur.setEmail(utilisateur.getEmail());
        currentUtilisateur.setNom(utilisateur.getNom());
        currentUtilisateur.setPassword(utilisateur.getPassword());
        currentUtilisateur.setPrenom(utilisateur.getPrenom());
        currentUtilisateur.setSexe(utilisateur.getSexe());
        currentUtilisateur.setTel(utilisateur.getTel());
        currentUtilisateur.setTypeUtilisateur(utilisateur.getTypeUtilisateur());

      /*  currentUtilisateur.clearElections(); 

        for(Election elect : utilisateur.getElections()) { 
			currentUtilisateur.addElection(elect);
		}*/
        this.utilisateurDao.save(currentUtilisateur);
         
		return new ResponseEntity<Utilisateur>(currentUtilisateur, HttpStatus.OK);
    }

   
}