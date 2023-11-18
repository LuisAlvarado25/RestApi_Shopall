package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Service.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="api/shopall/Usuario")
@Tag(name="Gestión de Usuarios")
public class UserController {

private final UserService userService;

@Autowired
public UserController(UserService userService){
    this.userService=userService;
}

    //Método para obtener todos los Usuarios.
    @Operation(summary = "Obtiene Todos los Usuarios")
    @GetMapping()
    public  List<Usuario> getUsers(){
    return userService.getUsers1();
   }

    //Método Obtener Usuario por CorreoElectronico.
    @Operation(summary = "Obtiene un usuario por correo")
    @GetMapping("/{correo}")
    public Usuario getUserByCorreo(@PathVariable String correo){
    Usuario usuario=userService.getUserByCorreo(correo);
        if (usuario == null) {
            throw new UserException("El Usuario con el Correo: " + correo + " no se encontró");
        }
        return usuario;
}


     //Método para crear un Usuario.
     @Operation(summary = "Crea un nuevo Usuario")
     @PostMapping()
    public ResponseEntity<String> RegistrarUsuario(@Valid @RequestBody Usuario usuario){
    userService.newUsuario(usuario);
    return new ResponseEntity<>("Usuario Agregado", HttpStatus.ACCEPTED);
}

     //Método para borrar Usuario por ID
     @Operation(summary = "Elimina un Usuario")
     @DeleteMapping("/{id}")
     public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        return this.userService.deleteUsuario(id);
    }

    //Método para actualizar un Usuario
    @Operation(summary = "Actualiza un Usuario")
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return this.userService.UpdateUsuario(id,usuarioActualizado);
    }

}
