package com.ShopAll.Methaporce.config;

import com.ShopAll.Methaporce.Entity.*;
import com.ShopAll.Methaporce.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RolService roleService;
    @Autowired
    private ProductService productoService;
    @Autowired
    private DireccionesService direccionesService;

    @Autowired
    private ComentarioService comentarioService;

    @Override
    public void run(String... args) throws Exception {
        Usuario usuario= new Usuario("admin", "admin",18,"admin@shopall.com");
        if (userService.userNotExists()){

            Rol role1 = new Rol("Comprador");
            roleService.save(role1);
            Rol role2 = new Rol("Vendedor");
            roleService.save(role2);

            usuario.setRoles(Set.of(role1, role2));
            Direccion direccion = new Direccion();
            direccion.setCalle("AdminStreet");


            userService.save(usuario);
            direccion.setUsuario(usuario);
            direccionesService.Save(direccion);

            Producto producto = new Producto();
            producto.setNombre("Camisa");
            producto.setDescripcion("Camisa1");
            producto.setPrecio(19.99);
            producto.setCategoria("Ropa");
            producto.setCantidad(10);
            producto.setUsuario(usuario);
            usuario.getProductos().add(producto);
            productoService.Save(producto);

            Comentario comentario=new Comentario();
            comentario.setComentario("Buena Calidad");
            comentario.setCalificacion(10);
            comentario.setUsuario(usuario);
            comentario.setProducto(producto);
            comentarioService.Save(comentario);
            }



    }
}

