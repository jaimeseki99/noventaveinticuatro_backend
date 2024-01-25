package net.ausiasmarch.noventaveinticuatro.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.bean.CaptchaBean;
import net.ausiasmarch.noventaveinticuatro.bean.CaptchaResponseBean;
import net.ausiasmarch.noventaveinticuatro.bean.UsuarioBean;
import net.ausiasmarch.noventaveinticuatro.entity.CaptchaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.PendentEntity;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.exception.UnauthorizedException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.helper.JWTHelper;
import net.ausiasmarch.noventaveinticuatro.repository.CaptchaRepository;
import net.ausiasmarch.noventaveinticuatro.repository.PendentRepository;
import net.ausiasmarch.noventaveinticuatro.repository.UsuarioRepository;

@Service
public class SessionService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    CaptchaService oCaptchaService;

    @Autowired
    PendentRepository oPendentRepository;

    @Autowired
    CaptchaRepository oCaptchaRepository;

    @Transactional
    public CaptchaResponseBean prelogin() {
        CaptchaEntity oCaptchaEntity = oCaptchaService.getRandomCaptcha();

        PendentEntity oPendentEntity = new PendentEntity();
        oPendentEntity.setCaptcha(oCaptchaEntity);
        oPendentEntity.setTimecode(LocalDateTime.now());
        PendentEntity oNewPendentEntity = oPendentRepository.save(oPendentEntity);

        oNewPendentEntity.setToken(DataGenerationHelper.getSHA256(
            String.valueOf(oNewPendentEntity.getId())
            + String.valueOf(oCaptchaEntity.getId())
            + String.valueOf(DataGenerationHelper.getRandomInt(0, 9999))
        ));

        oPendentRepository.save(oNewPendentEntity);

        CaptchaResponseBean oCaptchaResponseBean = new CaptchaResponseBean();
        oCaptchaResponseBean.setToken(oNewPendentEntity.getToken());
        oCaptchaResponseBean.setCaptchaImage(oCaptchaEntity.getImage());

        return oCaptchaResponseBean;

    }

    public String loginCaptcha(@RequestBody CaptchaBean oCaptchaBean) {
        if (oCaptchaBean.getUsername() != null && oCaptchaBean.getContrasenya() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsernameAndContrasenya(oCaptchaBean.getUsername(), oCaptchaBean.getContrasenya()).orElseThrow(() -> new ResourceNotFoundException("Usuario o contraseña incorrectos"));
            if (oUsuarioEntity!=null) {
                PendentEntity oPendentEntity = oPendentRepository.findByToken(oCaptchaBean.getToken()).orElseThrow(() -> new ResourceNotFoundException("Token incorrecto"));

                LocalDateTime timecode = oPendentEntity.getTimecode();

                if (LocalDateTime.now().isAfter(timecode.plusSeconds(180))) {
                    throw new UnauthorizedException("El captcha ha caducado");
                }

                if (oPendentEntity.getCaptcha().getText().equals(oCaptchaBean.getAnswer())) {
                    oPendentRepository.delete(oPendentEntity);
                    return JWTHelper.generateJWT(oCaptchaBean.getUsername());
                } else {
                    throw new UnauthorizedException("Captcha incorrecto");
                }
            } else {
                throw new UnauthorizedException("Usuario o contraseña incorrectos");
            }
        } else {
            throw new UnauthorizedException("Usuario no encontrado");}
    }

    public String login(UsuarioBean oUsuarioBean) {
        oUsuarioRepository.findByUsernameAndContrasenya(oUsuarioBean.getUsername(), oUsuarioBean.getContrasenya()).orElseThrow(() -> new ResourceNotFoundException("Usuario o contraseña incorrectos"));
        return JWTHelper.generateJWT(oUsuarioBean.getUsername());
    }

    public String getSessionUsername() {
        if (oHttpServletRequest.getAttribute("username") instanceof String) {
            return oHttpServletRequest.getAttribute("username").toString();
        } else {
            return null;
        }
    }

    public UsuarioEntity getSessionUser() {
        if (this.getSessionUsername() != null) {
            return oUsuarioRepository.findByUsername(this.getSessionUsername()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        } else {
            return null;
        }
    }

    public boolean isSessionActive() {
        if (this.getSessionUsername() != null) {
            return oUsuarioRepository.findByUsername(this.getSessionUsername()).isPresent();
        } else {
            return false;
        }
    }

    public Boolean isAdmin() {
        if (this.getSessionUsername() != null) {
            UsuarioEntity oUsuarioEntitySesion = oUsuarioRepository.findByUsername(this.getSessionUsername()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            return Boolean.TRUE.equals(oUsuarioEntitySesion.isTipo());
        } else {
            return false;
        }
    }

    public Boolean isUsuario() {
        if (this.getSessionUsername() != null) {
            UsuarioEntity oUsuarioEntitySesion = oUsuarioRepository.findByUsername(this.getSessionUsername()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            return Boolean.FALSE.equals(oUsuarioEntitySesion.isTipo());
        } else {
            return false;
        }
    }

    public void onlyAdmins() {
        if (!this.isAdmin()) {
            throw new UnauthorizedException("Solo los administradores pueden hacer esto");
        }
    }

    public void onlyUsuarios() {
        if (!this.isUsuario()) {
            throw new UnauthorizedException("Solo los usuarios pueden hacer esto");
        }
    }

    public void onlyAdminsOUsuarios() {
        if (!this.isSessionActive()) {
            throw new UnauthorizedException("Debes iniciar sesión para hacer esto");
        }
    }

    public void onlyUsuariosConSusDatos(Long usuario_id) {
        if (!this.isUsuario()) {
            throw new UnauthorizedException("Solo los usuarios pueden hacer esto");
        }
        if (!this.getSessionUser().getId().equals(usuario_id)) {
            throw new UnauthorizedException("Solo puedes acceder a tus datos");
        }
    } 

    public void onlyAdminsOUsuariosConSusDatos(Long usuario_id) {
        if (this.isSessionActive()) {
            if (!this.isAdmin()) {
                if (!this.isUsuario()) {
                    throw new UnauthorizedException("Solo los usuarios pueden hacer esto");
                } else {
                    if (!this.getSessionUser().getId().equals(usuario_id)) {
                        throw new UnauthorizedException("Solo puedes acceder a tus datos");
                    }
                }
            }
        } else {
            throw new UnauthorizedException("Debes iniciar sesión para hacer esto");
        }
    
    }
    
    
}
