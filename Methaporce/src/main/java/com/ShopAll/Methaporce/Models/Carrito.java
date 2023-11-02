package com.ShopAll.Methaporce.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Carritos")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;

    @Column(name="Usuario_Id")
    private int usuario_id;

    @Column(name="Producto_Id")
    private int producto_id;

    @Column(name="Cantidad")
    private int Cantidad;


}
