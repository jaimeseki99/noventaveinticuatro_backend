package net.ausiasmarch.noventaveinticuatro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.CarritoEntity;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    List<CarritoEntity> findByUsuarioId(Long usuario_id);

    Optional<CarritoEntity> findByUsuarioIdAndCamisetaId(Long usuario_id, Long camiseta_id);

    @Query(value = "SELECT c.cantidad * (c.camiseta.precio - (c.camiseta.precio * c.camiseta.porcentaje_descuento / 100.0)) FROM carrito c WHERE c.id = ?1", nativeQuery = true)
    Double calcularCosteCarrito(Long id);

    @Query(value = "SELECT SUM(c.cantidad * (c.camiseta.precio - (c.camiseta.precio * c.camiseta.porcentaje_descuento / 100.0)) FROM carrito c WHERE c.usuario_id = ?1", nativeQuery = true)
    Double calcularCosteTotalCarrito(Long usuario_id);

    @Query(value = "DELETE FROM carrito WHERE usuario_id = ?1", nativeQuery = true)
    void deleteByUsuarioId(Long usuario_id);

    @Modifying
    @Query(value = "ALTER TABLE carrito AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    
} 
