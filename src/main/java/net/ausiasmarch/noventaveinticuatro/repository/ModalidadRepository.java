package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.ModalidadEntity;

public interface ModalidadRepository extends JpaRepository<ModalidadEntity, Long> {

    //query para buscar por el nombre de la modalidad
    @Query(value = "SELECT * FROM modalidad WHERE nombre = ?1", nativeQuery = true)
    public ModalidadEntity findByNombre(String nombre);

    @Query(value = "ALTER TABLE modalidad AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    
} 