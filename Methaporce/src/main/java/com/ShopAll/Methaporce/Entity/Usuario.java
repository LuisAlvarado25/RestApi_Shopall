package com.ShopAll.Methaporce.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.util.*;

@Entity
@Data
@Table(name = "Usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonIgnore
    private Long id;
    @Column
    @NotBlank(message = "Este campo es obligatorio y no puede contener espacios vacios")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 10 caracteres")
    private String nombre;
    @Column
    private String apellido;
    @Column
    @Min(value = 18,message = "Tienes que ser mayor de edad para poder registrarte")
    private int edad ;
    @Column
    private Long telefono;

    @Column(unique = true)
    @Email(message = "El formato del correo no es válido")
    private String correo;

    @Column
    private String password;

    @OneToMany(mappedBy = "usuario",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direcciones;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rol> roles = new HashSet<>();
    public Usuario(String username, String password) {
        this.nombre = username;
        this.password = password;
    }
    public Usuario(){}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Implementa la lógica para devolver las autoridades (roles) del usuario
        // Puedes utilizar la clase SimpleGrantedAuthority para representar roles
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Devuelve la contraseña del usuario
        return password;
    }

    @Override
    public String getUsername() {
        // Devuelve el nombre de usuario del usuario
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
