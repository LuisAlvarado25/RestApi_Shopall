package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/shopall/Usuario")
@Tag(name="Gestión de Usuarios")
public class UserController {

private final UserService userService;

@Autowired
public UserController(UserService userService){
    this.userService=userService;
}

//Método Obtener Usuario por ID.
@Operation(summary = "Obtiene un usuario por id")
@GetMapping("/{id}")
    public Usuario getUser(@PathVariable Long id){
    if(id <= 0){
        throw new UserException("El Usuario con el ID: " + id + " no se encontró");
    }
        return userService.getUsers(id);

}

//Método para crear un Usuario.
@Operation(summary = "Crea un nuevo Usuario")
@PostMapping
    public ResponseEntity<String> RegistrarUsuario(@Valid @RequestBody Usuario usuario){
    try {
        userService.newUsuario(usuario);
        return new ResponseEntity<>("Usuario registrado con éxito", HttpStatus.OK);
    } catch (UserException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
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
