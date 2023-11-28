package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Carrito;
import com.ShopAll.Methaporce.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {
    Carrito findByUser(Usuario usuario);

}
