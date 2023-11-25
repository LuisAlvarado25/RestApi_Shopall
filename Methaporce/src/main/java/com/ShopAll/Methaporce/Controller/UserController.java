package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Repository.RolRepository;
import com.ShopAll.Methaporce.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@CrossOrigin(origins="*")
@Tag(name="Gestión de Usuarios")
public class UserController {

private final UserService userService;
@Autowired
private RolRepository rolRepository;
@Autowired
public UserController(UserService userService){
    this.userService=userService;
}

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password) {
        return "redirect:/login-success";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/login-success")
    public String loginSuccessPage() {
        return "login-success";
    }
    @GetMapping("/login-error")
    public String loginFailurePage() {
        return "login-error";
    }
    @GetMapping("/NewUser")
    public String NewUser(Model model) {
    List<Rol> listRoles=rolRepository.findAll();
    model.addAttribute("listRoles",listRoles);
    model.addAttribute("Usuario" ,new Usuario());
    return "NewUSer";
    }
    @PostMapping("/register")
    public String registerUser(@Valid Usuario usuario) {
    userService.save(usuario);
       return "redirect:/login";
    }

}
