package net.ausiasmarch.noventaveinticuatro.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "equipo")
public class EquipoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false)
    private LigaEntity liga;

    @OneToMany(mappedBy = "equipo", fetch = jakarta.persistence.FetchType.LAZY)
    private List<CamisetaEntity> camisetas = new ArrayList<>();

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

    public LigaEntity getLiga() {
        return liga;
    }

    public void setLiga(LigaEntity liga) {
        this.liga = liga;
    }

    public int getCamisetas() {
        return camisetas.size();
    }

    public EquipoEntity() {
        camisetas = new ArrayList<>();
    }

    public EquipoEntity(Long id, String nombre, LigaEntity liga) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
    }

    public EquipoEntity(String nombre, LigaEntity liga) {
        this.nombre = nombre;
        this.liga = liga;
    }

}
