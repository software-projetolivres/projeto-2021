package br.com.livresbs.livres.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.livresbs.livres.model.TipoPerfil;

public class UserDetailsImpl implements UserDetails{
    private static final long serialVersionUID = 1L;
    private String id;
    private String login;
    private String senha;

    public UserDetailsImpl(){

    }

    public UserDetailsImpl(String id, String login, String senha){
        super();
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public String getId() {
        return this.id;
    }

    public boolean hasRole (TipoPerfil perfil) {
        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
