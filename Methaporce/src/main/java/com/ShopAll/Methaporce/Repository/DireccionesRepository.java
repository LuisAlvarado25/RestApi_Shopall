package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Carrito;
import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionesRepository extends JpaRepository<Direccion,Long> {
    Direccion findByUser(Usuario usuario);

}
