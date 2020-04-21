package com.m3alem.m3alem_back_end.daos;



import com.m3alem.m3alem_back_end.models.Prix;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrixDao extends JpaRepository<Prix, Long> {
    Prix findById(long id);
}