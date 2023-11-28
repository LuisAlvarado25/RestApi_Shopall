package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.ListaCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ListaCarritoRepository extends JpaRepository<ListaCarrito, Long> {
    ListaCarrito findByProductoIdAndCarritoId(Long productoId, Long carritoId);
}