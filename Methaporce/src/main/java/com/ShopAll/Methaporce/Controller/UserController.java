    package com.ShopAll.Methaporce.Controller;

    import com.ShopAll.Methaporce.Entity.Direccion;
    import com.ShopAll.Methaporce.Entity.Rol;
    import com.ShopAll.Methaporce.Entity.Usuario;
    import com.ShopAll.Methaporce.Exception.UserException;
    import com.ShopAll.Methaporce.Repository.RolRepository;
    import com.ShopAll.Methaporce.Service.UserService;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;

    import java.security.Principal;
    import java.util.ArrayList;
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
        public String loginSuccessPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            String nombreUsuario = userDetails.getUsername();
            model.addAttribute("nombreUsuario", nombreUsuario);
            return "login-success";
        }
        @GetMapping("/login-error")
        public String loginFailurePage() {
            return "login-error";
        }
        @GetMapping("/Actualizar")
        public String Actualizar(Model model) {
            List<Rol> listRoles=rolRepository.findAll();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Obtener el nombre de usuario del principal (usuario autenticado)
            String nombreUsuario = authentication.getName();

            // Aquí deberías implementar la lógica para cargar el usuario desde la base de datos
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("Usuario" ,usuario);
        return "Actualizar";
        }
        @PostMapping("/register")
        public String registerUser(@Valid @ModelAttribute("Usuario")Usuario usuario, BindingResult bindingResult,Model model) {

            if (bindingResult.hasErrors()) {
                model.addAttribute("listRoles", this.rolRepository.findAll());
                model.addAttribute("Usuario", usuario);
                System.out.println(usuario);
                return "Registro";
            }
            userService.save(usuario);
            return "redirect:/login";
        }

        @PostMapping("/EnviarActualizacion")
        public String ActualizarUSer(@Valid @ModelAttribute("Usuario")Usuario usuario, BindingResult bindingResult,Model model) {

            if (bindingResult.hasErrors()) {
                model.addAttribute("listRoles", this.rolRepository.findAll());
                model.addAttribute("Usuario", usuario);
                System.out.println(usuario);
                return "Actualizar";
            }

            userService.Actualizar(usuario.getId(),usuario);
            return "redirect:/login-success";
        }

        @GetMapping("/Registro")
        public String Registro(Model model) {
            List<Rol> listRoles=rolRepository.findAll();
            Usuario usuario = new Usuario();
            List<Direccion> direcciones = new ArrayList<>(); // Crear una nueva lista de direcciones
            Direccion direccion = new Direccion(); // Crear una nueva instancia de Direccion
            direcciones.add(direccion); // Agregar la instancia a la lista
            usuario.setDirecciones(direcciones);
            usuario.setDirecciones(direcciones);
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("Usuario" ,usuario);
            return "Registro";
        }

        @GetMapping("/borrar-cuenta")
        public String borrarCuenta(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Verificar si el usuario está autenticado y si tiene detalles
            if (authentication != null && authentication.getDetails() != null) {
                // Suponemos que el objeto principal en la autenticación es una instancia de Usuario
                Usuario usuario = (Usuario) authentication.getPrincipal();

                // Obtener el id del usuario
                Long userId = usuario.getId();

                // Ahora puedes usar el id para realizar las operaciones necesarias, como borrar la cuenta
                userService.deleteUsuario(userId);

                return "redirect:/login";
        }
            return "redirect:/";
    }

    }
