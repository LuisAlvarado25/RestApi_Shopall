package com.ShopAll.Methaporce.Controller;


import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="api/shopall/Producto")
@Tag(name="Gestión de Productos")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @Operation(summary = "Obten todos los productos")

    @GetMapping
    public List<Producto> getProduct(){
        return this.productService.getProduct();
    }
    
    @Operation(summary = "Crea un nuevo Producto")
    @PostMapping
    public ResponseEntity<Object> RegistrarProducto(@RequestBody Producto producto){
        return  this.productService.newProduct(producto);
    }

    @Operation(summary = "Elimina un Producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        return this.productService.deleteProducto(id);
    }

}
