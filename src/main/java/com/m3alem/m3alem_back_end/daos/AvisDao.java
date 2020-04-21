package com.m3alem.m3alem_back_end.daos;

import com.m3alem.m3alem_back_end.models.Avis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisDao extends JpaRepository<Avis, Long> {
    Avis findById(long id);
}