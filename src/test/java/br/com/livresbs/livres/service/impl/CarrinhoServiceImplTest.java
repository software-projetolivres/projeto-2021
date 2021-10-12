package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.model.*;
import br.com.livresbs.livres.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CarrinhoServiceImplTest {

    @Mock
    private ConsumidorRepository repositoryConsumidor;

    @Mock
    private CotacaoRepository repositoryCotacao;

    @Mock
    private CarrinhoRepository repositoryCarrinho;

    @Mock
    private ProdutoRepository repositoryProduto;

    @Mock
    private PreComunidadeRepository preComunidadeRepository;

    @InjectMocks
    private CarrinhoServiceImpl carrinhoServiceImpl;

    @Test
    void sincronizarProduto__consumidor_nao_existe() {
        //Given
        String cpf = "74642112006";
        Long cotacaoId = 1L;
        Double quantidade = 4.0;

        //When
        when(repositoryCarrinho.findByConsumidorCpfAndCotacaoId(cpf, cotacaoId)).thenReturn(Optional.empty());

        //Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {carrinhoServiceImpl.sincronizarProduto(cpf, cotacaoId, quantidade);});
        assertEquals("Consumidor não existe", exception.getMessage());
    }

    @Test
    void sincronizarProduto__estoque_nao_existe() {
        //Given
        String cpf = "74642112006";
        Long cotacaoId = 1L;
        Double quantidade = 4.0;

        //When
        when(repositoryCarrinho.findByConsumidorCpfAndCotacaoId(cpf, cotacaoId)).thenReturn(Optional.empty());
        when(repositoryConsumidor.findById(cpf)).thenReturn(Optional.of(new Consumidor()));

        //Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {carrinhoServiceImpl.sincronizarProduto(cpf, cotacaoId, quantidade);});
        assertEquals("Estoque não existe", exception.getMessage());
    }

    @Test
    void sincronizarProduto__deleta_produto_carromjp() {
        //Given
        String cpf = "74642112006";
        Long cotacaoId = 1L;
        Double quantidade = 0.0;

        //When
        Carrinho carrinho = new Carrinho();
        carrinho.setQuantidade(quantidade);

        when(repositoryCarrinho.findByConsumidorCpfAndCotacaoId(cpf, cotacaoId)).thenReturn(Optional.of(carrinho));
        when(repositoryCotacao.findById(cotacaoId)).thenReturn(Optional.of(new Cotacao()));

        //Then
        carrinhoServiceImpl.sincronizarProduto(cpf, cotacaoId, quantidade);

        verify(repositoryCarrinho, times(1)).delete(any(Carrinho.class));
    }

    @Test
    void sincronizarProduto__acrescenta_produto_carrinho() {
        //Given
        String cpf = "74642112006";
        Long cotacaoId = 1L;
        Double quantidade = 4.0;

        //When
        Carrinho carrinho = new Carrinho();
        carrinho.setQuantidade(quantidade);

        when(repositoryCarrinho.findByConsumidorCpfAndCotacaoId(cpf, cotacaoId)).thenReturn(Optional.of(carrinho));
        when(repositoryCotacao.findById(cotacaoId)).thenReturn(Optional.of(new Cotacao()));

        //Then
        carrinhoServiceImpl.sincronizarProduto(cpf, cotacaoId, quantidade);

        verify(repositoryCarrinho, times(1)).save(any(Carrinho.class));
    }

    @Test
    void sincronizarProduto__adiciona_primeiro_produto_carrinho() {
        //Given
        String cpf = "74642112006";
        Long cotacaoId = 1L;
        Double quantidade = 4.0;

        //When
        when(repositoryCarrinho.findByConsumidorCpfAndCotacaoId(cpf, cotacaoId)).thenReturn(Optional.empty());
        when(repositoryConsumidor.findById(cpf)).thenReturn(Optional.of(new Consumidor()));
        when(repositoryCotacao.findById(cotacaoId)).thenReturn(Optional.of(new Cotacao()));

        //Then
        carrinhoServiceImpl.sincronizarProduto(cpf, cotacaoId, quantidade);

        verify(repositoryCarrinho, times(1)).save(any(Carrinho.class));
    }
}