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
        return oCarritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Carrito no encontrado."));
    }

    public Page<CarritoEntity> getCarritoByUsuario(Long usuario_id, Pageable oPageable) {
       // oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCarritoRepository.findByUsuarioId(usuario_id, oPageable);
    }

    public CarritoEntity getCarritoByUsuarioAndCamiseta(Long usuario_id, Long camiseta_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCarritoRepository.findByUsuarioIdAndCamisetaId(usuario_id, camiseta_id)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Carrito no encontrado."));
    }

    public Page<CarritoEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oCarritoRepository.findAll(oPageable);
    }

    @Transactional
    public Long create(CarritoEntity oCarritoEntity) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(oSessionService.getSessionUser().getId());
       
             UsuarioEntity oUsuarioEntity = oSessionService.getSessionUser();

            // UsuarioEntity oUsuarioEntity =
            // oUsuarioService.get(oCarritoEntity.getUsuario().getId());
            CamisetaEntity oCamisetaEntity = oCamisetaService.get(oCarritoEntity.getCamiseta().getId());

            Optional<CarritoEntity> carritoBaseDatos = oCarritoRepository
                    .findByUsuarioIdAndCamisetaId(oUsuarioEntity.getId(), oCamisetaEntity.getId());
            if (carritoBaseDatos.isPresent()) {
                CarritoEntity carrito = carritoBaseDatos.get();
                carrito.setCantidad(carrito.getCantidad() + oCarritoEntity.getCantidad());
                oCarritoRepository.save(carrito);
                oCamisetaService.actualizarStock(oCamisetaEntity, carrito.getCantidad());
                return carrito.getId();
            } else {
                oCarritoEntity.setId(null);
                oCarritoEntity.setUsuario(oUsuarioEntity);
                oCarritoEntity.setCamiseta(oCamisetaEntity);
                oCamisetaService.actualizarStock(oCamisetaEntity, oCarritoEntity.getCantidad());
                oCarritoEntity.setNombre(oCarritoEntity.getNombre());
                oCarritoEntity.setDorsal(oCarritoEntity.getDorsal());
                return oCarritoRepository.save(oCarritoEntity).getId();
            }
        
    }

   @Transactional
    public CarritoEntity update(CarritoEntity oCarritoEntity) {
        CarritoEntity carritoBaseDatos = this.get(oCarritoEntity.getId());
        oSessionService.onlyAdminsOUsuariosConSusDatos(carritoBaseDatos.getUsuario().getId());

        int diferenciaCantidad = oCarritoEntity.getCantidad() - carritoBaseDatos.getCantidad();

        oCarritoEntity.setUsuario(carritoBaseDatos.getUsuario());
        oCarritoEntity.setCamiseta(carritoBaseDatos.getCamiseta());

        CarritoEntity carritoActualizado = oCarritoRepository.save(oCarritoEntity);

        oCamisetaService.actualizarStock(carritoBaseDatos.getCamiseta(), diferenciaCantidad);

        return oCarritoRepository.save(carritoActualizado);
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

    public Long eliminarCamisetaCarrito(Long id) {
        CarritoEntity carritoBaseDatos = this.get(id);
        oSessionService.onlyAdminsOUsuariosConSusDatos(carritoBaseDatos.getUsuario().getId());

        if (oCarritoRepository.existsById(id)) {
            oCarritoRepository.deleteById(id);
            oCamisetaService.actualizarStock(carritoBaseDatos.getCamiseta(), -(carritoBaseDatos.getCantidad()));
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

    @Transactional
    public void eliminarTodasCamisetasCarrito(Long usuario_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        List<CarritoEntity> carritos = oCarritoRepository.findByUsuarioId(usuario_id, Pageable.unpaged()).getContent();
        for (CarritoEntity carrito: carritos) {
            oCamisetaService.actualizarStock(carrito.getCamiseta(), -(carrito.getCantidad()));
            oCarritoRepository.delete(carrito);
        }
    }

    

    public Long populate(int amount) {
        // oSessionService.onlyAdmins();
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
