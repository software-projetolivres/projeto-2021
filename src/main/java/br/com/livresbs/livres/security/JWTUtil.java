package br.com.livresbs.livres.security;

import br.com.livresbs.livres.model.TipoPerfil;
import br.com.livresbs.livres.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JWTUtil implements Serializable{
    private static final Long serialVersionUID = 1L;

    @Value("${jwt.secret}")
    private String secret = "dfhdsf2483@@@#12dk";

    @Value("${jwt.expiration}")
    private Long expiration = 600000L;

    public String generateToken(String login, String nome, String cpf) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .claim("user-name", nome)
                .claim("user-cpf", cpf)
                .claim("user-login", login)
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
            return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }
            catch (Exception e) {
            return null;
        }
    }

    public boolean authorized(String id) {
        UserDetailsImpl user = UsuarioService.authenticated();
        if (user == null || (!user.hasRole(TipoPerfil.ADMIN) && !id.equals(user.getId()))) {
            return false;
        }
        return true;
    }

}
