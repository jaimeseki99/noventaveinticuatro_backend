package net.ausiasmarch.noventaveinticuatro.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.CarritoEntity;
import net.ausiasmarch.noventaveinticuatro.entity.CompraEntity;
import net.ausiasmarch.noventaveinticuatro.entity.DetalleCompraEntity;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.repository.CompraRepository;
import net.ausiasmarch.noventaveinticuatro.repository.DetalleCompraRepository;

@Service
public class CompraService {
    
    @Autowired
    CarritoService oCarritoService;

    @Autowired
    DetalleCompraService oDetalleCompraService;

    @Autowired
    DetalleCompraRepository oDetalleCompraRepository;

    @Autowired
    CompraRepository oCompraRepository;

    @Autowired
    CamisetaService oCamisetaService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    SessionService oSessionService;

    public CompraEntity get(Long id) {
        return oCompraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Compra no encontrada."));
    }

    public String generarCodigoPedido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String fechaActual = LocalDateTime.now().format(formatter);
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
        return fechaActual + uuid;
    }

    @Transactional
    public CompraEntity realizarCompraProducto(CamisetaEntity oCamisetaEntity, UsuarioEntity oUsuarioEntity, int cantidad) {

        oSessionService.onlyAdminsOUsuariosConSusDatos(oUsuarioEntity.getId());

        CompraEntity oCompraEntity = new CompraEntity();

        oCompraEntity.setUsuario(oUsuarioEntity);
        oCompraEntity.setFecha(LocalDate.now());
        oCompraEntity.setCodigoPedido(generarCodigoPedido());
        oCompraEntity.setPrimeraFechaEntrega(LocalDate.now().plusDays(2));
        oCompraEntity.setSegundaFechaEntrega(LocalDate.now().plusDays(3));
        oCompraEntity.setDireccion(oUsuarioEntity.getDireccion());


        oCompraRepository.save(oCompraEntity);

        DetalleCompraEntity oDetalleCompraEntity = new DetalleCompraEntity();
        oDetalleCompraEntity.setId(null);
        oDetalleCompraEntity.setCamiseta(oCamisetaEntity);
        oDetalleCompraEntity.setCompra(oCompraEntity);
        oDetalleCompraEntity.setCantidad(cantidad);
        oDetalleCompraEntity.setPrecio(oCamisetaEntity.getPrecio());
        oDetalleCompraEntity.setIva(oCamisetaEntity.getIva());
        oDetalleCompraEntity.setPorcentajeDescuento(oCamisetaEntity.getPorcentajeDescuento());
        
        oDetalleCompraRepository.save(oDetalleCompraEntity);

        oCamisetaService.actualizarStock(oCamisetaEntity, cantidad);

        return oCompraEntity;
    }

    @Transactional
    public CompraEntity realizarCompraUnicoCarrito(CarritoEntity oCarritoEntity, UsuarioEntity oUsuarioEntity) {

       oSessionService.onlyAdminsOUsuariosConSusDatos(oUsuarioEntity.getId());

        CompraEntity oCompraEntity = new CompraEntity();

        oCompraEntity.setUsuario(oUsuarioEntity);
        oCompraEntity.setFecha(LocalDate.now());
        oCompraEntity.setCodigoPedido(generarCodigoPedido());
        oCompraEntity.setPrimeraFechaEntrega(LocalDate.now().plusDays(2));
        oCompraEntity.setSegundaFechaEntrega(LocalDate.now().plusDays(3));
        oCompraEntity.setDireccion(oUsuarioEntity.getDireccion());

        oCompraRepository.save(oCompraEntity);

        DetalleCompraEntity oDetalleCompraEntity = new DetalleCompraEntity();
        oDetalleCompraEntity.setId(null);
        oDetalleCompraEntity.setCamiseta(oCarritoEntity.getCamiseta());
        oDetalleCompraEntity.setCompra(oCompraEntity);
        oDetalleCompraEntity.setCantidad(oCarritoEntity.getCantidad());
        oDetalleCompraEntity.setPrecio(oCarritoEntity.getCamiseta().getPrecio());
        oDetalleCompraEntity.setIva(oCarritoEntity.getCamiseta().getIva());
        oDetalleCompraEntity.setPorcentajeDescuento(oCarritoEntity.getCamiseta().getPorcentajeDescuento());
        oDetalleCompraEntity.setNombre(oCarritoEntity.getNombre());
        oDetalleCompraEntity.setDorsal(oCarritoEntity.getDorsal());
       
        oDetalleCompraRepository.save(oDetalleCompraEntity);

        // CamisetaEntity camiseta = oCarritoEntity.getCamiseta();
        // oCamisetaService.actualizarStock(camiseta, oCarritoEntity.getCantidad());

        oCarritoService.delete(oCarritoEntity.getId());

        return oCompraEntity;
    }


    @Transactional
    public CompraEntity realizarCompraTodosCarritos(Page<CarritoEntity> carritos, UsuarioEntity oUsuarioEntity) {

       oSessionService.onlyAdminsOUsuariosConSusDatos(oUsuarioEntity.getId());

        CompraEntity oCompraEntity = new CompraEntity();

        oCompraEntity.setUsuario(oUsuarioEntity);
        oCompraEntity.setFecha(LocalDate.now());
        oCompraEntity.setCodigoPedido(generarCodigoPedido());
        oCompraEntity.setPrimeraFechaEntrega(LocalDate.now().plusDays(2));
        oCompraEntity.setSegundaFechaEntrega(LocalDate.now().plusDays(3));
        oCompraEntity.setDireccion(oUsuarioEntity.getDireccion());

        oCompraRepository.save(oCompraEntity);
        
        carritos = oCarritoService.getCarritoByUsuario(oUsuarioEntity.getId(), PageRequest.of(0, 1000));

        carritos.forEach(carrito -> {
            DetalleCompraEntity oDetalleCompraEntity = new DetalleCompraEntity();
            oDetalleCompraEntity.setId(null);
            oDetalleCompraEntity.setCamiseta(carrito.getCamiseta());
            oDetalleCompraEntity.setCompra(oCompraEntity);
            oDetalleCompraEntity.setCantidad(carrito.getCantidad());
            oDetalleCompraEntity.setPrecio(carrito.getCamiseta().getPrecio());
            oDetalleCompraEntity.setIva(carrito.getCamiseta().getIva());
            oDetalleCompraEntity.setPorcentajeDescuento(carrito.getCamiseta().getPorcentajeDescuento());
            oDetalleCompraEntity.setNombre(carrito.getNombre());
            oDetalleCompraEntity.setDorsal(carrito.getDorsal());
            oDetalleCompraRepository.save(oDetalleCompraEntity);

            // CamisetaEntity camiseta = carrito.getCamiseta();
            // oCamisetaService.actualizarStock(camiseta, carrito.getCantidad());
        });

        oCarritoService.deleteByUsuario(oUsuarioEntity.getId());

        return oCompraEntity;

    }

    @Transactional
    public Long cancelarCompra(Long id) {
        CompraEntity compra = oCompraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Compra no encontrada."));
        oSessionService.onlyAdminsOUsuariosConSusDatos(compra.getUsuario().getId());
        if (oCompraRepository.existsById(id)) {
            Page<DetalleCompraEntity> detallesCompra = oDetalleCompraRepository.findByCompraId(id, PageRequest.of(0, 1000));
            for (DetalleCompraEntity detalleCompra : detallesCompra) {
                CamisetaEntity camiseta = detalleCompra.getCamiseta();
                int cantidad = detalleCompra.getCantidad();
                oCamisetaService.actualizarStock(camiseta, -cantidad);
            }
            oDetalleCompraRepository.deleteAll(detallesCompra);
            oCompraRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: La compra no existe.");
        }
    }

    public Page<CompraEntity> getPage(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        if (usuario_id != null) {
            return oCompraRepository.findByUsuarioId(usuario_id, oPageable);
        } else {
            return oCompraRepository.findAll(oPageable);
        }
    }

    public CompraEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oCompraRepository.count()), 1);
        return oCompraRepository.findAll(oPageable).getContent().get(0);
    }

    // Encontrar a las compras más recientes
    public Page<CompraEntity> getComprasMasRecientes(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oCompraRepository.findComprasMasRecientes(oPageable);
    }

    // Encontrar a las compras más antiguas
    public Page<CompraEntity> getComprasMasAntiguas(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oCompraRepository.findComprasMasAntiguas(oPageable);
    }

    // Encontrar a las compras de un usuario
    public Page<CompraEntity> getComprasUsuario(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCompraRepository.findByUsuarioId(usuario_id, oPageable);
    }

    // Encontrar a las compras más caras de un usuario
    public Page<CompraEntity> getComprasMasCarasByUsuarioId(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCompraRepository.findComprasMasCarasByUsuarioId(usuario_id, oPageable);
    }

    // Encontrar a las compras más baratas de un usuario
    public Page<CompraEntity> getComprasMasBaratasByUsuarioId(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oCompraRepository.findComprasMasBaratasByUsuarioId(usuario_id, oPageable);
    }

    public Page<CompraEntity> getCompraPorCodigoPedido(String codigo_pedido, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oCompraRepository.findByCodigoPedido(codigo_pedido, oPageable);
    }

    public Double getPrecioCompra(Long compra_id) {
        return oCompraRepository.findTotalCompraById(compra_id);
    }

    public Long populate(int amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            UsuarioEntity usuario = oUsuarioService.getOneRandom();
            LocalDate fecha = LocalDate.now();
            String codigoPedido = generarCodigoPedido();
            oCompraRepository.save(new CompraEntity(usuario, fecha, codigoPedido));
        }
        return oCompraRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oCompraRepository.deleteAll();
        oCompraRepository.resetAutoIncrement();
        oCompraRepository.flush();
        return oCompraRepository.count();
    }


}
