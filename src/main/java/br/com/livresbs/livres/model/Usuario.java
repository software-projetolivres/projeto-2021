package br.com.livresbs.livres.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "tb_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    @Column (unique = true)
    private String email;

    @NotBlank
    @JsonIgnore
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_perfil")
    @NotBlank
    @Getter(AccessLevel.NONE)@Setter(AccessLevel.NONE)
    private Set<Integer> perfis = new HashSet<>();

    public Set<TipoPerfil> getPerfis() {
        return perfis.stream().map( tp -> TipoPerfil.toEnum(tp))
                              .collect(Collectors.toSet());
    }

    public void setPerfil(TipoPerfil perfil) {
        System.out.println("Não Entrouuu");
        this.perfis.add(perfil.getCod());
    }
    
}
