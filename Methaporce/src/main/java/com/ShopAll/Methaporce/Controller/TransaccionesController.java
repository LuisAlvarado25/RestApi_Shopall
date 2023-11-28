package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.ShopAll.Methaporce.Entity.Transaccion;
import com.ShopAll.Methaporce.Service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class TransaccionesController {
    @Autowired
    private TransaccionService transaccionService;
    @Autowired
    private UserService userService;
    public TransaccionesController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/Transacciones")
    public String mostrarTransacciones(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            String nombreUsuario1 = userDetails.getUsername();
            model.addAttribute("nombreUsuario", nombreUsuario1);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            boolean esVendedor = authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_Vendedor"));
            model.addAttribute("esVendedor", esVendedor);
            String nombreUsuario = userDetails.getUsername();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            List<Transaccion> transacciones = transaccionService.obtenerTransaccionesPorUsuario(usuario);
            model.addAttribute("transacciones", transacciones);
            return "Transacciones";
    }
}
