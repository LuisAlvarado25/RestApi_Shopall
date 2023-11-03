package com.ShopAll.Methaporce.Entity;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    @JsonIgnore
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    @Schema(hidden = true)
    private Usuario usuario;

    @Column(name="Calle")
    private String calle;

    @Column(name="Numero")
    private int numero;

    @Column(name="Colonia")
    private String colonia;

    @Column(name="CP")
    private int cp;

    @Column(name="Ciudad")
    private String ciudad;

}
