package net.ausiasmarch.noventaveinticuatro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final String contrasenya = "7fc01059913987bd360c1f37d2ae1ff5c4a055473ccb0a7a6ab4bf97967a3821";

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    public UsuarioEntity get(Long id) {
        return oUsuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Usuario no encontrado."));
    }

    public UsuarioEntity getByUsername(String username) {
        return oUsuarioRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Error: Usuario no encontrado."));
    }

    public Long create (UsuarioEntity oUsuarioEntity) {
        oSessionService.onlyAdmins();
        oUsuarioEntity.setId(null);
        oUsuarioEntity.setContrasenya(contrasenya);
        return oUsuarioRepository.save(oUsuarioEntity).getId();
    }

    public UsuarioEntity update(UsuarioEntity oUsuarioEntity) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(oUsuarioEntity.getId());
        UsuarioEntity usuarioBaseDatos = this.get(oUsuarioEntity.getId());
        
        oUsuarioEntity.setContrasenya(usuarioBaseDatos.getContrasenya());
        oUsuarioEntity.setTipo(usuarioBaseDatos.isTipo());
        return oUsuarioRepository.save(oUsuarioEntity);
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        if (oUsuarioRepository.existsById(id)) {
            oUsuarioRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: El usuario no existe.");
        }
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oUsuarioRepository.findAll(oPageable);
    }

    public UsuarioEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oUsuarioRepository.count()), 1);
        return oUsuarioRepository.findAll(oPageable).getContent().get(0);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i=0; i<amount; i++) {
            String nombre = DataGenerationHelper.getNombreRandom();
            String apellido = DataGenerationHelper.getApellidoRandom();
            String username = DataGenerationHelper.doNormalizeString(nombre.substring(0,4) + apellido.substring(0, 3).toLowerCase());
            String email = DataGenerationHelper.doNormalizeString(nombre.substring(0,4) + apellido.substring(0, 3).toLowerCase()) + "@gmail.com";
            String direccion = DataGenerationHelper.generarDireccionRandom();
            String telefono = DataGenerationHelper.generarNumeroTelefono();
            oUsuarioRepository.save(new UsuarioEntity(nombre, apellido, username, email, direccion, telefono, contrasenya, false));
        }
        return oUsuarioRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oUsuarioRepository.deleteAll();
        oUsuarioRepository.resetAutoIncrement();
        UsuarioEntity oUsuarioEntity = new UsuarioEntity(1L, "Jaime", "Serrano", "jaimeseki99", "jaime99sq@gmail.com", "C/La Senyera, 24", "601148404", contrasenya, true);
        oUsuarioRepository.save(oUsuarioEntity);
        oUsuarioEntity = new UsuarioEntity(2L, "Seki", "Morten", "sekimmortenn", "mortensitojsk@gmail.com", "C/ La Piruleta, 666", "666666666", contrasenya, false);
        oUsuarioRepository.save(oUsuarioEntity);
        return oUsuarioRepository.count();
    }

    public Page<UsuarioEntity> getUsuariosMasCompras(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oUsuarioRepository.findUsuariosMasCompras(oPageable);
    }

    public Page<UsuarioEntity> getUsuariosMasValoraciones(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oUsuarioRepository.findUsuariosMasValoraciones(oPageable);
    }
 
}
