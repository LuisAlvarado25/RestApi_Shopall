package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private RolRepository roleRepository;

    public Rol save(Rol role) {
        return roleRepository.save(role);
    }
}
