package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RolServiceTest {

    @Mock
    private RolRepository roleRepository;

    // Supongamos que tienes un servicio que contiene el método save
    @InjectMocks
    private RolService rolService;

    @BeforeEach
    public void setUp() {
        // Inicializa el mock antes de cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveRol() {

        Rol rol = new Rol("Vendedor");
        when(roleRepository.save(any(Rol.class))).thenReturn(rol);
        Rol savedRol = rolService.save(rol);
        verify(roleRepository, times(1)).save(any(Rol.class));
        assertEquals(rol, savedRol);
    }

}