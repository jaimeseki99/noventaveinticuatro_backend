package net.ausiasmarch.noventaveinticuatro.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {

    @NotBlank(message = "La contraseña es necesaria")
    private String contrasenya;

    @NotBlank(message = "Confirma la contraseña")
    private String confirmarContrasenya;

    @NotBlank(message = "El token de la contraseña es necesario")
    private String tokenContrasenya;

    public ChangePasswordDTO() {
        
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getConfirmarContrasenya() {
        return confirmarContrasenya;
    }

    public void setConfirmarContrasenya(String confirmarContrasenya) {
        this.confirmarContrasenya = confirmarContrasenya;
    }

    public String getTokenContrasenya() {
        return tokenContrasenya;
    }

    public void setTokenContrasenya(String tokenContrasenya) {
        this.tokenContrasenya = tokenContrasenya;
    }

    public ChangePasswordDTO(@NotBlank(message = "La contraseña es necesaria") String contrasenya,
            @NotBlank(message = "Confirma la contraseña") String confirmarContrasenya,
            @NotBlank(message = "El token de la contraseña es necesario") String tokenContrasenya) {
        this.contrasenya = contrasenya;
        this.confirmarContrasenya = confirmarContrasenya;
        this.tokenContrasenya = tokenContrasenya;
    }




}
