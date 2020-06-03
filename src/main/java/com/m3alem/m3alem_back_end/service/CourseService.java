package com.m3alem.m3alem_back_end.service;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.m3alem.m3alem_back_end.daos.CourseDao;
import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.dto.CourseInputDTO;
import com.m3alem.m3alem_back_end.dto.CourseListingDTO;
import com.m3alem.m3alem_back_end.dto.DriverOnLineListingDTO;
import com.m3alem.m3alem_back_end.dto.HistoriqueCourseDTO;
import com.m3alem.m3alem_back_end.exceptions.UserNotFoundException;
import com.m3alem.m3alem_back_end.models.Avis;
import com.m3alem.m3alem_back_end.models.Course;
import com.m3alem.m3alem_back_end.models.Utilisateur;
import com.m3alem.utils.TypeUtilisateur;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseDao courseDao;
    private UtilisateurDao utilisateurDao;

    private ModelMapper modelMapper;

    /*
     * public List<CourseListingDTO> findAll() { return
     * courseDao.findAll().stream().map(this::getListingDTO).collect(Collectors.
     * toList()); }
     */

    @Transactional
    public CourseListingDTO save(CourseInputDTO entity) {
        Course course = new Course(null, LocalDateTime.now(), entity.getDistance(), entity.getPrixCourse(),
                entity.getLatLngDepart(), entity.getLatLngArrivee(), entity.getDepart(), entity.getArrivee(),
                utilisateurDao.findById(entity.getIdDriver()).orElseThrow(UserNotFoundException::new),
                utilisateurDao.findById(entity.getIdPassager()).orElseThrow(UserNotFoundException::new));

        return getListingDTO(courseDao.saveAndFlush(course));

    }

    public Iterable<HistoriqueCourseDTO> findAllHistoriqueCourse() {
        Iterable<HistoriqueCourseDTO> hists = courseDao.findAll().stream().map(this::convertToDtoHistCourse)
                .collect(Collectors.toList());
        return hists;
    }

    public Iterable<HistoriqueCourseDTO> findHistByDriver(Long cin) {
        Utilisateur driver = utilisateurDao.findById(cin).orElseThrow(UserNotFoundException::new);
        Iterable<HistoriqueCourseDTO> hists = courseDao.findByDriver(driver).stream().map(this::convertToDtoHistCourse)
                .collect(Collectors.toList());
        return hists;
    }

    public Iterable<HistoriqueCourseDTO> findHistByPassager(Long cin) {
        Utilisateur driver = utilisateurDao.findById(cin).orElseThrow(UserNotFoundException::new);
        Iterable<HistoriqueCourseDTO> hists = courseDao.findByPassager(driver).stream()
                .map(this::convertToDtoHistCourse).collect(Collectors.toList());
        return hists;
    }

    private CourseListingDTO getListingDTO(Course entity) {
        return new CourseListingDTO(entity.getId(), entity.getDateCourse(), entity.getDistance(), entity.getDepart(),
                entity.getArrivee(), entity.getPassager(), entity.getDriver());
    }

    public Iterable<DriverOnLineListingDTO> getDriversOnLine() {
        /** Juste pour le fun ;) */
        Function<List<Avis>, Double> getRating = (avis) -> {
            int nbAvis = avis.size();
            int points = 0;
            Double result = 0D;
            for (Avis item : avis) {
                points += item.getNbEtoile();
            }
            try {
                result = Double.valueOf(points / nbAvis);
            } catch (ArithmeticException e) {
                // Division par 0
            } catch (Exception e) {
            }
            return result;
        };

        List<DriverOnLineListingDTO> onLineListingDTOs = utilisateurDao.findAll().stream()
                .filter((item) -> item.getTypeUtilisateur().equals(TypeUtilisateur.chauffeur) && item.getIsOnLine())
                .map((item) -> new DriverOnLineListingDTO(item.getCin(), item.getNom(),
                        getRating.apply(item.getAvis().stream().collect(Collectors.toList())), 30.2, "Mercedes"))
                .collect(Collectors.toList());
        return onLineListingDTOs;
    }

    private HistoriqueCourseDTO convertToDtoHistCourse(Course course) {
        HistoriqueCourseDTO h = new HistoriqueCourseDTO();
        modelMapper.map(course, h);
        h.setNomDriver(course.getDriver().getNom());
        h.setNomPassager(course.getPassager().getNom());
        return h;
    }

}
