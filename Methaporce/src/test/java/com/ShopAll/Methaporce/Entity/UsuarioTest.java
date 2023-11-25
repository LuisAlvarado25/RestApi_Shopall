package com.ShopAll.Methaporce.Entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setCorreo("john@example.com");
        usuario.setEdad(18);
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación. Violaciones encontradas: " + violations);
    }
    @Test
    public void testInvalidUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Jo");  // Nombre demasiado corto
        usuario.setCorreo("correo-invalido");  // Correo con formato incorrecto
        usuario.setEdad(17);  // Edad menor de lo permitido
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(3, violations.size(), "Debería haber 4 violaciones de validación. Violaciones encontradas: " + violations);
    }

}