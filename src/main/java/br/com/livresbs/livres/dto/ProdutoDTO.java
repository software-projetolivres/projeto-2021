package br.com.livresbs.livres.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoDTO {
    private Integer id;
    private String nome;
    private Integer categoria;
}
