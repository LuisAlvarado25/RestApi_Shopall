package com.ShopAll.Methaporce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="Id")
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Descripcion")
    private String descripcion;

    @Column(name="Precio")
    private double precio;

    @Column(name="Categoria")
    private String Categoria;

    @Column(name="Cantidad")
    private int cantidad;

    @OneToMany(mappedBy = "producto",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comentario> comentarios;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    @Schema(hidden = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaCarrito> listaCarritos;

    public void agregarComentario(Comentario comentario) {
        if (comentarios == null) {
            comentarios = new ArrayList<>();
        }
        comentarios.add(comentario);
        comentario.setProducto(this); // Asegura la relación bidireccional
    }

}
