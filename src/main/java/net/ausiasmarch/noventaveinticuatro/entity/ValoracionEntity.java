package net.ausiasmarch.noventaveinticuatro.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "valoracion")
public class ValoracionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String comentario;

    private String imagen;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;

    @ManyToOne()
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne()
    @JoinColumn(name = "camiseta_id", nullable = false)
    private CamisetaEntity camiseta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public ValoracionEntity() {
    }

    public ValoracionEntity(Long id, String comentario, String imagen, LocalDate fecha, UsuarioEntity usuario,
            CamisetaEntity camiseta) {
        this.id = id;
        this.comentario = comentario;
        this.imagen = imagen;
        this.fecha = fecha;
        this.usuario = usuario;
        this.camiseta = camiseta;
    }

    public ValoracionEntity(String comentario, String imagen, LocalDate fecha, UsuarioEntity usuario,
            CamisetaEntity camiseta) {
        this.comentario = comentario;
        this.imagen = imagen;
        this.fecha = fecha;
        this.usuario = usuario;
        this.camiseta = camiseta;
    }

    public ValoracionEntity(String comentario, LocalDate fecha, UsuarioEntity usuario, CamisetaEntity camiseta) {
        this.comentario = comentario;
        this.fecha = fecha;
        this.usuario = usuario;
        this.camiseta = camiseta;
    }

}
