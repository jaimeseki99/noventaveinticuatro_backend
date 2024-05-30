package net.ausiasmarch.noventaveinticuatro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.ModalidadEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.ModalidadRepository;

@Service
public class ModalidadService {

    @Autowired
    ModalidadRepository oModalidadRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    public ModalidadEntity get(Long id) {
        return oModalidadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Modalidad no encontrado."));
    }

    public Long create(ModalidadEntity oModalidadEntity) {
        oSessionService.onlyAdmins();
        oModalidadEntity.setId(null);
        return oModalidadRepository.save(oModalidadEntity).getId();
    }

    public ModalidadEntity update(ModalidadEntity oModalidadEntity) {
        oSessionService.onlyAdmins();
        if (oModalidadEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: La modalidad no existe.");
        } else {
            return oModalidadRepository.save(oModalidadEntity);
        }
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        if (oModalidadRepository.existsById(id)) {
            oModalidadRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: La modalidad no existe.");
        }
    }

    public ModalidadEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oModalidadRepository.count()), 1);
        return oModalidadRepository.findAll(oPageable).getContent().get(0);
    }

    public Page<ModalidadEntity> getPage(Pageable oPageable, String filtro) {
        oSessionService.onlyAdminsOUsuarios();
        if (filtro != null && !filtro.isEmpty() && !filtro.trim().isEmpty()) {
            return oModalidadRepository.findByNombreContainingIgnoreCase(filtro, oPageable);
        } else {
            return oModalidadRepository.findAll(oPageable);
        }
    }

    //Encontrar modalidad por nombre
    public ModalidadEntity findByNombre(String nombre) {
        oSessionService.onlyAdminsOUsuarios();
        return oModalidadRepository.findByNombre(nombre);
    }

    public Long populate(int amount) {
       oSessionService.onlyAdmins();
        for (int i=0; i<amount; i++) {
            String nombreModalidad = DataGenerationHelper.getTipoCamisetaRandom() + " " + DataGenerationHelper.getVersionCamisetaRandom();
            oModalidadRepository.save(new ModalidadEntity(nombreModalidad));
        }
        return oModalidadRepository.count();
    }
    
    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oModalidadRepository.deleteAll();
        oModalidadRepository.resetAutoIncrement();
        oModalidadRepository.flush();
        return oModalidadRepository.count();
    }
}
