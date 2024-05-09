package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;

public interface CamisetaRepository extends JpaRepository<CamisetaEntity, Long> {

    Page<CamisetaEntity> findByEquipoId(Long equipo_id, Pageable pageable);

    Page<CamisetaEntity> findByModalidadId(Long modalidad_id, Pageable pageable);

    Page<CamisetaEntity> findByEquipoIdAndModalidadId(Long equipo_id, Long modalidad_id, Pageable pageable);

    Page<CamisetaEntity> findByEquipoIdAndLigaId(Long equipo_id, Long liga_id, Pageable pageable);

    Page<CamisetaEntity> findByModalidadIdAndLigaId(Long modalidad_id, Long liga_id, Pageable pageable);

    Page<CamisetaEntity> findByEquipoIdAndModalidadIdAndLigaId(Long equipo_id, Long modalidad_id, Long liga_id, Pageable pageable);

    Page<CamisetaEntity> findByLigaId(Long liga_id, Pageable pageable);

    @Query(value = "SELECT c.* FROM camiseta c INNER JOIN detalle_compra dc ON c.id = dc.camiseta_id INNER JOIN compra co ON dc.compra_id = co.id GROUP BY c.id ORDER BY SUM(dc.cantidad) DESC", nativeQuery = true)
    Page<CamisetaEntity> findCamisetasMasVendidas(Pageable pageable);

    
    Page<CamisetaEntity> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    @Query(value = "SELECT * FROM camiseta WHERE descuento = true ORDER BY porcentaje_descuento DESC", nativeQuery = true)
    Page<CamisetaEntity> findCamisetasConDescuento(Pageable pageable);  

    @Query(value = "SELECT c.precio + (c.precio * c.iva / 100) - (c.precio * c.porcentaje_descuento / 100) FROM camiseta c WHERE c.id = ?1", nativeQuery = true)
    Double getPrecioTotal(Long id);

    @Query(value = "SELECT * FROM camiseta c WHERE c.id IN (SELECT dc.camiseta_id FROM detalle_compra dc WHERE dc.compra_id IN (SELECT co.id FROM compra co WHERE co.usuario_id = ?1))", nativeQuery = true)
    Page<CamisetaEntity> findCamisetasCompradasByUsuario(Long usuario_id, Pageable pageable);

    @Modifying
    @Query(value ="ALTER TABLE camiseta AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    
}