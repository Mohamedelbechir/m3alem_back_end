package com.m3alem.m3alem_back_end.daos;

import com.m3alem.m3alem_back_end.models.Contrat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContratDao extends JpaRepository<Contrat, Long> {
    Contrat findById(long id);
}