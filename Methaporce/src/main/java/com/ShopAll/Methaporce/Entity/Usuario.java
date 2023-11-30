package com.ShopAll.Methaporce.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.util.*;
import java.util.stream.Collectors;

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

    @Column
    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "Este campo es obligatorio y no puede contener espacios vacios")
    private String correo;

    @Column
    @NotBlank(message = "Este campo es obligatorio y no puede contener espacios vacios")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Direccion direccion;

    @OneToMany(mappedBy = "usuario",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Producto> productos;

    @OneToMany(mappedBy = "usuario",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comentario> comentarios;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rol> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Carrito> carritos=new ArrayList<Carrito>();


    public List<Carrito> getCarritos() {
        return carritos;
    }
    public Usuario(String username, String password) {
        this.nombre = username;
        this.password = password;
    }
    public Usuario(String nombre, String password, int edad, String correo) {
        this.nombre = nombre;
        this.password = password;
        this.edad = edad;
        this.correo = correo;
        this.productos = new ArrayList<>();
    }

    public Usuario(){}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        // Devuelve la contraseña del usuario
        return password;
    }

    @Override
    public String getUsername() {

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

    public String getCorreo() {
        return correo;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, edad, telefono, correo, password);
    }
}
