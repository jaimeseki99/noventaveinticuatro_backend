package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.CompraEntity;

public interface CompraRepository extends JpaRepository<CompraEntity, Long>{

    Page<CompraEntity> findByUsuarioId(Long usuario_id, Pageable pageable);

    @Query(value = "SELECT * FROM compra ORDER BY coste_total DESC ", nativeQuery = true)
    Page<CompraEntity> findComprasMasCaras(Pageable pageable);
    
    @Query(value = "SELECT * FROM compra ORDER BY coste_total ASC ", nativeQuery = true)
    Page<CompraEntity> findComprasMasBaratas(Pageable pageable);

    @Query(value = "SELECT * FROM compra ORDER BY fecha ASC", nativeQuery = true)
    Page<CompraEntity> findComprasMasAntiguas(Pageable pageable);
    
    @Query(value = "SELECT * FROM compra ORDER BY fecha DESC", nativeQuery = true)
    Page<CompraEntity> findComprasMasRecientes(Pageable pageable);

    @Query(value = "SELECT * FROM compra WHERE codigo_pedido LIKE %?1%", nativeQuery = true)
    Page<CompraEntity> findByCodigoPedido(String codigo_pedido, Pageable pageable);

    @Query(value = "SELECT * FROM compra WHERE usuario_id = ?1 ORDER BY coste_total DESC ", nativeQuery = true)
    Page<CompraEntity> findComprasMasCarasByUsuarioId(Long usuario_id, Pageable pageable);

    @Query(value = "SELECT * FROM compra WHERE usuario_id = ?1 ORDER BY coste_total ASC ", nativeQuery = true)
    Page<CompraEntity> findComprasMasBaratasByUsuarioId(Long usuario_id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE compra AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
