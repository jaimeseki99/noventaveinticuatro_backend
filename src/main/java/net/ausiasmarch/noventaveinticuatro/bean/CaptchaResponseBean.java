package net.ausiasmarch.noventaveinticuatro.bean;

public class CaptchaResponseBean {

    private String token;
    private byte[] captchaImage;
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public byte[] getCaptchaImage() {
        return captchaImage;
    }
    public void setCaptchaImage(byte[] captchaImage) {
        this.captchaImage = captchaImage;
    }


    
}
