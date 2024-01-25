package net.ausiasmarch.noventaveinticuatro.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioBean {

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenya;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }


    
}
