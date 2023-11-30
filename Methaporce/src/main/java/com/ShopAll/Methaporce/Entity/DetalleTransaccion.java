package com.ShopAll.Methaporce.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetalleTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "transaccion_id")
    private Transaccion transaccion;

}