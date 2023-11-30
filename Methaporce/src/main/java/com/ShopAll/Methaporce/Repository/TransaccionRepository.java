package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Transaccion;
import com.ShopAll.Methaporce.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
 List<Transaccion> findByUsuario(Usuario usuario);


 }