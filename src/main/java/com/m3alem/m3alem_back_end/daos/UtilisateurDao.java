package com.m3alem.m3alem_back_end.daos;

import java.util.List;

import com.m3alem.m3alem_back_end.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * InnerUtilisateurDao
 */
@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByCinAndPassword(Long cin, String password);
    Utilisateur findById(long id);
    List<Utilisateur> findBytypeUtilisateur(String typeUtilisateur);
    // void update(Utilisateur currentUtilisateur);

}