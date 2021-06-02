package br.com.livresbs.livres.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tb_consumidor")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Consumidor extends Usuario{

    @NotBlank
    private String cpf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "precomunidade_id")
    @JsonBackReference
    private PreComunidade precomunidade;

    @ManyToMany
    @JoinTable(
        name = "consumidor_endereco",
        joinColumns = @JoinColumn(name = "consumidor_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<EnderecoEntrega> enderecos;

}
