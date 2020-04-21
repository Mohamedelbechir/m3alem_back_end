package com.m3alem.m3alem_back_end.daos;

import com.m3alem.m3alem_back_end.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {
    Course findById(long id);
}