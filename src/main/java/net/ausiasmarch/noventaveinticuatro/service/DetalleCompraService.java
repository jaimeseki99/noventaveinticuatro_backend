package net.ausiasmarch.noventaveinticuatro.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.CompraEntity;
import net.ausiasmarch.noventaveinticuatro.entity.DetalleCompraEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.DetalleCompraRepository;

@Service
public class DetalleCompraService {

    @Autowired
    DetalleCompraRepository oDetalleCompraRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    
    @Autowired
    CamisetaService oCamisetaService;

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

   public Page<DetalleCompraEntity> getPage(Long camiseta_id, Long compra_id, Pageable oPageable) {
        if (camiseta_id != null) {
            return oDetalleCompraRepository.findByCamisetaId(camiseta_id, oPageable);
        } else if (compra_id != null) {
            return oDetalleCompraRepository.findByCompraId(compra_id, oPageable);
        } else {
            return oDetalleCompraRepository.findAll(oPageable);
        }
    }

    public Optional<DetalleCompraEntity> getOneByCompraIdAndCamisetaId(Long compra_id, Long camiseta_id) {
        return oDetalleCompraRepository.findByCompraIdAndCamisetaId(compra_id, camiseta_id);
    }

    public DetalleCompraEntity getOneRandom() {
        Pageable oPageable = Pageable.ofSize(1);
        return oDetalleCompraRepository.findAll(oPageable).getContent().get(0);
    }

   // public Long populate(int amount) {
   //     for (int i=0; i<amount; i++) {
   //         CompraEntity compra = oCompraService.getOneRandom();
   //         CamisetaEntity camiseta = oCamisetaService.getOneRandom();
   //         double precio = camiseta.getPrecio();
   //         int cantidad = DataGenerationHelper.generarIntRandom();
   //         double iva = camiseta.getIva();
   //         oDetalleCompraRepository.save(new DetalleCompraEntity(compra, camiseta, precio, cantidad, iva));
   //     }
   //     return oDetalleCompraRepository.count();
   // }

    @Transactional
    public Long empty() {
        oDetalleCompraRepository.deleteAll();
        oDetalleCompraRepository.resetAutoIncrement();
        oDetalleCompraRepository.flush();
        return oDetalleCompraRepository.count();
    }



 
    
    
    
}


