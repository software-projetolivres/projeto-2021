package br.com.livresbs.livres.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.livresbs.livres.model.Usuario;
import br.com.livresbs.livres.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario user = repository.findByEmail(login);

        if (user != null){
            return new UserDetailsImpl(user.getId(), user.getEmail(), user.getSenha(), user.getPerfis());
        }
        
        throw new UsernameNotFoundException(login);
    }
    
}
