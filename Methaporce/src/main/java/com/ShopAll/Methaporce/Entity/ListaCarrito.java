package com.ShopAll.Methaporce.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ListaCarrito")
public class ListaCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "Cantidad")
    private int cantidad;
}