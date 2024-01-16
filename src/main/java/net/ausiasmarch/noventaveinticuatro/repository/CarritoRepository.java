package net.ausiasmarch.noventaveinticuatro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.CarritoEntity;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    List<CarritoEntity> findByUsuarioId(Long usuario_id);

    Optional<CarritoEntity> findByUsuarioIdAndCamisetaId(Long usuario_id, Long camiseta_id);

    @Query(value = "DELETE FROM carrito WHERE usuario_id = ?1", nativeQuery = true)
    void deleteByUsuarioId(Long usuario_id);

    @Query(value = "ALTER TABLE carrito AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    
} 