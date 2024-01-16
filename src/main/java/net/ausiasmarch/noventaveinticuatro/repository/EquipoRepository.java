package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.EquipoEntity;

public interface EquipoRepository extends JpaRepository<EquipoEntity, Long> {
    
    Page<EquipoEntity> findByLigaId(Long liga_id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE equipo AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
