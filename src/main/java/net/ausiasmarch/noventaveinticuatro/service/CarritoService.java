package net.ausiasmarch.noventaveinticuatro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.CarritoEntity;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.repository.CarritoRepository;

@Service
public class CarritoService {
    
    @Autowired
    CarritoRepository oCarritoRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    CamisetaService oCamisetaService;

    @Autowired
    UsuarioService oUsuarioService;

   public CarritoEntity get(Long id) {
        return oCarritoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Carrito no encontrado."));
   }

   public List<CarritoEntity> getCarritoByUsuario(Long usuario_id) {
        return oCarritoRepository.findByUsuarioId(usuario_id);
   }

   public CarritoEntity getCarritoByUsuarioAndCamiseta(Long usuario_id, Long camiseta_id) {
        return oCarritoRepository.findByUsuarioIdAndCamisetaId(usuario_id, camiseta_id).orElseThrow(() -> new ResourceNotFoundException("Error: Carrito no encontrado."));
   }

   public Page<CarritoEntity> getPage(Pageable oPageable) {
        return oCarritoRepository.findAll(oPageable);
   }

   public Long create(CarritoEntity oCarritoEntity) {
       UsuarioEntity oUsuarioEntity = oUsuarioService.get(oCarritoEntity.getUsuario().getId());
       CamisetaEntity oCamisetaEntity = oCamisetaService.get(oCarritoEntity.getCamiseta().getId());

       Optional<CarritoEntity> carritoBaseDatos = oCarritoRepository.findByUsuarioIdAndCamisetaId(oUsuarioEntity.getId(), oCamisetaEntity.getId());
         if (carritoBaseDatos.isPresent()) {
              CarritoEntity carrito = carritoBaseDatos.get();
              carrito.setCantidad(carrito.getCantidad() + oCarritoEntity.getCantidad());
              carrito.setPrecio(carrito.getCantidad() * carrito.getCamiseta().getPrecio());
              return carrito.getId();
         } else {
              oCarritoEntity.setId(null);
              oCarritoEntity.setUsuario(oUsuarioEntity);
              oCarritoEntity.setCamiseta(oCamisetaEntity);
              oCarritoEntity.setPrecio(oCarritoEntity.getCantidad() * oCarritoEntity.getCamiseta().getPrecio());
              return oCarritoRepository.save(oCarritoEntity).getId();
         }
   }

    public CarritoEntity update(CarritoEntity oCarritoEntity) {
        CarritoEntity carritoBaseDatos = this.get(oCarritoEntity.getId());
        oCarritoEntity.setUsuario(carritoBaseDatos.getUsuario());
        oCarritoEntity.setCamiseta(carritoBaseDatos.getCamiseta());

        int nuevaCantidad = oCarritoEntity.getCantidad();
        double precioUnitario = oCarritoEntity.getCamiseta().getPrecio();
        oCarritoEntity.setPrecio(nuevaCantidad * precioUnitario);
       
        return oCarritoRepository.save(oCarritoEntity);
    }

    public Long delete(Long id) {
        if (oCarritoRepository.existsById(id)) {
            oCarritoRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: El carrito no existe.");
        }
    }

    public void deleteByUsuario(Long usuario_id) {
        oCarritoRepository.deleteByUsuarioId(usuario_id);
    }

    public List<CarritoEntity> getCarritosUsuario(Long usuario_id) {
        return oCarritoRepository.findByUsuarioId(usuario_id);
    }

    @Transactional
    public Long empty() {
        oCarritoRepository.deleteAll();
        oCarritoRepository.resetAutoIncrement();
        oCarritoRepository.flush();
        return oCarritoRepository.count();
    }











  


}
