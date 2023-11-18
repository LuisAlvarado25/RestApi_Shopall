package com.ShopAll.Methaporce.Entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String password;

    @OneToMany(mappedBy = "usuario",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direcciones;

    @ManyToMany(fetch = FetchType.EAGER,cascade =CascadeType.ALL)

    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="usuario_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Rol> roles;

}
