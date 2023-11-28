package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.*;
import com.ShopAll.Methaporce.Repository.CarritoRepository;
import com.ShopAll.Methaporce.Repository.ComentarioRepository;
import com.ShopAll.Methaporce.Repository.ListaCarritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    @Autowired
    private ListaCarritoRepository listaCarritoRepository;

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public void Save(Carrito carrito){
        carritoRepository.save(carrito);
    }
    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
        return carritoRepository.findByUser(usuario);
    }
    public ListaCarrito obtenerListaCarritoPorProductoYCarrito(Long productoId, Long carritoId) {
        return listaCarritoRepository.findByProductoIdAndCarritoId(productoId, carritoId);
    }

    @Transactional
    public void borrarListaCarritoPorProductoYCarrito(Long productoId, Long carritoId) {

        ListaCarrito listaCarrito = listaCarritoRepository.findByProductoIdAndCarritoId(productoId, carritoId);
        if (listaCarrito != null) {
            listaCarritoRepository.delete(listaCarrito);
        }
    }
}
