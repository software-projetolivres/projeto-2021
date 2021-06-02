package br.com.livresbs.livres.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.livresbs.livres.security.UserDetailsImpl;

public class UsuarioService {
    
    public static UserDetailsImpl authenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            return (UserDetailsImpl) auth.getPrincipal();
        }
        return null;
    }
}
