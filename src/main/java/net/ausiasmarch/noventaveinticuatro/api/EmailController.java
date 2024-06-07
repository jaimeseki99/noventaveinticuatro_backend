package net.ausiasmarch.noventaveinticuatro.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import net.ausiasmarch.noventaveinticuatro.dto.ChangePasswordDTO;
import net.ausiasmarch.noventaveinticuatro.dto.EmailValuesDTO;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.repository.UsuarioRepository;
import net.ausiasmarch.noventaveinticuatro.service.EmailService;
import net.ausiasmarch.noventaveinticuatro.service.UsuarioService;

@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService oEmailService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Value("$(spring.mail.username)")
    private String mailFrom;

    @PostMapping("/recover-password")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO emailValuesDTO) {
        UsuarioEntity oUsuarioEntity = oUsuarioService.getByEmail(emailValuesDTO.getMailTo());

        emailValuesDTO.setMailFrom(mailFrom);
        emailValuesDTO.setMailTo(oUsuarioEntity.getEmail());
        emailValuesDTO.setMailSubject("Cambio de contraseña");
        emailValuesDTO.setUserName(oUsuarioEntity.getUsername());
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        emailValuesDTO.setTokenPassword(token);

        oUsuarioEntity.setTokenContrasenya(token);
        oUsuarioRepository.save(oUsuarioEntity);

        oEmailService.sendEmailTemplate(emailValuesDTO);

        return new ResponseEntity(null, HttpStatus.OK);
        
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity("Compruebe los datos introducidos", HttpStatus.BAD_REQUEST);
        }

        if (!changePasswordDTO.getContrasenya().equals(changePasswordDTO.getConfirmarContrasenya())) {
            return new ResponseEntity("Las contraseñas no coinciden", HttpStatus.BAD_REQUEST);
        }

        UsuarioEntity usuarioEntity = oUsuarioService.getByTokenContrasenya(changePasswordDTO.getTokenContrasenya());
        usuarioEntity.setContrasenya(changePasswordDTO.getContrasenya());
        usuarioEntity.setTokenContrasenya(null);
        oUsuarioRepository.save(usuarioEntity);

        return new ResponseEntity(null, HttpStatus.OK);
    }

}
