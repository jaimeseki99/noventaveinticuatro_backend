package net.ausiasmarch.noventaveinticuatro.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.ausiasmarch.noventaveinticuatro.dto.EmailValuesDTO;

@Service("emailService")
public class EmailService {

    @Autowired
    JavaMailSender oJavaMailSender;

    @Autowired
    TemplateEngine oTemplateEngine;

    @Value("${mail.urlFront}")
    private String urlFront;

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void sendEmailTemplate(EmailValuesDTO oEmailValuesDTO) {
        MimeMessage mimeMessage = oJavaMailSender.createMimeMessage();

        try {
            MimeMessageHelper oMimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();

            Map<String, Object> mapModel = new HashMap<>();
            mapModel.put("username", oEmailValuesDTO.getUserName());
            mapModel.put("url", urlFront + oEmailValuesDTO.getTokenPassword());

            context.setVariables(mapModel);

            String htmlText = oTemplateEngine.process("email-template", context);

            oMimeMessageHelper.setFrom(oEmailValuesDTO.getMailFrom());
            oMimeMessageHelper.setTo(oEmailValuesDTO.getMailTo());
            oMimeMessageHelper.setSubject(oEmailValuesDTO.getMailSubject());
            oMimeMessageHelper.setText(htmlText, true);

            oJavaMailSender.send(oMimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
