package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.model.*;
import br.com.livresbs.livres.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest
@ActiveProfiles("test")
public class CarrinhoServiceImplTest {

    @Autowired
    private ConsumidorRepository repositoryConsumidor;

    @Autowired
    private CotacaoRepository repositoryCotacao;

    @Autowired
    private CarrinhoRepository repositoryCarrinho;

    @Autowired
    private ProdutoRepository repositoryProduto;

    @Autowired
    private CarrinhoServiceImpl carrinhoServiceImpl;

    @Autowired
    private PreComunidadeRepository preComunidadeRepository;

    @Test
    void sincronizarProduto() {
        PreComunidade precomunidade1 = new PreComunidade();
        precomunidade1.setNome("Unisantos");
        preComunidadeRepository.save(precomunidade1);
        Set<TipoPerfil> adm = new HashSet<>();
        Consumidor consumidor1 = new Consumidor();
        consumidor1.setCpf("12345678910");
        consumidor1.setNome("Gustavo");
        consumidor1.setSobrenome("Moraes");
        consumidor1.setSenha("senha");
        consumidor1.setEmail("gustavo@email.com");
        consumidor1.setPrecomunidade(precomunidade1);
        repositoryConsumidor.save(consumidor1);

        Produto produto1 = new Produto();
        produto1.setNome("Batata");
        repositoryProduto.save(produto1);

        Cotacao cotacao1 = new Cotacao();
        cotacao1.setProduto(produto1);
        cotacao1.setPreco(BigDecimal.valueOf(3.50));
        repositoryCotacao.save(cotacao1);

        carrinhoServiceImpl.sincronizarProduto(consumidor1.getId(), cotacao1.getId(), 2.0);

        List<Carrinho> carrinho1 = repositoryCarrinho.findByConsumidorCpf("31522933808");

        Assertions.assertEquals(0, carrinho1.size());
    }
}