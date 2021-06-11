package br.com.livresbs.livres.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer CEP;
    private String cidade;
    private String estado;
    private String endereco;
    private Integer numero;
    private String complemento;

    @ManyToMany(mappedBy = "enderecos")
    @JsonIgnore
    private List<Consumidor> consumidores;

}
