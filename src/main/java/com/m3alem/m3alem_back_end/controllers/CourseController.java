package com.m3alem.m3alem_back_end.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.m3alem.m3alem_back_end.daos.CourseDao;
import com.m3alem.m3alem_back_end.dto.CourseConfirmationDTO;
import com.m3alem.m3alem_back_end.dto.CourseInputDTO;
import com.m3alem.m3alem_back_end.dto.CourseListingDTO;
import com.m3alem.m3alem_back_end.dto.DriverOnLineListingDTO;
import com.m3alem.m3alem_back_end.dto.HistoriqueCourseDTO;
import com.m3alem.m3alem_back_end.models.Course;
import com.m3alem.m3alem_back_end.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;

    //
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /*
     * Le passager commande une course alors on notifier tous les chauffeurs
     */
    @MessageMapping("/passager-request") // les passager utilise pour commander une course
    public void attentedeCourse(CourseInputDTO courseInputDTO) {
        simpMessagingTemplate.convertAndSend("/course/driver-course-waiting/" + courseInputDTO.getIdDriver(),
                courseInputDTO);
    }

    /*
     * Chauffeur a validé alors on notifie le passager
     */
    @MessageMapping("/driver-course-confirmation")
    public void accepterCourse(CourseConfirmationDTO courseConfirmationDTO) {
        simpMessagingTemplate.convertAndSend(
                "/course/passager-request-response/" + courseConfirmationDTO.getIdPassager(), courseConfirmationDTO);
    }

    @GetMapping(value = "/courses")
    public ResponseEntity<Iterable<Course>> findAll() {
        final Iterable<Course> utilisateurs = courseDao.findAll();
        return new ResponseEntity<Iterable<Course>>(utilisateurs, HttpStatus.OK);
    }

    @GetMapping(value = "/courses/histories/{cin}")
    public ResponseEntity<Iterable<HistoriqueCourseDTO>> findHisByDriver(@PathVariable Long cin) {
        final Iterable<HistoriqueCourseDTO> hists = courseService.findHistByDriver(cin);
        return new ResponseEntity<Iterable<HistoriqueCourseDTO>>(hists, HttpStatus.OK);
    }

    @GetMapping(value = "/drivers-online")
    public ResponseEntity<Iterable<DriverOnLineListingDTO>> getDriverOnLine() {
        final Iterable<DriverOnLineListingDTO> drivers = courseService.getDriversOnLine();
        return new ResponseEntity<Iterable<DriverOnLineListingDTO>>(drivers, HttpStatus.OK);
    }

    @PostMapping(value = "/courses")
    public ResponseEntity<CourseListingDTO> ajouterElection(@RequestBody final CourseInputDTO course) throws Exception {
        try {
            final CourseListingDTO courseAdded = courseService.save(course);
            if (courseAdded == null)
                return ResponseEntity.noContent().build();
            return new ResponseEntity<CourseListingDTO>(courseAdded, HttpStatus.CREATED);
        } catch (final Exception e) {
            throw e;
        }
    }
}
