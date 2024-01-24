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

    @Autowired
    SessionService oSessionService;

   public CarritoEntity get(Long id) {
        return oCarritoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Carrito no encontrado."));
   }

   public List<CarritoEntity> getCarritoByUsuario(Long usuario_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCarritoRepository.findByUsuarioId(usuario_id);
   }

   public CarritoEntity getCarritoByUsuarioAndCamiseta(Long usuario_id, Long camiseta_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCarritoRepository.findByUsuarioIdAndCamisetaId(usuario_id, camiseta_id).orElseThrow(() -> new ResourceNotFoundException("Error: Carrito no encontrado."));
   }

   public Page<CarritoEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oCarritoRepository.findAll(oPageable);
   }

   public Long create(CarritoEntity oCarritoEntity) {
       oSessionService.onlyAdminsOUsuariosConSusDatos(oCarritoEntity.getUsuario().getId());
       UsuarioEntity oUsuarioEntity = oUsuarioService.get(oCarritoEntity.getUsuario().getId());
       CamisetaEntity oCamisetaEntity = oCamisetaService.get(oCarritoEntity.getCamiseta().getId());

       Optional<CarritoEntity> carritoBaseDatos = oCarritoRepository.findByUsuarioIdAndCamisetaId(oUsuarioEntity.getId(), oCamisetaEntity.getId());
         if (carritoBaseDatos.isPresent()) {
              CarritoEntity carrito = carritoBaseDatos.get();
              carrito.setCantidad(carrito.getCantidad() + oCarritoEntity.getCantidad());
              oCarritoRepository.save(carrito);
              return carrito.getId();
         } else {
              oCarritoEntity.setId(null);
              oCarritoEntity.setUsuario(oUsuarioEntity);
              oCarritoEntity.setCamiseta(oCamisetaEntity);
              return oCarritoRepository.save(oCarritoEntity).getId();
         }
   }

    public CarritoEntity update(CarritoEntity oCarritoEntity) {
        CarritoEntity carritoBaseDatos = this.get(oCarritoEntity.getId());
        oSessionService.onlyAdminsOUsuariosConSusDatos(carritoBaseDatos.getUsuario().getId());
        oCarritoEntity.setUsuario(carritoBaseDatos.getUsuario());
        oCarritoEntity.setCamiseta(carritoBaseDatos.getCamiseta());

        return oCarritoRepository.save(oCarritoEntity);
    }

    public Long delete(Long id) {
        CarritoEntity carritoBaseDatos = this.get(id);
        oSessionService.onlyAdminsOUsuariosConSusDatos(carritoBaseDatos.getUsuario().getId());
        if (oCarritoRepository.existsById(id)) {
            oCarritoRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: El carrito no existe.");
        }
    }

    @Transactional
    public void deleteByUsuario(Long usuario_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        oCarritoRepository.deleteByUsuarioId(usuario_id);
    }

    public List<CarritoEntity> getCarritosUsuario(Long usuario_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCarritoRepository.findByUsuarioId(usuario_id);
    }

    public Long populate(int amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            UsuarioEntity usuario = oUsuarioService.getOneRandom();
            CamisetaEntity camiseta = oCamisetaService.getOneRandom();
            int cantidad = (int) (Math.random() * 10) + 1;
            oCarritoRepository.save(new CarritoEntity(usuario, camiseta, cantidad));
        }
        return oCarritoRepository.count();
    }

    public Double calcularCosteCarrito(Long id) {
        return oCarritoRepository.calcularCosteCarrito(id);
    }

    public Double calcularCosteTotalCarrito(Long usuario_id) {
        return oCarritoRepository.calcularCosteTotalCarrito(usuario_id);
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oCarritoRepository.deleteAll();
        oCarritoRepository.resetAutoIncrement();
        oCarritoRepository.flush();
        return oCarritoRepository.count();
    }











  


}
