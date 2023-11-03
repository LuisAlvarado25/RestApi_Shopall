package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Repository.UserRepository;
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

    public List<Usuario> getNames(){
        return this.userRepository.findAll();
    }

    public ResponseEntity<Object> newUsuario(Usuario usuario) {
            Optional<Usuario> existingUser = userRepository.findUsuarioByCorreo(usuario.getCorreo());

            if (existingUser.isPresent()) {

                return new ResponseEntity<>(
                        Map.of("message", "El correo ya está registrado"),
                        HttpStatus.CONFLICT
                );
            }


            for (Direccion direccion : usuario.getDirecciones()) {
                direccion.setUsuario(usuario);
            }


            userRepository.save(usuario);


            return new ResponseEntity<>(
                    Map.of("message", "Usuario registrado con éxito"),
                    HttpStatus.OK
            );}

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
