package br.com.livresbs.livres.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdministratorDTO {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
}
