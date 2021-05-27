package br.com.livresbs.livres.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumidorDTO implements Serializable{
    private String id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;
    private Long precomunidade;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        System.out.println("Entrei aqui no CPF");
        this.cpf = cpf;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getPrecomunidade() {
        return this.precomunidade;
    }

    public void setPrecomunidade(Long precomunidade) {
        this.precomunidade = precomunidade;
    }

}
