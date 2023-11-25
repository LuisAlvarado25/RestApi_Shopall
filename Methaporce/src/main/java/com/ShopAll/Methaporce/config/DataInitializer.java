package com.ShopAll.Methaporce.config;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Service.RolService;
import com.ShopAll.Methaporce.Service.UserService;
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

    @Override
    public void run(String... args) throws Exception {

            if (userService.userNotExists()){

                Rol role1 = new Rol("Comprador");
                roleService.save(role1);
                Rol role2 = new Rol("Vendedor");
                roleService.save(role2);
                Usuario usuario = new Usuario("admin", "admin",18,"admin@shopall.com");
                usuario.setRoles(Set.of(role1, role2));
                Direccion direccion = new Direccion();
                usuario.setDirecciones(List.of(direccion));
                userService.save(usuario);


        }
    }
}

