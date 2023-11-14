package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Usuario getUsers(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public Usuario newUsuario(Usuario usuario) {
            Optional<Usuario> existingUser = userRepository.findUsuarioByCorreo(usuario.getCorreo());
            if (existingUser.isPresent()) {
                throw new UserException("El correo ya esta registrado");
            }
            for (Direccion direccion : usuario.getDirecciones()) {
                direccion.setUsuario(usuario);
            }
            userRepository.save(usuario);
            return usuario;
    }

    public ResponseEntity<String> deleteUsuario(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> UpdateUsuario(Long id, Usuario usuarioActualizado) {

        if (userRepository.existsById(id)) {
            Usuario usuarioExistente = userRepository.findById(id).get();
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());

            for (Direccion direccion : usuarioExistente.getDirecciones()) {
                direccion.setUsuario(usuarioExistente);
            }

            userRepository.save(usuarioExistente);
            return new ResponseEntity<>("Usuario actualizado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
