package net.ausiasmarch.noventaveinticuatro.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.entity.ValoracionEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.ValoracionRepository;

@Service
public class ValoracionService {

    @Autowired
    ValoracionRepository oValoracionRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    CamisetaService oCamisetaService;

    @Autowired
    SessionService oSessionService;

    public ValoracionEntity get(Long id) {
        return oValoracionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Valoracion no encontrado."));
    }

    public Long create(ValoracionEntity oValoracionEntity) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(oValoracionEntity.getUsuario().getId());
        Optional<ValoracionEntity> valoracionBaseDatos = oValoracionRepository.findByCamisetaIdAndUsuarioId(oValoracionEntity.getCamiseta().getId(), oValoracionEntity.getUsuario().getId());
        if (valoracionBaseDatos.isPresent()) {
            ValoracionEntity valoracion = valoracionBaseDatos.get();
            valoracion.setComentario(oValoracionEntity.getComentario());
            valoracion.setFecha(LocalDate.now());
            return oValoracionRepository.save(valoracion).getId();
        } else {
            oValoracionEntity.setId(null);
            oValoracionEntity.setFecha(LocalDate.now());
            return oValoracionRepository.save(oValoracionEntity).getId();
        }
    }

    public ValoracionEntity update(ValoracionEntity oValoracionEntity) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(oValoracionEntity.getUsuario().getId());
        if (oValoracionEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: La valoracion no existe.");
        } else {
            oValoracionEntity.setFecha(LocalDate.now());
            return oValoracionRepository.save(oValoracionEntity);
        }
    }

    public Long delete(Long id) {
        ValoracionEntity oValoracionEntity = this.get(id);
        oSessionService.onlyAdminsOUsuariosConSusDatos(oValoracionEntity.getUsuario().getId());
        if (oValoracionRepository.existsById(id)) {
            oValoracionRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: La valoracion no existe.");
        }
    }

    public ValoracionEntity getOneRandom() {
         Pageable oPageable = PageRequest.of((int) (Math.random() * oValoracionRepository.count()), 1);
        return oValoracionRepository.findAll(oPageable).getContent().get(0);
    }

    public Optional<ValoracionEntity> getValoracionByUsuarioAndCamiseta(Long camiseta_id, Long usuario_id) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oValoracionRepository.findByCamisetaIdAndUsuarioId(camiseta_id, usuario_id);
    }

    public Page<ValoracionEntity> getPage(Pageable oPageable, Long usuario_id, Long camiseta_id) {
        //oSessionService.onlyAdminsOUsuarios();
        if (usuario_id == 0) {
            if ( camiseta_id == 0) {
                return oValoracionRepository.findAll(oPageable);
            } else {
                return oValoracionRepository.findByCamisetaId(camiseta_id, oPageable);
            }
        } else {
            return oValoracionRepository.findByUsuarioId(usuario_id, oPageable);
        }

    }

    public Page<ValoracionEntity> getPageByCamisetaId(Long camiseta_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oValoracionRepository.findByCamisetaId(camiseta_id, oPageable);
    }

    public Page<ValoracionEntity> getPageByUsuarioId(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oValoracionRepository.findByUsuarioId(usuario_id, oPageable);
    }

    public Page<ValoracionEntity> getPageMasRecientes(Long camiseta_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oValoracionRepository.getValoracionesMasRecientes(camiseta_id, oPageable);
    }

    public Page<ValoracionEntity> getPageMasAntiguas(Long camiseta_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuarios();
        return oValoracionRepository.getValoracionesMasAntiguas(camiseta_id, oPageable);
    }

    public Page<ValoracionEntity> getPageMasRecientesByUsuarioId(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oValoracionRepository.getValoracionesMasRecientesByUsuarioId(usuario_id, oPageable);
    }

    public Page<ValoracionEntity> getPageMasAntiguasByUsuarioId(Long usuario_id, Pageable oPageable) {
        oSessionService.onlyAdminsOUsuariosConSusDatos(usuario_id);
        return oValoracionRepository.getValoracionesMasAntiguasByUsuarioId(usuario_id, oPageable);
    }

    public Long populate(int amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i<amount; i++) {
            String comentario = DataGenerationHelper.generarComentarioRandom();
            LocalDate fecha = LocalDate.now();
            UsuarioEntity usuario = oUsuarioService.getOneRandom();
            CamisetaEntity camiseta = oCamisetaService.getOneRandom();
            oValoracionRepository.save(new ValoracionEntity(comentario, fecha, usuario, camiseta));
        }
        return oValoracionRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oValoracionRepository.deleteAll();
        oValoracionRepository.resetAutoIncrement();
        oValoracionRepository.flush();
        return oValoracionRepository.count();
    }


    
}
