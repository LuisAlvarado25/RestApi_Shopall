package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.RolRepository;
import com.ShopAll.Methaporce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    //Servicio para obtener todos los Usuarios
    public  List<Usuario> getUsers1() {
        return userRepository.findAll();
    }

    //Servicio para consultar un Usuario por ID
    public Usuario getUserByCorreo(String correo) {
        return userRepository.findUsuarioByCorreo(correo)
                .orElse(null);
    }
        public Usuario save(Usuario user) {

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        Optional<Usuario> existingUser = userRepository.findUsuarioByCorreo(user.getCorreo());
        // Crea un nuevo usuario con la contraseña encriptada
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

//    //Servicio para borrar un Usuario por su Id
//    public ResponseEntity<String> deleteUsuario(Long id) {
//        if (userRepository.existsById(id)) {
//            Usuario usuario= userRepository.findById(id).get();
//            usuario.getDirecciones().forEach(direccion -> direccion.setUsuario(null));
//            usuario.setDirecciones(null);
//            usuario.setRoles(null);
//            userRepository.deleteById(id);
//            return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    //Servicio para actualizar la Informacion del Usuario
//    public ResponseEntity<String> UpdateUsuario(Long id, Usuario usuarioActualizado) {
//        if (userRepository.existsById(id)) {
//            Usuario usuarioExistente = userRepository.findById(id).get();
//            usuarioExistente.setNombre(usuarioActualizado.getNombre());
//            usuarioExistente.setApellido(usuarioActualizado.getApellido());
//            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
//            usuarioExistente.setPassword(usuarioActualizado.getPassword());
//            usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
//            userRepository.save(usuarioExistente);
//            return new ResponseEntity<>("Usuario actualizado con éxito", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Usuario> usuarioOptional=userRepository.findUsuarioByNombre(username);
       return usuarioOptional.orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado: " +username));
    }


}
