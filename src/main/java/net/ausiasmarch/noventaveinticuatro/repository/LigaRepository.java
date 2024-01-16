package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.LigaEntity;

public interface LigaRepository extends JpaRepository<LigaEntity, Long> {

    @Query(value = "SELECT * FROM liga WHERE pais LIKE %?1%", nativeQuery = true)
    Page<LigaEntity> findByPais(String pais, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE liga AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    
}
