package net.ausiasmarch.noventaveinticuatro.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pendent")
public class PendentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_captcha")
    private CaptchaEntity captcha;

     private String token;

    private LocalDateTime timecode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CaptchaEntity getCaptcha() {
        return captcha;
    }

    public void setCaptcha(CaptchaEntity captcha) {
        this.captcha = captcha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTimecode() {
        return timecode;
    }

    public void setTimecode(LocalDateTime timecode) {
        this.timecode = timecode;
    }
    
}
