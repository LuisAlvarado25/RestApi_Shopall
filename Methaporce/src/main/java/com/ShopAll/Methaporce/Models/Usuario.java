package com.ShopAll.Methaporce.Models;




import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int edad ;

    @Column
    private Long telefono;

    @Column(unique = true)
    private String correo;

    @Column
    private String pass;

   @Column
   private String tipo;

    @OneToMany(mappedBy = "usuario",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direcciones;

}
