package net.ausiasmarch.noventaveinticuatro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "carrito")
public class CarritoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "camiseta_id", nullable = false)
    private CamisetaEntity camiseta;

    @NotNull
    @Min(1)
    private int cantidad;

    @Size(max = 15)
    private String nombre;

    private int dorsal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public CamisetaEntity getCamiseta() {
        return camiseta;
    }

    public void setCamiseta(CamisetaEntity camiseta) {
        this.camiseta = camiseta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }


    public CarritoEntity() {

    }

    public CarritoEntity(Long id, UsuarioEntity usuario, CamisetaEntity camiseta, int cantidad) {
        this.id = id;
        this.usuario = usuario;
        this.camiseta = camiseta;
        this.cantidad = cantidad;
    }

    public CarritoEntity(UsuarioEntity usuario, CamisetaEntity camiseta, int cantidad) {
        this.usuario = usuario;
        this.camiseta = camiseta;
        this.cantidad = cantidad;
    }

    public CarritoEntity(Long id, UsuarioEntity usuario, CamisetaEntity camiseta, int cantidad,
             String nombre, int dorsal) {
        this.id = id;
        this.usuario = usuario;
        this.camiseta = camiseta;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.dorsal = dorsal;
    }

    public CarritoEntity(UsuarioEntity usuario, CamisetaEntity camiseta, int cantidad, String nombre, int dorsal) {
        this.usuario = usuario;
        this.camiseta = camiseta;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.dorsal = dorsal;
    }

    
}
