package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Repository.ComentarioRepository;
import com.ShopAll.Methaporce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;


    public Optional<Producto> findByNombre(String nombre) {
        return productRepository.findByNombre(nombre);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productRepository.findAll();
    }
    public List<Producto> obtenerProductosPorUsuario(String nombreUsuario) {
        return productRepository.findByUsuarioNombre(nombreUsuario);
    }

    @Transactional
    public void Save(Producto producto){
        productRepository.save(producto);
    }

    public Producto obtenerProductoPorId(Long productoId) {
        return productRepository.findById(productoId).orElse(null);
    }

    @Transactional
    public void borrarProducto(Long id) {

        Optional<Producto> productoOptional = productRepository.findById(id);


        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();


            producto.getComentarios().clear();
            comentarioRepository.deleteAllByProducto(producto);

            productRepository.deleteById(id);
        }
    }
    public String obtenerNombreUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {

            return null;
        }

        return authentication.getName();
    }

    public boolean esUsuarioVendedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_Vendedor"));
    }


}
