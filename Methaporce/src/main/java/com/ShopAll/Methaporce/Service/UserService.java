package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.RolRepository;
import com.ShopAll.Methaporce.Repository.UserRepository;
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
    public Usuario save(Usuario user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        Optional<Usuario> existingUser = userRepository.findUsuarioByCorreo(user.getCorreo());

            user.setPassword(encryptedPassword);
            if (user.getDirecciones() != null) {
                user.getDirecciones().forEach(direccion -> direccion.setUsuario(user));
            }

        if (existingUser.isPresent()) {
            throw new UserException("El correo ya esta registrado");
        }
        return userRepository.save(user);
    }
    public boolean userNotExists(){
        return userRepository.findAll().isEmpty();
    }
    public Usuario Actualizar(Long id,Usuario user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        Optional<Usuario> existingUserOptional = userRepository.findUsuarioByCorreo(user.getCorreo());

        if (existingUserOptional.isPresent()) {
            Usuario usuarioExistente = existingUserOptional.get();
            usuarioExistente.setEdad(user.getEdad());
            usuarioExistente.setPassword(encryptedPassword);
            usuarioExistente.setNombre(user.getNombre());
            usuarioExistente.setApellido(user.getApellido());
            usuarioExistente.setCorreo(user.getCorreo());
            usuarioExistente.getDirecciones().clear();
            user.getDirecciones().forEach(direccion -> {
                    direccion.setUsuario(usuarioExistente);
                    usuarioExistente.getDirecciones().add(direccion);
                });

            usuarioExistente.setRoles(user.getRoles());

            return userRepository.save(usuarioExistente);
        } else {

            return null;
        }
    }
    public ResponseEntity<String> deleteUsuario(Long id) {
        if (userRepository.existsById(id)) {
            Usuario usuario= userRepository.findById(id).get();
            usuario.getDirecciones().forEach(direccion -> direccion.setUsuario(null));
            usuario.setDirecciones(null);
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
