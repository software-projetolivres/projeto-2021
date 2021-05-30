package br.com.livresbs.livres.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.repository.ConsumidorRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private ConsumidorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Consumidor consumidor = repository.findByEmail(login);
        if (consumidor == null){
            throw new UsernameNotFoundException(login);
        }

        return new UserDetailsImpl(consumidor.getId(), consumidor.getEmail(), consumidor.getSenha());
    }
    
}
