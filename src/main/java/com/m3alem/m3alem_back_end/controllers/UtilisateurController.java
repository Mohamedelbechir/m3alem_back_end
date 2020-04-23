package com.m3alem.m3alem_back_end.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.exceptions.AuthentificationException;
import com.m3alem.m3alem_back_end.models.Utilisateur;
import com.m3alem.utils.CodeImage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping(value = "/multipart", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestPart("file") final MultipartFile myfile) {
        // LOG.info("Received file {}", file.getOriginalFilename());
        /*
         * try { File file = new
         * File("/users/devk/"+myfile.getOriginalFilename()+".jpg");
         * myfile.transferTo(file); return new ResponseEntity(HttpStatus.CREATED);
         * }catch (IOException e){ return new ResponseEntity(HttpStatus.BAD_REQUEST); }
         */
        return new ResponseEntity<String>("received " + myfile.getOriginalFilename(), HttpStatus.OK);

    }

    @PostMapping(value = "/utilisateurs/online/{cin}/{status}")
    private ResponseEntity<Map<String, Boolean>> setOnLine(@PathVariable final long cin,
            @PathVariable final boolean status) {

        final Utilisateur currentUtilisateur = utilisateurDao.findById(cin);
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        if (currentUtilisateur == null) {
            return new ResponseEntity<Map<String, Boolean>>(HttpStatus.NOT_FOUND);
        }
        currentUtilisateur.setIsOnLine(status);
        utilisateurDao.save(currentUtilisateur);

        response.put("resultat", status);
        return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.OK);
    }

    /*
     * @RequestMapping(value = "/file", method = RequestMethod.POST, consumes = {
     * "multipart/form-data" }) public ResponseEntity<HttpStatus>
     * uploadSingleFile(@RequestPart("file") final MultipartFile file) { try { final
     * File convertFile = new File(System.getProperty("user.dir") + "/images/" +
     * file.getOriginalFilename()); convertFile.createNewFile(); final
     * FileOutputStream fout = new FileOutputStream(convertFile);
     * fout.write(file.getBytes()); fout.close();
     * System.out.println(convertFile.getParent()); return new
     * ResponseEntity(HttpStatus.CREATED); } catch (final IOException e) { return
     * new ResponseEntity(HttpStatus.BAD_REQUEST); }
     * 
     * }
     */
    @PutMapping(value = "/utilisateurs/photos/{cin}/{codePhoto}", consumes = { "multipart/form-data" })
    public ResponseEntity<HttpStatus> uploadSingleFile(@PathVariable final long cin,
            @PathVariable final String codePhoto, @RequestPart("file") final MultipartFile file) {
        try {
            Utilisateur currentUtilisateur = utilisateurDao.findById(cin);
            if (currentUtilisateur == null)
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            switch (codePhoto) {
                case CodeImage.PHOTO_PERMIS:
                    currentUtilisateur.setPhotoPermis(file.getBytes());
                    break;
                case CodeImage.PHOTO_ASSURANCE:
                    currentUtilisateur.setPhotoAssurance(file.getBytes());
                    break;
                case CodeImage.PHOTO_CARTE_GRISE:
                    currentUtilisateur.setPhotoCarteGrise(file.getBytes());
                    break;
                case CodeImage.PHOTO_VOITURE:
                    currentUtilisateur.setPhotoPhotoExterieur(file.getBytes());
                    break;
                case CodeImage.PHOTO_PIECE_IDENTITE:
                    currentUtilisateur.setPhotoPieceIdentite(file.getBytes());
                    break;
                case CodeImage.PHOTO_IDENTITE:
                    currentUtilisateur.setPhotoIdentite(file.getBytes());
                    break;

                default:
                    break;
            }
            this.utilisateurDao.save(currentUtilisateur);

            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/utilisateur/images/{cin}/{imageCode}")
    public ResponseEntity<String> getImages(@PathVariable final long cin, @PathVariable final String imageCode) {
        try {

            /*
             * Aller chercher l'utilisateur correspondant
             */
            Utilisateur utilisateur = utilisateurDao.findById(cin);
            String encodedImage = "";
            switch (imageCode) {
                case CodeImage.PHOTO_PERMIS:
                    encodedImage = Base64.getEncoder().encodeToString(utilisateur.getPhotoPermis());
                    break;
                case CodeImage.PHOTO_ASSURANCE:
                    encodedImage = Base64.getEncoder().encodeToString(utilisateur.getPhotoAssurance());
                    break;
                case CodeImage.PHOTO_CARTE_GRISE:
                    encodedImage = Base64.getEncoder().encodeToString(utilisateur.getPhotoCarteGrise());
                    break;
                case CodeImage.PHOTO_VOITURE:
                    encodedImage = Base64.getEncoder().encodeToString(utilisateur.getPhotoPhotoExterieur());
                    break;
                case CodeImage.PHOTO_PIECE_IDENTITE:
                    encodedImage = Base64.getEncoder().encodeToString(utilisateur.getPhotoPieceIdentite());
                    break;
                case CodeImage.PHOTO_IDENTITE:
                    encodedImage = Base64.getEncoder().encodeToString(utilisateur.getPhotoIdentite());
                    break;

                default:
                    break;
            }
            // final InputStream inputStream = new
            // ClassPathResource("images/bojack.jpg").getInputStream();
            /*
             * final InputStream inputStream = new ClassPathResource(
             * System.getProperty("user.dir") + "/images/" + pathImage).getInputStream();
             * final byte[] bytes = IOUtils.toByteArray(inputStream);
             */
            // final String encodedImage = Base64.getEncoder().encodeToString(bytes);

            return new ResponseEntity<String>(encodedImage == null ? "" : encodedImage, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }
        // throw new RuntimeException("oops!");
    }

    @PostMapping(value = "/utilisateurs")
    public ResponseEntity<Utilisateur> addUser(@RequestBody final Utilisateur utilisateur
    /* @RequestParam("mySingleFile") final MultipartFile file */) throws Exception {
        try {
            /*
             * final File convertFile = new File(getClass().getResource("/images") +
             * file.getOriginalFilename()); convertFile.createNewFile(); final
             * FileOutputStream fout = new FileOutputStream(convertFile);
             * fout.write(file.getBytes()); fout.close();
             */
            final Utilisateur utilisateurAdded = utilisateurDao.save(utilisateur);

            if (utilisateurAdded == null)
                return ResponseEntity.noContent().build();

            // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            // .buildAndExpand(electionAdded.getId()).toUri();
            return new ResponseEntity<Utilisateur>(utilisateurAdded, HttpStatus.CREATED);

        } catch (final Exception e) {
            throw e;
        }

    }

    @GetMapping(value = "/login/{cin}/{password}")
    public ResponseEntity<Utilisateur> login(@PathVariable final long cin, @PathVariable final String password)
            throws AuthentificationException {
        final Utilisateur utilisateur = utilisateurDao.findByCinAndPassword(cin, password);
        if (utilisateur == null)
            throw new AuthentificationException("Utilisateur avec le cin : " + cin + " est introuvable");

        return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateurs")
    public ResponseEntity<Iterable<Utilisateur>> getUsers() {
        final Iterable<Utilisateur> utilisateurs = utilisateurDao.findAll();

        return new ResponseEntity<Iterable<Utilisateur>>(utilisateurs, HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateurs/{cin}")
    public ResponseEntity<Utilisateur> getUser(@PathVariable long cin) {
        Utilisateur utilisateur = utilisateurDao.findById(cin);

        return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "utilisateurs/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable final String id,
            @RequestBody final Utilisateur utilisateur) {

        final HttpHeaders headers = new HttpHeaders();
        if (utilisateur == null) {
            return new ResponseEntity<Utilisateur>(headers, HttpStatus.BAD_REQUEST);
        }
        final Utilisateur currentUtilisateur = this.utilisateurDao.findById(Long.parseLong(id));
        if (currentUtilisateur == null) {
            return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);
        }

        currentUtilisateur.setAdresse(utilisateur.getAdresse());
        currentUtilisateur.setCin(utilisateur.getCin());
        currentUtilisateur.setDateNaissance(utilisateur.getDateNaissance());
        currentUtilisateur.setDateDemande(utilisateur.getDateDemande());
        currentUtilisateur.setEmail(utilisateur.getEmail());
        currentUtilisateur.setEtatCompte(utilisateur.getEtatCompte());
        currentUtilisateur.setEtatInscription(utilisateur.getEtatInscription());
        currentUtilisateur.setNom(utilisateur.getNom());
        currentUtilisateur.setPassword(utilisateur.getPassword());
        currentUtilisateur.setPrenom(utilisateur.getPrenom());
        currentUtilisateur.setSexe(utilisateur.getSexe());
        currentUtilisateur.setTel(utilisateur.getTel());
        currentUtilisateur.setTypeUtilisateur(utilisateur.getTypeUtilisateur());
        currentUtilisateur.setTypeUtilisateur(utilisateur.getTypeUtilisateur());

        /*
         * currentUtilisateur.clearElections();
         * 
         * for(Election elect : utilisateur.getElections()) {
         * currentUtilisateur.addElection(elect); }
         */
        this.utilisateurDao.save(currentUtilisateur);

        return new ResponseEntity<Utilisateur>(currentUtilisateur, HttpStatus.OK);
    }

}