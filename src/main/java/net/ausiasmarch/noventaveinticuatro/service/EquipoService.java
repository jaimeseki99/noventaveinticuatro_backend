package net.ausiasmarch.noventaveinticuatro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.EquipoEntity;
import net.ausiasmarch.noventaveinticuatro.entity.LigaEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.EquipoRepository;

@Service
public class EquipoService {
    
    @Autowired
    EquipoRepository oEquipoRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    LigaService oLigaService;

    @Autowired
    SessionService oSessionService;

    public EquipoEntity get(Long id) {
        return oEquipoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Equipo no encontrado."));
    }

    public Long create(EquipoEntity oEquipoEntity) {
        oSessionService.onlyAdmins();
        oEquipoEntity.setId(null);
        return oEquipoRepository.save(oEquipoEntity).getId();
    }

    public EquipoEntity update(EquipoEntity oEquipoEntity) {
        oSessionService.onlyAdmins();
        if (oEquipoEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: El equipo no existe.");
        } else {
            return oEquipoRepository.save(oEquipoEntity);
        }
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
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
        oSessionService.onlyAdminsOUsuarios();
        return oEquipoRepository.findAll(oPageable);
    }

    public Page<EquipoEntity> getPageByLigaId(Long liga_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oEquipoRepository.findByLigaId(liga_id, oPageable);
    }

    public Long populate(int amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            String nombre = DataGenerationHelper.getEquipoRandom();
            LigaEntity liga = oLigaService.getOneRandom();
            oEquipoRepository.save(new EquipoEntity(nombre, liga));
        }
        return oEquipoRepository.count();
    }



    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oEquipoRepository.deleteAll();
        oEquipoRepository.resetAutoIncrement();
        oEquipoRepository.flush();
        return oEquipoRepository.count();
    }




}
