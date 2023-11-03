package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Producto,Long> {
}
