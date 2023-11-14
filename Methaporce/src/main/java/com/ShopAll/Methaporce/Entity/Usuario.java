package com.ShopAll.Methaporce.Entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Entity
@Data
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @JsonIgnore
    private Long id;

    @Column
    @NotBlank(message = "Este campo es obligatorio y no puede contener espacios vacios")
    private String nombre;

    @Column
    private String apellido;

    @Column
    @Min(value = 18,message = "Tienes que ser mayor de edad para poder registrarte")
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
