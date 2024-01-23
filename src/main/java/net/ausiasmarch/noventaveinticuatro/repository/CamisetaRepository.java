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

    Page<CamisetaEntity> findByLigaId(Long liga_id, Pageable pageable);

    @Query(value = "SELECT c.* FROM camiseta c INNER JOIN detalle_compra dc ON c.id = dc.camiseta_id INNER JOIN compra co ON dc.compra_id = co.id GROUP BY c.id ORDER BY SUM(dc.cantidad) DESC", nativeQuery = true)
    Page<CamisetaEntity> findCamisetasMasVendidas(Pageable pageable);

    @Query(value = "SELECT * FROM camiseta WHERE LENGTH(?1) >=3 AND titulo LIKE %?1%", nativeQuery = true)
    Page<CamisetaEntity> findBySearchIgnoreCase(String searchText, Pageable pageable);

    @Query(value = "SELECT * FROM camiseta WHERE descuento = true ORDER BY porcentaje_descuento DESC", nativeQuery = true)
    Page<CamisetaEntity> findCamisetasConDescuento(Pageable pageable);  

    @Query(value = "SELECT c.precio - (c.precio * c.porcentaje_descuento / 100) FROM camiseta c WHERE c.id = ?1", nativeQuery = true)
    Double getPrecioConDescuento(Long id);

    @Modifying
    @Query(value ="ALTER TABLE camiseta AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    
}