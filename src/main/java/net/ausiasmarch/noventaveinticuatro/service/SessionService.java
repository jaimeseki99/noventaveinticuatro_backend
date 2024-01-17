package net.ausiasmarch.noventaveinticuatro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
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
    
    
}
