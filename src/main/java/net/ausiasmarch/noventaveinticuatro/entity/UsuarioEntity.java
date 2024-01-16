package net.ausiasmarch.noventaveinticuatro.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 100)
    private String apellido;

    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre de usuario debe tener caracteres alfanuméricos")
    private String username;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    private String direccion;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotNull
    @PositiveOrZero
    private double saldo;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "contrasenya")
    private String contrasenya = "7fc01059913987bd360c1f37d2ae1ff5c4a055473ccb0a7a6ab4bf97967a3821";

    @NotNull
    private boolean tipo = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<CompraEntity> compras;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<CarritoEntity> carritos;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ValoracionEntity> valoraciones;

    public int getCompras() {
        return compras.size();
    }

    public int getCarritos() {
        return carritos.size();
    }

    public int getValoraciones() {
        return valoraciones.size();
    }

    public UsuarioEntity() {
        compras = new ArrayList<>();
        carritos = new ArrayList<>();
        valoraciones = new ArrayList<>();
    }

    public UsuarioEntity(Long id, String nombre, String apellido, String username, String email, String direccion,
            String telefono, double saldo, String contrasenya, boolean tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.saldo = saldo;
        this.contrasenya = contrasenya;
        this.tipo = tipo;
    }

    public UsuarioEntity(String nombre, String apellido, String username, String email, String direccion,
            String telefono, double saldo, String contrasenya, boolean tipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.saldo = saldo;
        this.contrasenya = contrasenya;
        this.tipo = tipo;
    }

    public UsuarioEntity(String username, String contrasenya) {
        this.username = username;
        this.contrasenya = contrasenya;
    }
}
