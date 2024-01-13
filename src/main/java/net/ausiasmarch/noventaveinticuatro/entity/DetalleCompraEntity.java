package net.ausiasmarch.noventaveinticuatro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private CompraEntity compra;

    @ManyToOne
    @JoinColumn(name = "camiseta_id", nullable = false)
    private CamisetaEntity camiseta;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double precio;

    @NotNull
    @Min(1)
    private int cantidad;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double iva;

    @NotBlank
    private boolean descuento;

    @NotBlank
    @Min(0)
    private double porcentajeDescuento;

    private double costeFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
    }

    public CamisetaEntity getCamiseta() {
        return camiseta;
    }

    public void setCamiseta(CamisetaEntity camiseta) {
        this.camiseta = camiseta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getCosteFinal() {
        return costeFinal;
    }

    public void setCosteFinal(double costeFinal) {
        this.costeFinal = costeFinal;
    }

    public DetalleCompraEntity() {

    }

    public DetalleCompraEntity(Long id, CompraEntity compra, CamisetaEntity camiseta, double precio, int cantidad,
            double iva, boolean descuento, double porcentajeDescuento, double costeFinal) {
        this.id = id;
        this.compra = compra;
        this.camiseta = camiseta;
        this.precio = precio;
        this.cantidad = cantidad;
        this.iva = iva;
        this.descuento = descuento;
        this.porcentajeDescuento = porcentajeDescuento;
        this.costeFinal = costeFinal;
    }

    public DetalleCompraEntity(CompraEntity compra, CamisetaEntity camiseta, double precio, int cantidad, double iva,
            boolean descuento, double porcentajeDescuento, double costeFinal) {
        this.compra = compra;
        this.camiseta = camiseta;
        this.precio = precio;
        this.cantidad = cantidad;
        this.iva = iva;
        this.descuento = descuento;
        this.porcentajeDescuento = porcentajeDescuento;
        this.costeFinal = costeFinal;
    }




    
    
    
}
