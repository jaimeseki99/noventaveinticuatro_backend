package net.ausiasmarch.noventaveinticuatro.service;

import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import net.ausiasmarch.noventaveinticuatro.entity.CaptchaEntity;
import net.ausiasmarch.noventaveinticuatro.repository.CaptchaRepository;

@Service
public class CaptchaService {

    @Autowired
    private CaptchaRepository oCaptchaRepository;    

    @Autowired
    private DefaultKaptcha oDefaultKaptcha;

    public CaptchaEntity createCaptcha() {
        CaptchaEntity oCaptchaEntity = new CaptchaEntity();
        String text = oDefaultKaptcha.createText();
        byte[] image = generateCaptchaImage(text);
        oCaptchaEntity.setId(null);
        oCaptchaEntity.setText(text);
        oCaptchaEntity.setImage(image);
        return oCaptchaRepository.save(oCaptchaEntity);
    }

    public CaptchaEntity getRandomCaptcha() {

        List<CaptchaEntity> list = oCaptchaRepository.findAll();
        if (!list.isEmpty()) {
            Random oRandom = new Random();
            int index = oRandom.nextInt(list.size());
            return list.get(index);
        } else {
            throw new RuntimeException("No se han encontrado captchas en la base de datos");        }
    }

    private byte[] generateCaptchaImage(String text) {
        BufferedImage oBufferedImage = oDefaultKaptcha.createImage(text);
        try (ByteArrayOutputStream oByteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(oBufferedImage, "png", oByteArrayOutputStream);
            return oByteArrayOutputStream.toByteArray();
        } catch (Exception oException) {
            oException.printStackTrace();
            return new byte[0];
        }
    }
    
}
