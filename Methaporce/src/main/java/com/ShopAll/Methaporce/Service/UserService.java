package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Models.Direccion;
import com.ShopAll.Methaporce.Models.Usuario;
import com.ShopAll.Methaporce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                // Devuelve una respuesta de conflicto con un mensaje de error
                return new ResponseEntity<>(
                        Map.of("message", "El correo ya está registrado"),
                        HttpStatus.CONFLICT
                );
            }

            // Asigna las direcciones al usuario antes de guardar
            for (Direccion direccion : usuario.getDirecciones()) {
                direccion.setUsuario(usuario);
            }

            // Guarda el usuario y sus direcciones en una transacción
            userRepository.save(usuario);

            // Devuelve una respuesta de éxito
            return new ResponseEntity<>(
                    Map.of("message", "Usuario registrado con éxito"),
                    HttpStatus.OK
            );}
}
