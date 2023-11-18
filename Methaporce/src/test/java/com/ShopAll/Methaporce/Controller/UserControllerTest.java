package com.ShopAll.Methaporce.Controller;


import com.ShopAll.Methaporce.Entity.Usuario;
import com.ShopAll.Methaporce.Exception.UserException;
import com.ShopAll.Methaporce.Repository.UserRepository;
import com.ShopAll.Methaporce.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;



@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getUserSuccess() throws Exception {
        String correo = "juanluis";
        Usuario usuario= new Usuario();
        usuario.setCorreo("juanluis");
        given(userService.getUserByCorreo(correo)).willReturn(usuario);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopall/Usuario/{correo}", correo))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void getUserNotFound() throws Exception {
        String correoNoExistente = "correoquenoexiste";
        // Configurar el comportamiento del servicio para devolver Optional.empty()
      //  given(userService.getUserByCorreo(correoNoExistente)).willReturn(null);
        // Realizar la solicitud GET y verificar que obtienes un 404
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopall/Usuario/{correo}", correoNoExistente))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createUserSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopall/Usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Luis\", \"edad\": 18, \"correo\": \"juanluis@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void createUserError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopall/Usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": , \"edad\": 18, \"correo\": \"juanluis@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createUserValidation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopall/Usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Luis\", \"edad\": 18, \"correo\": \"juanluis@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void createUserValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopall/Usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Luis\", \"edad\": 17, \"correo\": \"juanluis@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
