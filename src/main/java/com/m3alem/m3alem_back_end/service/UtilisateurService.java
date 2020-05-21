package com.m3alem.m3alem_back_end.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.dto.UtilisateurInputDTO;
import com.m3alem.m3alem_back_end.dto.UtilisateurListingDTO;
import com.m3alem.m3alem_back_end.exceptions.AuthentificationException;
import com.m3alem.m3alem_back_end.exceptions.UserExistException;
import com.m3alem.m3alem_back_end.exceptions.UserNotFoundException;
import com.m3alem.m3alem_back_end.models.Utilisateur;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UtilisateurService {
    private UtilisateurDao utilisateurDao;
    private ModelMapper modelMapper;

    @Transactional
    public UtilisateurListingDTO save(UtilisateurInputDTO entity) {
        if (utilisateurDao.findById(entity.getCin()).isPresent())
            throw new UserExistException("le " + entity.getCin() + " existe déja");
        else {
            Utilisateur user = convertToEntity(entity);

            return convertToDto(utilisateurDao.saveAndFlush(user));
        }

    }

    public UtilisateurListingDTO update(Long cin, UtilisateurInputDTO entity) throws UserNotFoundException {
        Utilisateur utilisateur = utilisateurDao.findById(cin).orElseThrow(UserNotFoundException::new);
        modelMapper.map(entity, utilisateur);
        utilisateurDao.save(utilisateur);
        return convertToDto(utilisateur);
    }

    public UtilisateurListingDTO login(Long cin, String password) {
        final Utilisateur utilisateur = utilisateurDao.findByCinAndPassword(cin, password);
        if (utilisateur == null)
            throw new AuthentificationException("Utilisateur avec le cin : " + cin + " est introuvable");
        return convertToDto(utilisateur);
    }

    public Iterable<UtilisateurListingDTO> findAll() {
        return utilisateurDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());

    }

    private Utilisateur convertToEntity(UtilisateurInputDTO dto) throws ParseException {
        Utilisateur entity = modelMapper.map(dto, Utilisateur.class);
        return entity;
    }

    private UtilisateurListingDTO convertToDto(Utilisateur entity) {
        UtilisateurListingDTO dto = modelMapper.map(entity, UtilisateurListingDTO.class);
        return dto;
    }

}