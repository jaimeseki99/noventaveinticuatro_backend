package net.ausiasmarch.noventaveinticuatro.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.ValoracionEntity;

public interface ValoracionRepository extends JpaRepository<ValoracionEntity, Long> {

    Page<ValoracionEntity> findByCamisetaId(Long camiseta_id, Pageable pageable);

    Page<ValoracionEntity> findByUsuarioId(Long usuario_id, Pageable pageable);

    Optional<ValoracionEntity> findByUsuarioIdAndCamisetaId(Long usuario_id, Long camiseta_id);
    
    @Query(value = "SELECT * FROM valoracion WHERE camiseta_id = ?1 ORDER BY fecha DESC", nativeQuery = true)
    Page<ValoracionEntity> getValoracionesMasRecientes(Long camiseta_id, Pageable pageable);

    @Query(value = "SELECT * FROM valoracion WHERE camiseta_id = ?1 ORDER BY fecha ASC", nativeQuery = true)
    Page<ValoracionEntity> getValoracionesMasAntiguas(Long camiseta_id, Pageable pageable);

    @Query(value = "SELECT * FROM valoracion WHERE usuario_id = ?1 ORDER BY fecha DESC", nativeQuery = true)
    Page<ValoracionEntity> getValoracionesMasRecientesByUsuarioId(Long usuario_id, Pageable pageable);

    @Query(value = "SELECT * FROM valoracion WHERE usuario_id = ?1 ORDER BY fecha ASC", nativeQuery = true)
    Page<ValoracionEntity> getValoracionesMasAntiguasByUsuarioId(Long usuario_id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE valoracion AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
