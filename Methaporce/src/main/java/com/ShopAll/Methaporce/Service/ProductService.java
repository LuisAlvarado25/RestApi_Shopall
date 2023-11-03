package com.ShopAll.Methaporce.Service;
import com.ShopAll.Methaporce.Entity.Comentario;
import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepositoryRepository){
        this.productRepository=productRepositoryRepository;
    }

    public List<Producto> getProduct(){
        return this.productRepository.findAll();
    }
    public ResponseEntity<Object> newProduct(Producto producto) {


        for (Comentario comentario :producto.getComentarios()) {
            comentario.setProducto(producto);
        }


       productRepository.save(producto);


        return new ResponseEntity<>(
                Map.of("message", "Producto registrado con éxito"),
                HttpStatus.OK
        );}

    public ResponseEntity<String> deleteProducto(Long id) {
        if (productRepository.existsById(id)) {
           productRepository.deleteById(id);
            return new ResponseEntity<>("Producto eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}