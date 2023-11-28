package com.ShopAll.Methaporce.Controller;


import com.ShopAll.Methaporce.Entity.Comentario;
import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Service.ProductService;
import com.ShopAll.Methaporce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@CrossOrigin(origins="*")
public class ProductController {


    @Autowired
    private ProductService productoService;
    @Autowired
    private UserService userService;
    @GetMapping("/Productos")
    public String Productos(Model model) {
        String nombreUsuario =productoService.obtenerNombreUsuario();
        boolean esVendedor = productoService.esUsuarioVendedor();
        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("esVendedor", esVendedor);
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        model.addAttribute("productos", productos);
        return "Productos";
    }

    @GetMapping("/GestionarProductos")
    public String gestionarProductos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String nombreUsuario1=productoService.obtenerNombreUsuario();
        boolean esVendedor = productoService.esUsuarioVendedor();
        model.addAttribute("nombreUsuario", nombreUsuario1);
        model.addAttribute("esVendedor", esVendedor);
        String nombreUsuario = userDetails.getUsername();
        List<Producto> productosUsuario = productoService.obtenerProductosPorUsuario(nombreUsuario);
        model.addAttribute("productosUsuario", productosUsuario);
        return "GestionProductos";
    }
    @GetMapping("/NuevoProducto")
    public String nuevoProductoForm(@AuthenticationPrincipal UserDetails userDetails,Model model, Principal principal) {
        String nombreUsuario1= userDetails.getUsername();
        model.addAttribute("nombreUsuario", nombreUsuario1);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean esVendedor = authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_Vendedor"));
        model.addAttribute("esVendedor", esVendedor);
        String username = principal.getName();
        Usuario usuario = userService.getUserByUserName(username);
        model.addAttribute("usuarioId", usuario.getId());
        System.out.println(usuario.getId());
        model.addAttribute("nuevoProducto", new Producto());
        return "NuevoProducto";
    }

    @GetMapping("/EditarProducto/{id}")
    public String EditarProducto(@PathVariable Long id, Model model) {
        String nombreUsuario =productoService.obtenerNombreUsuario();
        boolean esVendedor = productoService.esUsuarioVendedor();
        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("esVendedor", esVendedor);
        Producto producto = productoService.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        return "EditarProducto";
    }

    @GetMapping("/borrar-producto/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
        return "redirect:/GestionarProductos";
    }
    @PostMapping("/NuevoProducto")
    public String agregarNuevoProducto(@ModelAttribute("nuevoProducto") Producto nuevoProducto,
                                       @RequestParam("usuarioId") Long usuarioId) {
        Usuario usuario = userService.getUserByid(usuarioId);
        nuevoProducto.setUsuario(usuario);
        productoService.Save(nuevoProducto);
        return "NuevoProducto";
    }

    @PostMapping("/agregar-comentario")
    public String agregarComentario(@RequestParam("comentario") String comentario,
                                    @RequestParam("productoId") Long productoId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Producto producto = productoService.obtenerProductoPorId(productoId);

        if (producto != null) {
            Comentario nuevoComentario = new Comentario();
            nuevoComentario.setComentario(comentario);
            Usuario usuario = userService.getUserByUserName(nombreUsuario);
            nuevoComentario.setUsuario(usuario);
            producto.agregarComentario(nuevoComentario);
            productoService.Save(producto);
        }


        return "Productos";
    }

    @PostMapping("/editar-producto/{id}")
    public String editarProducto(@PathVariable Long id, @ModelAttribute("producto") Producto productoEditado) {

        Producto productoExistente = productoService.obtenerProductoPorId(id);

        if (productoExistente != null) {
            productoExistente.setNombre(productoEditado.getNombre());
            productoExistente.setDescripcion(productoEditado.getDescripcion());
            productoExistente.setPrecio(productoEditado.getPrecio());
            productoExistente.setCategoria(productoEditado.getCategoria());
            productoExistente.setCantidad(productoEditado.getCantidad());
            productoService.Save(productoExistente);
        }
        return "EditarProducto";
    }
}

