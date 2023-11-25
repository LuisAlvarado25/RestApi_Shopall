package com.ShopAll.Methaporce.Repository;


import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario,Long> {

Optional<Usuario> findUsuarioByCorreo(String correo);
Optional<Usuario> findUsuarioByNombre(String username);
}
