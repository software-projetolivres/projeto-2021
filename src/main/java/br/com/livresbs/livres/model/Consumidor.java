package br.com.livresbs.livres.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_consumidor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Consumidor implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @NotBlank
    private String cpf;

    @NotBlank
    private String nome;
    
    @NotBlank
    private String sobrenome;
    
    @NotBlank
    @JsonIgnore
    private String senha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "precomunidade_id")
    @NotNull
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
