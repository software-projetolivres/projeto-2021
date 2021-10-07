package br.com.livresbs.livres.builder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.livresbs.livres.model.TipoPerfil;

public class UsuarioBuilder {
    private Set<Integer> perfis = new HashSet<Integer>();

    public Set<TipoPerfil> getPerfis() {
        return perfis.stream().map( tp -> TipoPerfil.toEnum(tp))
                .collect(Collectors.toSet());
    }

    public void setPerfil(TipoPerfil perfil) {
        this.perfis.add(perfil.getCod());
    }
}
