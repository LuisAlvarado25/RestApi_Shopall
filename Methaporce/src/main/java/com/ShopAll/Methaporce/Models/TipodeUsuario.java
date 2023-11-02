package com.ShopAll.Methaporce.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "TipodeUsuario")
public class TipodeUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;



    @Column(name="Tipo")
    private String tipo;
}
