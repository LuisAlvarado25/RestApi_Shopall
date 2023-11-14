package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@Operation(summary = "Obtiene Todos los Usuarios")
@GetMapping("/{id}")
    public Usuario getUser(@PathVariable Long id){

    if(id <= 0){
        throw new UserException("La tarea con el ID: " + id + " no se encontró");
    }
    return userService.getUsers(id);
}

@Operation(summary = "Crea un nuevo Usuario")
@PostMapping
    public Usuario RegistrarUsuario(@Valid @RequestBody Usuario usuario){
    return  this.userService.newUsuario(usuario);
}
@Operation(summary = "Elimina un Usuario")
@DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        return this.userService.deleteUsuario(id);
    }


    @Operation(summary = "Actualiza un Usuario")
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return this.userService.UpdateUsuario(id,usuarioActualizado);
    }
}
