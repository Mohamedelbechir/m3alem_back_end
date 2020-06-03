package com.m3alem.m3alem_back_end.daos;

import java.util.List;

import com.m3alem.m3alem_back_end.models.Course;
import com.m3alem.m3alem_back_end.models.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {
    Course findById(long id);
    List<Course> findByDriver(Utilisateur driver);
    List<Course> findByPassager(Utilisateur driver);
}