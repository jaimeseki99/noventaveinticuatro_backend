package net.ausiasmarch.noventaveinticuatro.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByUsername(String username);

    Optional<UsuarioEntity> findByUsernameAndContrasenya(String username, String contrasenya);

    Optional<UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findByTokenContrasenya(String tokenContrasenya);

    @Query(value = "SELECT u.*, count co(co.id) FROM usuario u, compra co WHERE u.id = co.usuario_id GROUP BY u.id ORDER BY COUNT(u.id) desc", nativeQuery = true)
    Page<UsuarioEntity> findUsuariosMasCompras(Pageable pageable);

    @Query(value = "SELECT u.*, count v(v.id) FROM usuario u, valoracion v WHERE u.id = v.usuario_id GROUP BY u.id ORDER BY COUNT(u.id) desc", nativeQuery = true)
    Page<UsuarioEntity> findUsuariosMasValoraciones(Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE usuario AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}