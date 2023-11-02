package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Models.Usuario;
import com.ShopAll.Methaporce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/Usuario")
public class UserController {

private final UserService userService;

@Autowired
public UserController(UserService userService){
    this.userService=userService;
}

@GetMapping
    public List<Usuario> getUser(){
    return this.userService.getNames();
}

@PostMapping
    public ResponseEntity<Object> RegistrarUsuario(@RequestBody Usuario usuario){
    return  this.userService.newUsuario(usuario);

}

}
