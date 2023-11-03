package com.ShopAll.Methaporce.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    @JsonIgnore
    private int id;

    @Column(name="Usuario_Id")
    private int usuario_id;

    @Column(name="Producto_Id")
    private int producto_id;

    @Column(name="Comentario")
    private String comentario;

    @Column(name="Calificacion")
    private int calificacion;

    @Column(name="Url")
    private String url;

    @Column(name="Fecha_Hora")
    private String fecha_hora;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    @JsonBackReference
    @Schema(hidden = true)
    private Producto producto;

}
