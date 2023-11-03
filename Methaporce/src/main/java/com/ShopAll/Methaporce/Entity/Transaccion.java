package com.ShopAll.Methaporce.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Transacciones")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;

    @Column(name="Carrito_Id")
    private int carrito_id;

    @Column(name="Total")
    private double total;

    @Column(name="Metododepago")
    private double metododepago;

    @Column(name="Fecha_Hora")
    private Timestamp fecha_hora;

}
