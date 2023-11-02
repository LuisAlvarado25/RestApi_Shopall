package com.ShopAll.Methaporce.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;
    @Column(name="Nombre")
    private String nombre;
    @Column(name="Descripcion")
    private String descripcion;
    @Column(name="Precio")
    private double precio;
    @Column(name="Categoria")
    private String Categoria;
    @Column(name="Usuario_Id")
    private int usuario_id;
    @Column(name="Cantidad")
    private int cantidad;



}
