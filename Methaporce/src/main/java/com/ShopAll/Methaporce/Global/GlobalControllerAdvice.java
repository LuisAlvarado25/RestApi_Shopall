package com.ShopAll.Methaporce.Global;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Collection;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void globalAttributes(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String nombreUsuario = userDetails.getUsername();
            model.addAttribute("nombreUsuario", nombreUsuario);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            boolean esVendedor = authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_Vendedor"));
            model.addAttribute("esVendedor", esVendedor);
        }
    }
}