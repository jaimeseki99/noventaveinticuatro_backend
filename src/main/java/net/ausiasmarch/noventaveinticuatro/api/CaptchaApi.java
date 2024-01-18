package net.ausiasmarch.noventaveinticuatro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.noventaveinticuatro.entity.CaptchaEntity;
import net.ausiasmarch.noventaveinticuatro.service.CaptchaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/captcha")
public class CaptchaApi {

    @Autowired
    CaptchaService oCaptchaService;

    @PostMapping("/create")
    public ResponseEntity<CaptchaEntity> create() {
        return ResponseEntity.ok(oCaptchaService.createCaptcha());
    }
    
}
