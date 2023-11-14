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
        Long userId = 5L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        given(userService.getUsers(userId)).willReturn(usuario);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopall/Usuario/{id}", userId)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserFailed() throws Exception {
        Long userId1 = 0L;
        given(userService.getUsers(userId1)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopall/Usuario/{id}", userId1)).
                andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createUserSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopall/Usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Luis\", \"edad\": 18, \"correo\": \"juanluis@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
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
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createUserValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopall/Usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Luis\", \"edad\": 17, \"correo\": \"juanluis@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
