package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Producto;
import com.ShopAll.Methaporce.Repository.DireccionesRepository;
import com.ShopAll.Methaporce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DireccionesService {

    @Autowired
    private DireccionesRepository direccionesRepository;
    @Transactional
    public void Save(Direccion direccion){
        direccionesRepository.save(direccion);
    }
}
