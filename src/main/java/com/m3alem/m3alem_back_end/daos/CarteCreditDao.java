package com.m3alem.m3alem_back_end.daos;

import com.m3alem.m3alem_back_end.models.CarteCredit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteCreditDao extends JpaRepository<CarteCredit, Long> {
    CarteCredit findById(long id);
}