package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private  UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private Usuario usuario;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario=new Usuario();
        usuario.setNombre("Luis");
        usuario.setEdad(18);
        usuario.setPassword("1234");
        usuario.setCorreo("Juan@gmail.com");
    }
    @Test
    void getUsers1() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(usuario));
        assertNotNull(userService.getUsers1());
    }
    @Test
    void testGetUserByCorreoWhenUserExists() {
        when(userRepository.findUsuarioByCorreo("Juan@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario resultUser = userService.getUserByCorreo("Juan@gmail.com");
        verify(userRepository).findUsuarioByCorreo("Juan@gmail.com");
        assertNotNull(resultUser);
        assertEquals("Luis", resultUser.getNombre());
        assertEquals("1234", resultUser.getPassword());
        assertEquals("Juan@gmail.com", resultUser.getCorreo());
    }
    @Test
    void testSaveNewUserSinDirecciones() {
        // Mocking
        Usuario user = new Usuario();
        user.setCorreo("Juan@example.com");
        user.setPassword("1234");
        when(userRepository.findUsuarioByCorreo("Juan@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("1234");
        when(userRepository.save(any(Usuario.class))).thenReturn(user);
        Usuario savedUser = userService.save(user);
        verify(userRepository).findUsuarioByCorreo("Juan@example.com");
        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(any(Usuario.class));
        assertNotNull(savedUser);
        assertEquals("Juan@example.com", savedUser.getCorreo());
        assertEquals("1234", savedUser.getPassword());
    }
    @Test
    void testSaveNewUserConDirecciones() {
        // Mocking
        Usuario user = new Usuario();
        user.setCorreo("Juan@example.com");
        user.setPassword("1234");
        Direccion direccion1 = new Direccion();
        Direccion direccion2 = new Direccion();
        user.setDirecciones(List.of(direccion1, direccion2));
        when(userRepository.findUsuarioByCorreo("Juan@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("1234");
        when(userRepository.save(any(Usuario.class))).thenReturn(user);
        Usuario savedUser = userService.save(user);
        verify(userRepository).findUsuarioByCorreo("Juan@example.com");
        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(any(Usuario.class));
        assertNotNull(savedUser);
        assertEquals("Juan@example.com", savedUser.getCorreo());
        assertEquals("1234", savedUser.getPassword());
        assertNotNull(savedUser.getDirecciones());
        assertEquals(2, savedUser.getDirecciones().size());
    }
    @Test
    void testGuardarUnUsuarioExistente() {
        Usuario user = new Usuario();
        user.setCorreo("Juan@example.com");
        when(userRepository.findUsuarioByCorreo("Juan@example.com")).thenReturn(Optional.of(user));
        assertThrows(UserException.class, () -> userService.save(user));
        verify(userRepository).findUsuarioByCorreo("Juan@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(Usuario.class));
    }
    @Test
    void testUsuariosNoExisten() {
        // Mocking
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        boolean result = userService.userNotExists();
        verify(userRepository).findAll();
        assertTrue(result);
    }

    @Test
    void testLoadUserByUsernameWhenUserExists() {
        // Mocking
        String username = "Luis";
        when(userRepository.findUsuarioByNombre(username)).thenReturn(Optional.of(usuario));
        UserDetails userDetails = userService.loadUserByUsername(username);
        verify(userRepository).findUsuarioByNombre(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());

    }
    @Test
    void testUseariosExisten() {
        // Mocking
        List<Usuario> userList = List.of(new Usuario(), new Usuario()); // Simula un repositorio no vacío
        when(userRepository.findAll()).thenReturn(userList);
        boolean result = userService.userNotExists();
        verify(userRepository).findAll();
        assertFalse(result);
    }

}