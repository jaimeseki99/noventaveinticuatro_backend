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
import jakarta.validation.constraints.DecimalMin;
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

    @NotNull
    @Min(1)
    private int cantidadTotal;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double costeTotal;

    @NotBlank
    @Size(max = 10)
    private String codigoPedido;

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


    public int getCantidadTotal() {
        return cantidadTotal;
    }


    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }


    public double getCosteTotal() {
        return costeTotal;
    }


    public void setCosteTotal(double costeTotal) {
        this.costeTotal = costeTotal;
    }


    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public CompraEntity() {
        detallesCompra = new ArrayList<>();
    }

    public CompraEntity(Long id, UsuarioEntity usuario, LocalDateTime fecha, int cantidadTotal, double costeTotal,
            String codigoPedido) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.cantidadTotal = cantidadTotal;
        this.costeTotal = costeTotal;
        this.codigoPedido = codigoPedido;
    }

    public CompraEntity(UsuarioEntity usuario, LocalDateTime fecha, int cantidadTotal, double costeTotal,
            String codigoPedido) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.cantidadTotal = cantidadTotal;
        this.costeTotal = costeTotal;
        this.codigoPedido = codigoPedido;
    }

}
