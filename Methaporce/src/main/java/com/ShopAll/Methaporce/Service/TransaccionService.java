package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Transaccion;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TransaccionService {

    @Autowired
    TransaccionRepository transaccionRepository;

    public void guardarTransaccion(Transaccion transaccion) {
        transaccionRepository.save(transaccion);
    }
    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAll();
    }

    public List<Transaccion> obtenerTransaccionesPorUsuario(Usuario usuario) {

        return transaccionRepository.findByUsuario(usuario);
    }

    public Transaccion obtenerTransaccionPorId(Long id) {
        Optional<Transaccion> optionalTransaccion = transaccionRepository.findById(id);
        return optionalTransaccion.orElse(null);
    }

}


