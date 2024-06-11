package net.ausiasmarch.noventaveinticuatro.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "camiseta")
public class CamisetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String titulo;

    private String imagen;

    @NotBlank
    @Size(max = 10)
    private String talla;

    @NotBlank
    @Size(max = 10)
    private String manga;

    @NotBlank
    @Size(max = 100)
    private String temporada;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double precio;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double iva;

    
    private boolean descuento;

    
    @Min(0)
    private double porcentajeDescuento;

    @NotNull
    @Min(0)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private EquipoEntity equipo;

    @ManyToOne
    @JoinColumn(name = "modalidad_id", nullable = false)
    private ModalidadEntity modalidad;

    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false)
    private LigaEntity liga;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getManga() {
        return manga;
    }

    public void setManga(String manga) {
        this.manga = manga;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public EquipoEntity getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoEntity equipo) {
        this.equipo = equipo;
    }

    public ModalidadEntity getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadEntity modalidad) {
        this.modalidad = modalidad;
    }

    public LigaEntity getLiga() {
        return liga;
    }

    public void setLiga(LigaEntity liga) {
        this.liga = liga;
    }

    @OneToMany(mappedBy = "camiseta", fetch = FetchType.LAZY)
    private List<CarritoEntity> carritos;

    @OneToMany(mappedBy = "camiseta", fetch = FetchType.LAZY)
    private List<ValoracionEntity> valoraciones;

    @OneToMany(mappedBy = "camiseta", fetch = FetchType.LAZY)
    private List<DetalleCompraEntity> detalleCompras;

    public CamisetaEntity() {
        carritos = new ArrayList<>();
        valoraciones = new ArrayList<>();
        detalleCompras = new ArrayList<>();
    }

    public int getCarritos() {
        return carritos.size();
    }

    public int getValoraciones() {
        return valoraciones.size();
    }

    public int getDetalleCompras() {
        return detalleCompras.size();
    }

    public CamisetaEntity(Long id, String titulo, String imagen,String talla, String manga,
            String temporada,  double precio, double iva, boolean descuento,
            double porcentajeDescuento, int stock, EquipoEntity equipo, ModalidadEntity modalidad, LigaEntity liga) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.talla = talla;
        this.manga = manga;
        this.temporada = temporada;
        this.precio = precio;
        this.iva = iva;
        this.descuento = descuento;
        this.porcentajeDescuento = porcentajeDescuento;
        this.stock = stock;
        this.equipo = equipo;
        this.modalidad = modalidad;
        this.liga = liga;
    }

    public CamisetaEntity(String titulo, String imagen, String talla, String manga, String temporada,
             double precio, double iva, boolean descuento, double porcentajeDescuento, int stock,
            EquipoEntity equipo, ModalidadEntity modalidad, LigaEntity liga) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.talla = talla;
        this.manga = manga;

        this.temporada = temporada;
        this.precio = precio;
        this.iva = iva;
        this.descuento = descuento;
        this.porcentajeDescuento = porcentajeDescuento;
        this.stock = stock;
        this.equipo = equipo;
        this.modalidad = modalidad;
        this.liga = liga;
    }

    public CamisetaEntity(String titulo, String talla, String manga, String temporada, double precio, double iva,
            int stock, EquipoEntity equipo, ModalidadEntity modalidad, LigaEntity liga) {
        this.titulo = titulo;
        this.talla = talla;
        this.manga = manga;
        this.temporada = temporada;
        this.precio = precio;
        this.iva = iva;
        this.stock = stock;
        this.equipo = equipo;
        this.modalidad = modalidad;
        this.liga = liga;
    }

    public CamisetaEntity(String titulo, String imagen, String talla, String manga, String temporada, double precio, double iva,
            int stock, EquipoEntity equipo, ModalidadEntity modalidad, LigaEntity liga) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.talla = talla;
        this.manga = manga;
        this.temporada = temporada;
        this.precio = precio;
        this.iva = iva;
        this.stock = stock;
        this.equipo = equipo;
        this.modalidad = modalidad;
        this.liga = liga;
    }

}
