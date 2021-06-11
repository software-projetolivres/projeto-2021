package br.com.livresbs.livres.dto;

import java.util.List;
import java.util.Set;

import br.com.livresbs.livres.model.EnderecoEntrega;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumidorDTO{
    private String id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String senha;
    private Long precomunidade;
    private Set<Integer> perfis;
    private List<EnderecoEntrega> enderecos;

}
