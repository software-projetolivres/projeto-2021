package br.com.livresbs.livres.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Entity
@Table(name = "categoriasprodutos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaProduto {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    
    @NotBlank
	private String categoria;

}
