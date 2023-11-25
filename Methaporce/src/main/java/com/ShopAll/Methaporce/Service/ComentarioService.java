package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Comentario;
import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Repository.ComentarioRepository;
import com.ShopAll.Methaporce.Repository.DireccionesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository  comentarioRepository;
    @Transactional
    public void Save(Comentario comentario){
       comentarioRepository.save(comentario);
    }
}
