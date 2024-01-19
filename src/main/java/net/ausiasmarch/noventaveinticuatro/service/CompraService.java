package net.ausiasmarch.noventaveinticuatro.service;

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

    public CompraEntity get(Long id) {
        return oCompraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Compra no encontrada."));
    }

    public String generarCodigoPedido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaActual = LocalDateTime.now().format(formatter);
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
        return fechaActual + uuid;
    }

    @Transactional
    public CompraEntity realizarCompraUnicoCarrito(CarritoEntity oCarritoEntity, UsuarioEntity oUsuarioEntity) {
        CompraEntity oCompraEntity = new CompraEntity();

        oCompraEntity.setUsuario(oUsuarioEntity);
        oCompraEntity.setFecha(LocalDateTime.now());
        oCompraEntity.setCodigoPedido(generarCodigoPedido());

        oCompraRepository.save(oCompraEntity);

        DetalleCompraEntity oDetalleCompraEntity = new DetalleCompraEntity();
        oDetalleCompraEntity.setId(null);
        oDetalleCompraEntity.setCamiseta(oCarritoEntity.getCamiseta());
        oDetalleCompraEntity.setCompra(oCompraEntity);
        oDetalleCompraEntity.setCantidad(oCarritoEntity.getCantidad());
        oDetalleCompraEntity.setPrecio(oCarritoEntity.getCamiseta().getPrecio());
        oDetalleCompraEntity.setIva(oCarritoEntity.getCamiseta().getIva());
        oDetalleCompraEntity.setPorcentajeDescuento(oCarritoEntity.getCamiseta().getPorcentajeDescuento());
       
        oDetalleCompraRepository.save(oDetalleCompraEntity);

        CamisetaEntity camiseta = oCarritoEntity.getCamiseta();
        oCamisetaService.actualizarStock(camiseta, oCarritoEntity.getCantidad());

        oCarritoService.delete(oCarritoEntity.getId());

        return oCompraEntity;
    }


    @Transactional
    public CompraEntity realizarCompraTodosCarritos(List<CarritoEntity> carritos, UsuarioEntity oUsuarioEntity) {
        CompraEntity oCompraEntity = new CompraEntity();

        oCompraEntity.setUsuario(oUsuarioEntity);
        oCompraEntity.setFecha(LocalDateTime.now());
        oCompraEntity.setCodigoPedido(generarCodigoPedido());

        oCompraRepository.save(oCompraEntity);
        
        carritos = oCarritoService.getCarritoByUsuario(oUsuarioEntity.getId());

        for (CarritoEntity carrito : carritos) {
            DetalleCompraEntity oDetalleCompraEntity = new DetalleCompraEntity();
            oDetalleCompraEntity.setId(null);
            oDetalleCompraEntity.setCamiseta(carrito.getCamiseta());
            oDetalleCompraEntity.setCompra(oCompraEntity);
            oDetalleCompraEntity.setCantidad(carrito.getCantidad());
            oDetalleCompraEntity.setPrecio(carrito.getCamiseta().getPrecio());
            oDetalleCompraEntity.setIva(carrito.getCamiseta().getIva());
            oDetalleCompraEntity.setPorcentajeDescuento(carrito.getCamiseta().getPorcentajeDescuento());
            
            oDetalleCompraRepository.save(oDetalleCompraEntity);
        }

        for (CarritoEntity carrito : carritos) {
            CamisetaEntity camiseta = carrito.getCamiseta();
            oCamisetaService.actualizarStock(camiseta, carrito.getCantidad());
        }

        oCarritoService.deleteByUsuario(oUsuarioEntity.getId());

        return oCompraEntity;

    }

    public Long cancelarCompra(Long id) {
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

    public Page<CompraEntity> getPage(Pageable oPageable) {
        return oCompraRepository.findAll(oPageable);
    }

    public CompraEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oCompraRepository.count()), 1);
        return oCompraRepository.findAll(oPageable).getContent().get(0);
    }

    // Encontrar a las compras más recientes
    public Page<CompraEntity> getComprasMasRecientes(Pageable oPageable) {
        return oCompraRepository.findComprasMasRecientes(oPageable);
    }

    // Encontrar a las compras más antiguas
    public Page<CompraEntity> getComprasMasAntiguas(Pageable oPageable) {
        return oCompraRepository.findComprasMasAntiguas(oPageable);
    }

    // Encontrar a las compras de un usuario
    public Page<CompraEntity> getComprasUsuario(Long usuario_id, Pageable oPageable) {
        return oCompraRepository.findByUsuarioId(usuario_id, oPageable);
    }

    // Encontrar a las compras más caras de un usuario
    public Page<CompraEntity> getComprasMasCarasByUsuarioId(Long usuario_id, Pageable oPageable) {
        return oCompraRepository.findComprasMasCarasByUsuarioId(usuario_id, oPageable);
    }

    // Encontrar a las compras más baratas de un usuario
    public Page<CompraEntity> getComprasMasBaratasByUsuarioId(Long usuario_id, Pageable oPageable) {
        return oCompraRepository.findComprasMasBaratasByUsuarioId(usuario_id, oPageable);
    }

    public Page<CompraEntity> getCompraPorCodigoPedido(String codigo_pedido, Pageable oPageable) {
        return oCompraRepository.findByCodigoPedido(codigo_pedido, oPageable);
    }

    @Transactional
    public Long empty() {
        oCompraRepository.deleteAll();
        oCompraRepository.resetAutoIncrement();
        oCompraRepository.flush();
        return oCompraRepository.count();
    }


}
