package com.ShopAll.Methaporce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Carritos")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @OneToMany(mappedBy = "carrito",cascade =CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonManagedReference
    private List<ListaCarrito> listaCarritos= new ArrayList<>();

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones;

}
