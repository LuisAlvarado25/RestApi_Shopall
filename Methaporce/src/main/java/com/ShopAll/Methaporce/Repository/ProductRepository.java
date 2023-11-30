package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Producto,Long> {
    Optional<Producto> findByNombre(String name);
    List<Producto> findByUsuarioNombre(String nombreUsuario);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);

}
