package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Comentario;
import com.ShopAll.Methaporce.Entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario,Long> {
    @Transactional
    void deleteAllByProducto(Producto producto);

    List<Comentario> findByProducto(Producto producto);
}
