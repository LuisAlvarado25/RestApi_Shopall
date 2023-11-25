package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public Optional<Producto> findByNombre(String nombre) {
        return productRepository.findByNombre(nombre);
    }
    @Transactional
    public void Save(Producto producto){
        productRepository.save(producto);
    }
}
