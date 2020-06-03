package com.m3alem.m3alem_back_end.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.dto.DriverListingDTO;
import com.m3alem.m3alem_back_end.dto.UtilisateurInputDTO;
import com.m3alem.m3alem_back_end.dto.UtilisateurListingDTO;
import com.m3alem.m3alem_back_end.exceptions.AuthentificationException;
import com.m3alem.m3alem_back_end.exceptions.UserExistException;
import com.m3alem.m3alem_back_end.models.Utilisateur;
import com.m3alem.m3alem_back_end.service.UtilisateurService;
import com.m3alem.utils.CodeImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private UtilisateurService utilisateurService;

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
    private ResponseEntity<Map<String, Boolean>> setOnLine(@PathVariable final Long cin,
            @PathVariable final boolean status) {

        final Utilisateur currentUtilisateur = utilisateurDao.findById(cin).get();
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
    public ResponseEntity<HttpStatus> uploadSingleFile(@PathVariable final Long cin,
            @PathVariable final String codePhoto, @RequestPart("file") final MultipartFile file) {
        try {
            Utilisateur currentUtilisateur = utilisateurDao.findById(cin).get();
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
            Utilisateur utilisateur = utilisateurDao.findById(cin).get();
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
    }

    @PostMapping(value = "/utilisateurs")
    public ResponseEntity<UtilisateurListingDTO> addUser(@RequestBody final UtilisateurInputDTO utilisateur)
            throws UserExistException {
        try {
            final UtilisateurListingDTO utilisateurAdded = utilisateurService.save(utilisateur);
            return new ResponseEntity<UtilisateurListingDTO>(utilisateurAdded, HttpStatus.CREATED);
        } catch (UserExistException e) {
            throw e;
        }

    }

    @GetMapping(value = "/login/{cin}/{password}")
    public ResponseEntity<Utilisateur> login(@PathVariable final long cin, @PathVariable final String password)
            throws AuthentificationException {
        final Utilisateur utilisateur = utilisateurService.login(cin, password);
        return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateurs")
    public ResponseEntity<Iterable<UtilisateurListingDTO>> getUsers() {
        final Iterable<UtilisateurListingDTO> utilisateurs = utilisateurService.findAll();
        return new ResponseEntity<Iterable<UtilisateurListingDTO>>(utilisateurs, HttpStatus.OK);
    }

    @GetMapping(value = "/drivers")
    public ResponseEntity<Iterable<DriverListingDTO>> getDrivers() {
        final Iterable<DriverListingDTO> utilisateurs = utilisateurService.findDrivers();
        return new ResponseEntity<Iterable<DriverListingDTO>>(utilisateurs, HttpStatus.OK);
    }

    @PutMapping(value = "drivers")
    public ResponseEntity<DriverListingDTO> updateDriver(@RequestBody final DriverListingDTO driverListingDTO) {
        DriverListingDTO currentUtilisateur = utilisateurService.update(driverListingDTO);
        return new ResponseEntity<DriverListingDTO>(currentUtilisateur, HttpStatus.OK);
    }

    // @CrossOrigin(origins = "http://localhost:4200")

    @PutMapping(value = "utilisateurs/{id}")
    public ResponseEntity<UtilisateurListingDTO> update(@PathVariable final Long id,
            @RequestBody final UtilisateurInputDTO utilisateur) {
        UtilisateurListingDTO currentUtilisateur = utilisateurService.update(id, utilisateur);
        return new ResponseEntity<UtilisateurListingDTO>(currentUtilisateur, HttpStatus.OK);
    }

}