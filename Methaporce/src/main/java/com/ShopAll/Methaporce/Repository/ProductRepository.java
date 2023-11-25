package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Producto,Long> {
    Optional<Producto> findByNombre(String name);
}
