package net.ausiasmarch.noventaveinticuatro.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.DetalleCompraEntity;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompraEntity, Long> {

    Page<DetalleCompraEntity> findByCompraId(Long compra_id, Pageable pageable);

    Page<DetalleCompraEntity> findByCamisetaId(Long camiseta_id, Pageable pageable);

    Optional<DetalleCompraEntity> findByCompraIdAndCamisetaId(Long compra_id, Long camiseta_id);

    @Query(value = "SELECT * FROM detalle_compra WHERE compra_id = ?1", nativeQuery = true)
    Page<DetalleCompraEntity> findByCompra(Long compra_id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE detalle_compra AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    
} 
