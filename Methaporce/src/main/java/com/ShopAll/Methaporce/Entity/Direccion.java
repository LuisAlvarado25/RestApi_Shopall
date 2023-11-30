package com.ShopAll.Methaporce.Entity;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "Direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Usuario user;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, calle, numero, colonia, cp, ciudad);
    }

}
