package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.CompraEntity;

public interface CompraRepository extends JpaRepository<CompraEntity, Long>{

    Page<CompraEntity> findByUsuarioId(Long usuario_id, Pageable pageable);


    @Query(value = "SELECT * FROM compra ORDER BY fecha ASC", nativeQuery = true)
    Page<CompraEntity> findComprasMasAntiguas(Pageable pageable);
    
    @Query(value = "SELECT * FROM compra ORDER BY fecha DESC", nativeQuery = true)
    Page<CompraEntity> findComprasMasRecientes(Pageable pageable);

    @Query(value = "SELECT * FROM compra WHERE codigo_pedido LIKE %?1%", nativeQuery = true)
    Page<CompraEntity> findByCodigoPedido(String codigo_pedido, Pageable pageable);

    @Query(value = "SELECT SUM(dc.precio * dc.cantidad) FROM compra c, detalle_compra dc WHERE c.id = dc.compra_id AND c.usuario_id = ?1", nativeQuery = true)
    Double findTotalComprasByUsuarioId(Long usuario_id);

    @Query(value = "SELECT SUM(dc.precio * dc.cantidad) FROM detalle_compra dc WHERE dc.compra_id = ?1", nativeQuery = true)
    Double findTotalCompraById(Long id);

    @Query(value = "SELECT SUM(dc.precio * dc.cantidad) FROM detalle_compra dc, compra c WHERE dc.compra_id = c.id AND c.usuario_id = ?1 AND c.id = ?2", nativeQuery = true)
    Double findTotalCompraByUsuarioIdAndCompraId(Long usuario_id, Long compra_id);

    @Query(value = "SELECT * FROM compra WHERE usuario_id = ?1 ORDER BY (SELECT SUM(dc.precio * dc.cantidad) FROM detalle_compra dc WHERE dc.compra_id = compra.id) DESC", nativeQuery = true)
    Page<CompraEntity> findComprasMasCarasByUsuarioId(Long usuario_id, Pageable pageable);

    @Query(value = "SELECT * FROM compra WHERE usuario_id = ?1 ORDER BY (SELECT SUM(dc.precio * dc.cantidad) FROM detalle_compra dc WHERE dc.compra_id = compra.id) ASC", nativeQuery = true)
    Page<CompraEntity> findComprasMasBaratasByUsuarioId(Long usuario_id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE compra AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
