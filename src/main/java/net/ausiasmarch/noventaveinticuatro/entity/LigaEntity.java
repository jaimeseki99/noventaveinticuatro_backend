package net.ausiasmarch.noventaveinticuatro.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "liga")
public class LigaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String nombre;

    @NotBlank
    @Size(max = 250)
    private String pais;

    @NotBlank
    @Size(max = 100)
    private String deporte;

    

    @OneToMany(mappedBy = "liga", fetch = FetchType.LAZY)
    private List<EquipoEntity> equipos;
    
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }
    public LigaEntity() {
        equipos = new ArrayList<>();
    }

    public LigaEntity(Long id, String nombre, String pais, String deporte) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.deporte = deporte;
    }

    public LigaEntity(String nombre, String pais, String deporte) {
        this.nombre = nombre;
        this.pais = pais;
        this.deporte = deporte;
    }

    
    
}