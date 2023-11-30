package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.DetalleTransaccion;
import com.ShopAll.Methaporce.Entity.Producto;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
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
            String nombreUsuario = userDetails.getUsername();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            List<Transaccion> transacciones = transaccionService.obtenerTransaccionesPorUsuario(usuario);
            model.addAttribute("transacciones", transacciones);
            return "Transacciones";
    }
    @GetMapping("/detalles-transaccion/{transaccionId}")
    public String mostrarDetallesTransaccion(@PathVariable Long transaccionId, Model model) {
        Transaccion transaccion = transaccionService.obtenerTransaccionPorId(transaccionId);
        if (transaccion == null) {
            return "redirect:/transacciones"; // Redirigir a la lista de transacciones
        }
        Usuario usuario = transaccion.getUsuario();
        List<DetalleTransaccion> detallesTransaccion = transaccion.getDetallesTransaccion();
        List<Producto> productosTransaccion = new ArrayList<>();
        for (DetalleTransaccion detalle : detallesTransaccion) {
            Producto producto = detalle.getProducto();
            producto.setCantidad(detalle.getCantidad());
            productosTransaccion.add(producto);
        }
        model.addAttribute("transaccion", transaccion);
        model.addAttribute("usuario", usuario);
        model.addAttribute("productosTransaccion", productosTransaccion);
        return "DetallesTransaccion";
    }

}
