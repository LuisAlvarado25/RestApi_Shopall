    package com.ShopAll.Methaporce.Controller;

    import com.ShopAll.Methaporce.Entity.Carrito;
    import com.ShopAll.Methaporce.Entity.Direccion;
    import com.ShopAll.Methaporce.Entity.Rol;
    import com.ShopAll.Methaporce.Entity.Usuario;
    import com.ShopAll.Methaporce.Exception.UserException;
    import com.ShopAll.Methaporce.Repository.CarritoRepository;
    import com.ShopAll.Methaporce.Repository.DireccionesRepository;
    import com.ShopAll.Methaporce.Repository.RolRepository;
    import com.ShopAll.Methaporce.Service.DireccionesService;
    import com.ShopAll.Methaporce.Service.UserService;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.validation.Valid;
    import org.hibernate.Hibernate;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;


    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.List;


    @Controller
    @CrossOrigin(origins="*")
    @Tag(name="Gestión de Usuarios")
    public class UserController {

    private final UserService userService;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DireccionesService direccionesService;
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

        @GetMapping("/login")
        public String loginPage() {
            return "login";
        }
        @PostMapping("/login")
        public String loginSubmit(@RequestParam String username, @RequestParam String password) {
            return "redirect:/login-success";
        }
        @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return "redirect:/login";
        }
        @GetMapping("/login-success")
        public String loginSuccessPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return "login-success";
        }
        @GetMapping("/login-error")
        public String loginFailurePage() {
            return "login-error";
        }
        @GetMapping("/Registro")
        public String Registro(Model model) {
            List<Rol> listRoles=rolRepository.findAll();
            Usuario usuario = new Usuario();
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("Usuario" ,usuario);
            return "Registro";
        }
        @GetMapping("/Actualizar")
        public String Actualizar(@AuthenticationPrincipal UserDetails userDetails,Model model) {
            List<Rol> listRoles=rolRepository.findAll();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = authentication.getName();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("Usuario" ,usuario);
        return "Actualizar";
        }

        @GetMapping("/borrar-cuenta")
        public String borrarCuenta(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getDetails() != null) {
                Usuario usuario = (Usuario) authentication.getPrincipal();
                Long userId = usuario.getId();
                if (usuario.getCarritos() != null) {
                    for (Carrito carrito : usuario.getCarritos()) {
                        carritoRepository.delete(carrito);
                    }
                }
                userService.deleteUsuario(userId);
                return "redirect:/login";
            }
            return "redirect:/";
        }

        @PostMapping("/register")
        public String registerUser(@Valid @ModelAttribute("Usuario")Usuario usuario, BindingResult bindingResult,Model model) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("listRoles", this.rolRepository.findAll());
                model.addAttribute("Usuario", usuario);
                return "Registro";
            }
            try {
                userService.save(usuario);
                return "redirect:/login";
            } catch (UserException e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("listRoles", this.rolRepository.findAll());
                return "Registro";
            }
        }

        @PostMapping("/EnviarActualizacion")
        public String ActualizarUSer(@Valid @ModelAttribute("Usuario")Usuario usuario, BindingResult bindingResult,Model model) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("listRoles", this.rolRepository.findAll());
                model.addAttribute("Usuario", usuario);
                return "Actualizar";
            }
            userService.Actualizar(usuario.getId(),usuario);
            return "redirect:/login-success";
        }
    }
