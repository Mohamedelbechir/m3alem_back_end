package com.m3alem.m3alem_back_end.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.m3alem.m3alem_back_end.daos.CourseDao;
import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.models.Course;
import com.m3alem.m3alem_back_end.models.Utilisateur;
import com.m3alem.utils.IdsCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    Logger logger = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /*
     * un passager commande une course et elle est envoyée aux chauffeurs
     */
    @MessageMapping("/passager-request") // les passager utilise pour commander une course
    @SendTo("/course/driver-waiting") // Les chauffeur l'écoute pour recevoir des notification
    public Course attentedeCourse(@RequestBody Course course) {
        return course;

    }

    /*
     * @MessageMapping("/driver-accept") // les chauffeurs utilisent pour accepter
     * une course
     * 
     * @SendToUser("/course/accepted/") Course commanderAccecpter(@RequestBody
     * Course course, Principal user) { return course; }
     */
    // Le chauffeur a accepter la course
    /*
     * @PostMapping("/driver-accept/{passager}/{driver}") ResponseEntity<Map<String,
     * String>> commandeAccepter(@RequestBody Course course, @PathVariable long
     * idPassager,
     * 
     * @PathVariable long idDriver) { Utilisateur passager =
     * utilisateurDao.findById(idPassager); Utilisateur driver =
     * utilisateurDao.findById(idDriver); course.setPassager(passager);
     * course.setDriver(driver);
     * simpMessagingTemplate.convertAndSend("/course/accepted/" +
     * course.getPassager().getCin(), course); Map<String, String> response = new
     * HashMap<String, String>();
     * 
     * response.put("resultat", "ok"); return new ResponseEntity<Map<String,
     * String>>(response, HttpStatus.OK); }
     */

    @MessageMapping("/driver-accept")
    public void accepterCourse(@RequestBody Course course, @RequestBody IdsCourse idsCourse) {
        setCourse(course, idsCourse.getIdDriver(), idsCourse.getIdPassager());
        simpMessagingTemplate.convertAndSend("/course/accepted/" + course.getPassager().getCin(), course);
    }

    /* Lorsque le passager valide sa course */
    @MessageMapping("/passager-validate")
    public void validerCourse(@RequestBody Course course, @RequestBody IdsCourse idsCourse) {

        setCourse(course, idsCourse.getIdDriver(), idsCourse.getIdPassager());
        // Notifier le chauffeur choisi par le passager
        simpMessagingTemplate.convertAndSend("/course/accepted-by-passager/" + course.getPassager().getCin(), course);

    }

    void setCourse(Course course, long idDriver, long idPassager) {
        Utilisateur passager = utilisateurDao.findById(idPassager);
        Utilisateur driver = utilisateurDao.findById(idDriver);
        course.setPassager(passager);
        course.setDriver(driver);
    }

    @GetMapping(value = "/courses")
    public ResponseEntity<Iterable<Course>> getUtilisateurs() {
        final Iterable<Course> utilisateurs = courseDao.findAll();

        return new ResponseEntity<Iterable<Course>>(utilisateurs, HttpStatus.OK);
    }

    @PostMapping(value = "/courses")
    public ResponseEntity<Course> ajouterElection(@RequestBody final Course course) throws Exception {
        try {
            final Course utilisateurAdded = courseDao.save(course);

            if (utilisateurAdded == null)
                return ResponseEntity.noContent().build();
            return new ResponseEntity<Course>(utilisateurAdded, HttpStatus.CREATED);

        } catch (final Exception e) {
            throw e;
        }

    }
}