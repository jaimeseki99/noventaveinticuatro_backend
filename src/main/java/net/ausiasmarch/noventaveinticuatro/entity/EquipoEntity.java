package net.ausiasmarch.noventaveinticuatro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    public EquipoEntity() {
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
