package com.ShopAll.Methaporce.Models;




import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
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
