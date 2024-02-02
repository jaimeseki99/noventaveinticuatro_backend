package net.ausiasmarch.noventaveinticuatro.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "modalidad")
public class ModalidadEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    
    private String imagen;

    @OneToMany(mappedBy = "modalidad", fetch = FetchType.LAZY)
    private List<CamisetaEntity> camisetas;

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCamisetas() {
        return camisetas.size();
    }

    public ModalidadEntity() {
        camisetas = new ArrayList<>();
    }

    public ModalidadEntity(Long id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public ModalidadEntity(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public ModalidadEntity(String nombre) {
        this.nombre = nombre;
    }

}
