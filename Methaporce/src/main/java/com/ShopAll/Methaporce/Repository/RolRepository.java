package com.ShopAll.Methaporce.Repository;

import com.ShopAll.Methaporce.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findRolByName(String name);
}
