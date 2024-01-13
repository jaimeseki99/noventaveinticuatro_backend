package net.ausiasmarch.noventaveinticuatro.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.ValoracionEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.repository.ValoracionRepository;

@Service
public class ValoracionService {

    @Autowired
    ValoracionRepository oValoracionRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public ValoracionEntity get(Long id) {
        return oValoracionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Valoracion no encontrado."));
    }

    public Long create(ValoracionEntity oValoracionEntity) {
        Optional<ValoracionEntity> valoracionBaseDatos = oValoracionRepository.findByCamisetaIdAndUsuarioId(oValoracionEntity.getCamiseta().getId(), oValoracionEntity.getUsuario().getId());
        if (valoracionBaseDatos.isPresent()) {
            ValoracionEntity valoracion = valoracionBaseDatos.get();
            valoracion.setComentario(oValoracionEntity.getComentario());
            valoracion.setFecha(LocalDateTime.now());
            return oValoracionRepository.save(valoracion).getId();
        } else {
            oValoracionEntity.setId(null);
            oValoracionEntity.setFecha(LocalDateTime.now());
            return oValoracionRepository.save(oValoracionEntity).getId();
        }
    }

    public ValoracionEntity update(ValoracionEntity oValoracionEntity) {
        if (oValoracionEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: La valoracion no existe.");
        } else {
            return oValoracionRepository.save(oValoracionEntity);
        }
    }

    public Long delete(Long id) {
        if (oValoracionRepository.existsById(id)) {
            oValoracionRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: La valoracion no existe.");
        }
    }

    public ValoracionEntity getOneRandom() {
         Pageable oPageable = PageRequest.of((int) (Math.random() * oValoracionRepository.count()), 1);
        return oValoracionRepository.findAll(oPageable).getContent().get(0);
    }

    public Optional<ValoracionEntity> getValoracionByUsuarioAndCamiseta(Long camiseta_id, Long usuario_id) {
        return oValoracionRepository.findByCamisetaIdAndUsuarioId(camiseta_id, usuario_id);
    }
    
    public Page<ValoracionEntity> getPage(Pageable oPageable) {
        return oValoracionRepository.findAll(oPageable);
    }

    @Transactional
    public Long empty() {
        oValoracionRepository.deleteAll();
        oValoracionRepository.resetAutoIncrement();
        oValoracionRepository.flush();
        return oValoracionRepository.count();
    }


    
}
