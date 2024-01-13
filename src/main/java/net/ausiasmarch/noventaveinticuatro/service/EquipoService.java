package net.ausiasmarch.noventaveinticuatro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.EquipoEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.repository.EquipoRepository;

@Service
public class EquipoService {
    
    @Autowired
    EquipoRepository oEquipoRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public EquipoEntity get(Long id) {
        return oEquipoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Equipo no encontrado."));
    }

    public Long create(EquipoEntity oEquipoEntity) {
        oEquipoEntity.setId(null);
        return oEquipoRepository.save(oEquipoEntity).getId();
    }

    public EquipoEntity update(EquipoEntity oEquipoEntity) {
        if (oEquipoEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: El equipo no existe.");
        } else {
            return oEquipoRepository.save(oEquipoEntity);
        }
    }

    public Long delete(Long id) {
        if (oEquipoRepository.existsById(id)) {
            oEquipoRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: El equipo no existe.");
        }
    }

    public EquipoEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oEquipoRepository.count()), 1);
        return oEquipoRepository.findAll(oPageable).getContent().get(0);
    }

    public Page<EquipoEntity> getPage(Pageable oPageable) {
        return oEquipoRepository.findAll(oPageable);
    }


    @Transactional
    public Long empty() {
        oEquipoRepository.deleteAll();
        oEquipoRepository.resetAutoIncrement();
        oEquipoRepository.flush();
        return oEquipoRepository.count();
    }




}
