package net.ausiasmarch.noventaveinticuatro.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.DetalleCompraEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.repository.DetalleCompraRepository;

@Service
public class DetalleCompraService {

    @Autowired
    DetalleCompraRepository oDetalleCompraRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public DetalleCompraEntity get(Long id) {
        return oDetalleCompraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: DetalleCompra no encontrado."));
    }

    public DetalleCompraEntity create(DetalleCompraEntity oDetalleCompraEntity) {
        oDetalleCompraEntity.setId(null);
        return oDetalleCompraRepository.save(oDetalleCompraEntity);
    }

    public DetalleCompraEntity update(DetalleCompraEntity oDetalleCompraEntity) {
        if (oDetalleCompraEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: El detalleCompra no existe.");
        } else {
            return oDetalleCompraRepository.save(oDetalleCompraEntity);
        }
    }

    public Long delete(Long id) {
        if (oDetalleCompraRepository.existsById(id)) {
            oDetalleCompraRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: El detalleCompra no existe.");
        }
    }

    public Page<DetalleCompraEntity> getPage(Long compra_id, Long camiseta_id, Pageable oPageable) {
        if (compra_id != null && camiseta_id != null) {
            return oDetalleCompraRepository.findByCompraIdAndCamisetaId(compra_id, camiseta_id, oPageable);
        } else if (compra_id != null) {
            return oDetalleCompraRepository.findByCompraId(compra_id, oPageable);
        } else if (camiseta_id != null) {
            return oDetalleCompraRepository.findByCamisetaId(camiseta_id, oPageable);
        } else {
            return oDetalleCompraRepository.findAll(oPageable);
        }
    }

    @Transactional
    public Long empty() {
        oDetalleCompraRepository.deleteAll();
        oDetalleCompraRepository.resetAutoIncrement();
        oDetalleCompraRepository.flush();
        return oDetalleCompraRepository.count();
    }



 
    
    
    
}


