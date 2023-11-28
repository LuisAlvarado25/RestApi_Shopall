package com.ShopAll.Methaporce.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@Table(name = "Transacciones")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Usuario_Id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Carrito_Id")
    private Carrito carrito;


    @Column(name="Total")
    private double total;

    @Column(name="Metododepago")
    private double metododepago;

    @Column(name="Fecha_Hora")
    private Timestamp fecha_hora;
    @PrePersist
    protected void onCreate() {
        fecha_hora = Timestamp.from(Instant.now());
    }

}
