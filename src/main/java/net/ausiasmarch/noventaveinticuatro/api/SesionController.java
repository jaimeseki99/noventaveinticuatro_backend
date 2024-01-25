package net.ausiasmarch.noventaveinticuatro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.noventaveinticuatro.bean.CaptchaBean;
import net.ausiasmarch.noventaveinticuatro.bean.CaptchaResponseBean;
import net.ausiasmarch.noventaveinticuatro.bean.UsuarioBean;
import net.ausiasmarch.noventaveinticuatro.service.SessionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sesion")
public class SesionController {
    
    @Autowired
    SessionService oSessionService;

    @GetMapping("/prelogin")
    public ResponseEntity<CaptchaResponseBean> prelogin() {
        return ResponseEntity.ok(oSessionService.prelogin());
    }

    @PostMapping("/loginCaptcha")
    public ResponseEntity<String> loginCaptcha(@RequestBody CaptchaBean oCaptchaBean) {
        return ResponseEntity.ok(oSessionService.loginCaptcha(oCaptchaBean));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioBean usuarioBean) {
        return ResponseEntity.ok(oSessionService.login(usuarioBean));
    }
}
