package net.ausiasmarch.noventaveinticuatro.entity;


import java.time.LocalDateTime;
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
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fecha;

    @NotBlank
    @Size(max = 10)
    private String codigoPedido;

    @Min(0)
    private Long factura_id;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaFactura;

   

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


    public LocalDateTime getFecha() {
        return fecha;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Long getFactura_id() {
        return factura_id;
    }

    public void setFactura_id(Long factura_id) {
        this.factura_id = factura_id;
    }

    public LocalDateTime getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDateTime fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public CompraEntity() {
        detallesCompra = new ArrayList<>();
    }

    public CompraEntity(Long id, UsuarioEntity usuario, LocalDateTime fecha, String codigoPedido, Long factura_id,
            LocalDateTime fechaFactura) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.codigoPedido = codigoPedido;
        this.factura_id = factura_id;
        this.fechaFactura = fechaFactura;
    }

    public CompraEntity(UsuarioEntity usuario, LocalDateTime fecha, String codigoPedido, Long factura_id,
            LocalDateTime fechaFactura) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.codigoPedido = codigoPedido;
        this.factura_id = factura_id;
        this.fechaFactura = fechaFactura;
    }

}
