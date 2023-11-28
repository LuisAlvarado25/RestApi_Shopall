package com.ShopAll.Methaporce.Controller;

import com.ShopAll.Methaporce.Entity.*;
import com.ShopAll.Methaporce.Service.CarritoService;
import com.ShopAll.Methaporce.Service.ProductService;
import com.ShopAll.Methaporce.Service.TransaccionService;
import com.ShopAll.Methaporce.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.*;

@Controller
@CrossOrigin(origins="*")
public class CarritoController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productoService;
    @Autowired
    private CarritoService carritoService;

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/carrito")
    public String verCarrito(@AuthenticationPrincipal UserDetails userDetails, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String nombreUsuario1 = userDetails.getUsername();
            model.addAttribute("nombreUsuario", nombreUsuario1);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            boolean esVendedor = authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_Vendedor"));
            model.addAttribute("esVendedor", esVendedor);
            String nombreUsuario = authentication.getName();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuario);
            if (carrito != null) {
                List<ListaCarrito> productosEnCarrito = carrito.getListaCarritos();
                model.addAttribute("productosEnCarrito", productosEnCarrito);
                double total = calcularTotal(productosEnCarrito);
                model.addAttribute("total", total);
            }
        }

        return "carrito";
    }
    private double calcularTotal(List<ListaCarrito> productosEnCarrito) {
        double total = 0.0;
        for (ListaCarrito item : productosEnCarrito) {
            // Multiplicar la cantidad por el precio y sumar al total
            total += item.getCantidad() * item.getProducto().getPrecio();
        }
        return total;
    }

    @PostMapping("/agregar-al-carrito")
    public String agregarAlCarrito(@RequestParam("productoId") Long productoId,
                                   @RequestParam("cantidad") int cantidad,
                                   HttpSession session, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            String nombreUsuario = authentication.getName();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            Carrito carrito = obtenerOcrearCarrito(usuario, session);
            Producto producto = productoService.obtenerProductoPorId(productoId);
            if (!productoEstaEnCarrito(carrito, producto)) {
                if (producto != null && cantidad > 0 && cantidad <= producto.getCantidad()) {
                ListaCarrito listaCarrito = new ListaCarrito();
                listaCarrito.setProducto(producto);
                listaCarrito.setCantidad(cantidad);
                listaCarrito.setCarrito(carrito);
                carrito.getListaCarritos().add(listaCarrito);
                carritoService.Save(carrito);
                }
            }
            return "redirect:/Productos";
        }
        return "redirect:/login";
    }
    private boolean productoEstaEnCarrito(Carrito carrito, Producto producto) {
        // Verificar si el producto ya está en el carrito
        return carrito.getListaCarritos().stream()
                .anyMatch(listaCarrito -> listaCarrito.getProducto().equals(producto));
    }
    private Carrito obtenerOcrearCarrito(Usuario usuario, HttpSession session) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuario);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUser(usuario);
            session.setAttribute("carrito", carrito);
        }

        return carrito;
    }
    @PostMapping("/actualizar-cantidad/{productoId}")
    public String actualizarCantidad(@PathVariable Long productoId,
                                     @RequestParam("cantidad") int nuevaCantidad,HttpSession session,
                                     Authentication authentication) {
        System.out.println("Actualizar");
        if (authentication != null && authentication.isAuthenticated()) {

            String nombreUsuario = authentication.getName();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            Carrito carrito = obtenerOcrearCarrito(usuario,session);
            ListaCarrito listaCarrito = carritoService.obtenerListaCarritoPorProductoYCarrito(productoId, carrito.getId());

            if (listaCarrito != null) {
                listaCarrito.setCantidad(nuevaCantidad);
                carritoService.Save(carrito);
            }
        }
        return "redirect:/carrito";
    }

    @PostMapping("/borrar-producto/{productoId}")
    public String borrarProductoDelCarrito(@PathVariable Long productoId, HttpSession session, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            String nombreUsuario = authentication.getName();
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            Carrito carrito = obtenerOcrearCarrito(usuario, session);
            carritoService.borrarListaCarritoPorProductoYCarrito(productoId, carrito.getId());
        }
    return "redirect:/carrito";
    }
    @PostMapping("/generar-transaccion")
    public String generarTransaccion(@AuthenticationPrincipal UserDetails userDetails, Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Usuario usuario = userService.getUserByUserName(nombreUsuario);
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuario);
        if (verificarStockSuficiente(carrito.getListaCarritos())) {
        Transaccion transaccion = new Transaccion();
        transaccion.setUsuario(usuario);
        transaccion.setCarrito(carrito);
        transaccion.setTotal(calcularTotal(carrito.getListaCarritos()));
        transaccionService.guardarTransaccion(transaccion);
        restarStockProductos(carrito.getListaCarritos());
        limpiarCarrito(carrito);

        return "redirect:/carrito";
        }
        return "redirect:/carrito";
    }
    public void limpiarCarrito(Carrito carrito) {
        carrito.getListaCarritos().clear();
        carritoService.Save(carrito);
    }


    private boolean verificarStockSuficiente(List<ListaCarrito> listaCarritos) {
        for (ListaCarrito listaCarrito : listaCarritos) {
            Producto producto = listaCarrito.getProducto();
            int cantidadEnCarrito = listaCarrito.getCantidad();
            if (cantidadEnCarrito > producto.getCantidad()) {
                return false; // No hay suficiente stock
            }
        }
        return true; // Hay suficiente stock para todos los productos en el carrito
    }

    private void restarStockProductos(List<ListaCarrito> listaCarritos) {
        for (ListaCarrito listaCarrito : listaCarritos) {
            Producto producto = listaCarrito.getProducto();
            int cantidadEnCarrito = listaCarrito.getCantidad();
            int nuevoStock = producto.getCantidad() - cantidadEnCarrito;
            producto.setCantidad(nuevoStock);
            productoService.Save(   producto);
        }
    }
}
