package br.com.livresbs.livres.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_administrador")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Administrator extends Usuario{
}


    