package net.ausiasmarch.noventaveinticuatro.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "compra")
public class CompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;

    @NotBlank
    @Size(max = 100)
    private String codigoPedido;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaEntrega;

    @NotBlank
    private String direccion;

   

    @OneToMany(mappedBy = "compra")
    private List<DetalleCompraEntity> detallesCompra;

    
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


    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }


    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getDetalleCompras() {
        return detallesCompra.size();
    }

    public CompraEntity() {
        detallesCompra = new ArrayList<>();
    }

    public CompraEntity(Long id, UsuarioEntity usuario, LocalDate fecha, String codigoPedido, LocalDate fechaEntrega,
            String direccion) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.codigoPedido = codigoPedido;
        this.fechaEntrega = fechaEntrega;
        this.direccion = direccion;
    }

    public CompraEntity(UsuarioEntity usuario, LocalDate fecha, String codigoPedido, LocalDate fechaEntrega,
            String direccion) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.codigoPedido = codigoPedido;
        this.fechaEntrega = fechaEntrega;
        this.direccion = direccion;
    }

}
