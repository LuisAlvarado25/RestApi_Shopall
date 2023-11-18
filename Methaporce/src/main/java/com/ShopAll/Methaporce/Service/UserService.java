package com.ShopAll.Methaporce.Service;

import com.ShopAll.Methaporce.Entity.Direccion;
import com.ShopAll.Methaporce.Entity.Rol;
import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.RolRepository;
import com.ShopAll.Methaporce.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;

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

   //Servicio para crear un Nuevo Usuario
    public Usuario newUsuario(Usuario usuario) {

        Optional<Usuario> existingUser = userRepository.findUsuarioByCorreo(usuario.getCorreo());
        if (existingUser.isPresent()) {
            throw new UserException("El correo ya esta registrado");
            }

        List<Rol> roles = usuario.getRoles();// Asigna los roles al nuevo usuario
        List<Rol> rolesActualizados = new ArrayList<>();

        for (Rol rol : roles) {
            // Verificar si el rol ya existe en la base de datos por su nombre
            Optional<Rol> rolExistente = rolRepository.findRolByName(rol.getName());

            if (rolExistente.isPresent()) {
                // Si el rol ya existe, utiliza el rol existente (actualizando el ID)
                rolesActualizados.add(rolExistente.get());
            } else {
                // Si el rol no existe, guarda el nuevo rol y usa el ID asignado por la base de datos
                Rol rolGuardado = rolRepository.save(rol);
                rolesActualizados.add(rolGuardado);
            }
        }

        // Asignar los roles actualizados al usuario
        usuario.setRoles(rolesActualizados);
        userRepository.save(usuario);
        return usuario;
    }

    //Servicio para borrar un Usuario por su Id
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

    //Servicio para actualizar la Informacion del Usuario
    public ResponseEntity<String> UpdateUsuario(Long id, Usuario usuarioActualizado) {
        if (userRepository.existsById(id)) {
            Usuario usuarioExistente = userRepository.findById(id).get();
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
            usuarioExistente.setPassword(usuarioActualizado.getPassword());
            usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            userRepository.save(usuarioExistente);
            return new ResponseEntity<>("Usuario actualizado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
