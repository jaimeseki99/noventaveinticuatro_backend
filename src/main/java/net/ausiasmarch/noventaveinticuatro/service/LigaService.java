package net.ausiasmarch.noventaveinticuatro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.LigaEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.LigaRepository;

@Service
public class LigaService {
    
    @Autowired
    LigaRepository oLigaRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public LigaEntity get(Long id) {
        return oLigaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Liga no encontrada."));
    }

    public Page<LigaEntity> getPage(Pageable oPageable) {
        return oLigaRepository.findAll(oPageable);
    }

    public Long create(LigaEntity oLigaEntity) {
        oLigaEntity.setId(null);
        return oLigaRepository.save(oLigaEntity).getId();
    }

    public LigaEntity update(LigaEntity oLigaEntity) {
        if (oLigaEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: La liga no existe.");
        } else {
            return oLigaRepository.save(oLigaEntity);
        }
    }

    public Long delete(Long id) {
        if (oLigaRepository.existsById(id)) {
            oLigaRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: La liga no existe.");
        }
    }

    public LigaEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oLigaRepository.count()), 1);
        return oLigaRepository.findAll(oPageable).getContent().get(0);
    }

    public Long populate(int amount) {
        for (int i=0; i<amount; i++) {
            String nombreLiga = DataGenerationHelper.getLigaRandom();
            String pais = DataGenerationHelper.getPaisRandom();
            String deporte = DataGenerationHelper.getDeporteRandom();
            oLigaRepository.save(new LigaEntity(nombreLiga, pais, deporte));
        }
        return oLigaRepository.count();
    } 

    @Transactional
    public Long empty() {
        oLigaRepository.deleteAll();
        oLigaRepository.resetAutoIncrement();
        oLigaRepository.flush();
        return oLigaRepository.count();
    }
}
