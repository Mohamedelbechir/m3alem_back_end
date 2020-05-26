package com.m3alem.m3alem_back_end.service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.m3alem.m3alem_back_end.daos.AvisDao;
import com.m3alem.m3alem_back_end.daos.UtilisateurDao;
import com.m3alem.m3alem_back_end.dto.AvisInputDTO;
import com.m3alem.m3alem_back_end.dto.AvisListingDTO;
import com.m3alem.m3alem_back_end.exceptions.AvisNotFoundExeption;
import com.m3alem.m3alem_back_end.exceptions.UserNotFoundException;
import com.m3alem.m3alem_back_end.models.Avis;
import com.m3alem.m3alem_back_end.models.Utilisateur;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AvisService {
    private AvisDao avisDao;
    private ModelMapper modelMapper;
    private UtilisateurDao utilisateurDao;

    public AvisListingDTO save(AvisInputDTO entity) throws UserNotFoundException {
        Avis avis = convertToEntity(entity);
        return convertToDto(avisDao.saveAndFlush(avis));

    }

    @Transactional
    public AvisListingDTO update(Long id, AvisInputDTO avisInputDTO) {
        Avis avis = avisDao.findById(id).orElseThrow(UserNotFoundException::new);
        modelMapper.map(avisInputDTO, avis);
        avisDao.save(avis);
        return convertToDto(avis);
    }

    public Boolean delete(Long id) {
        Optional<Avis> dAvis = avisDao.findById(id);
        if (dAvis.isPresent()) {
            avisDao.delete(dAvis.get());
            return true;
        }
        throw new AvisNotFoundExeption("L'id " + id + " ne correspond à aucune avis");
    }

    public Iterable<AvisListingDTO> findAll() {
        return avisDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private Avis convertToEntity(AvisInputDTO dto) throws ParseException {
        Avis entity = new Avis();
        entity.setDriver(utilisateurDao.findById(dto.getIdDriver()).orElseThrow(UserNotFoundException::new));
        entity.setPassager(utilisateurDao.findById(dto.getIdPassager()).orElseThrow(UserNotFoundException::new));
        entity.setNbEtoile(dto.getNbEtoile());
        entity.setMessage(dto.getMessage());

        return entity;
    }

    private AvisListingDTO convertToDto(Avis entity) {
        Function<Utilisateur, String> fullName = (Utilisateur item) -> {
            StringBuilder builder = new StringBuilder();
            builder.append(item.getPrenom());
            return builder.toString();
        };
        AvisListingDTO dto = new AvisListingDTO(entity.getId(), fullName.apply(entity.getPassager()),
                fullName.apply(entity.getDriver()), entity.getDateAvis(), entity.getNbEtoile(), entity.getMessage());
        return dto;
    }

}