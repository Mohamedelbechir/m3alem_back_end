package com.m3alem.m3alem_back_end.service;

import java.util.Optional;

import com.m3alem.m3alem_back_end.daos.PrixDao;
import com.m3alem.m3alem_back_end.exceptions.PrixNotFoundExeption;
import com.m3alem.m3alem_back_end.models.Prix;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrixService {
    @Autowired
    private PrixDao prixDao;

    private ModelMapper modelMapper;

    public Prix getPrix() {
        return prixDao.findAll().get(0);
    }

    public Prix save(Prix prix) {
        Prix prixAdded = prixDao.saveAndFlush(prix);
        return prixAdded;
    }

    public Prix update(Long id, Prix prix) throws PrixNotFoundExeption {
        Optional<Prix> p = prixDao.findById(id);
        if (p.isPresent()) {
            modelMapper.map(prix, p.get());
            prixDao.save(p.get());
            return p.get();
        }
        throw new PrixNotFoundExeption(prix.getId() + " ne correspond pas un prix");
    }

    public Double getPrixCourse(double distance) {
        return prixDao.findAll().get(0).getPrixKm() * distance;
    }

}