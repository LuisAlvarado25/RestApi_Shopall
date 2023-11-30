package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.DireccionesRepository;
import com.ShopAll.Methaporce.Repository.RolRepository;
import com.ShopAll.Methaporce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private DireccionesService direccionesService;
    @Autowired
    private DireccionesRepository direccionesRepository;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  List<Usuario> getUsers1() {
        return userRepository.findAll();
    }

    public  Usuario getUserByid(Long id){
        return  userRepository.findById(id).orElse(null);
    }

    public Usuario getUserByCorreo(String correo) {
        return userRepository.findUsuarioByCorreo(correo)
                .orElse(null);
    }
    public  Usuario getUserByUserName(String Name){
        return userRepository.findUsuarioByNombre(Name)
                .orElse(null);
    }

    @Transactional
    public Usuario save(Usuario user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        Optional<Usuario> existingUser = userRepository.findUsuarioByCorreo(user.getCorreo());
        user.setPassword(encryptedPassword);
        Direccion direccion=user.getDireccion();
        if (existingUser.isPresent()) {
            throw new UserException("El correo ya esta registrado");
        }
        userRepository.save(user);
        if (direccion != null) {
            direccion.setUser(user);
        }

        return user;
    }
    public boolean userNotExists(){
        return userRepository.findAll().isEmpty();
    }
    @Transactional
    public Usuario Actualizar(Long id, Usuario user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        Optional<Usuario> existingUserOptional = userRepository.findUsuarioByCorreo(user.getCorreo());

        if (existingUserOptional.isPresent()) {
            Usuario usuarioExistente = existingUserOptional.get();
            usuarioExistente.setEdad(user.getEdad());
            usuarioExistente.setPassword(encryptedPassword);
            usuarioExistente.setNombre(user.getNombre());
            usuarioExistente.setApellido(user.getApellido());
            usuarioExistente.setCorreo(user.getCorreo());
            usuarioExistente.setTelefono(user.getTelefono());

            Direccion direccion = user.getDireccion();
            if (direccion != null) {
                // Verificar si la dirección no es nula antes de intentar acceder a sus propiedades
                usuarioExistente.getDireccion().setCalle(direccion.getCalle());
                usuarioExistente.getDireccion().setNumero(direccion.getNumero());
                usuarioExistente.getDireccion().setColonia(direccion.getColonia());
                usuarioExistente.getDireccion().setCiudad(direccion.getCiudad());
                usuarioExistente.getDireccion().setCp(direccion.getCp());
            }

            usuarioExistente.setRoles(user.getRoles());
            userRepository.save(usuarioExistente);

            return usuarioExistente;
        } else {
            // Manejar el caso cuando el Optional no contiene un valor
            return null;
        }
    }
    public ResponseEntity<String> deleteUsuario(Long id) {
        if (userRepository.existsById(id)) {
            Usuario usuario= userRepository.findById(id).get();
//            usuario.getDirecciones().forEach(direccion -> direccion.setUsuario(null));
//            usuario.setDirecciones(null);
            usuario.setRoles(null);
            userRepository.deleteById(id);
            return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Usuario> usuarioOptional=userRepository.findUsuarioByNombre(username);
       return usuarioOptional.orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado: " +username));
    }


}
