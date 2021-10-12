package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.config.properties.MessageProperty;
import br.com.livresbs.livres.dto.AlteracaoItemCarrinhoDTO;
import br.com.livresbs.livres.dto.AvaliacaoPedidoDTO;
import br.com.livresbs.livres.dto.CheckoutDTO;
import br.com.livresbs.livres.dto.OperacaoAvaliacaoPedido;
import br.com.livresbs.livres.exception.CarrinhoVazioException;
import br.com.livresbs.livres.exception.LivresException;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceImplTest {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private MetodoPagamentoRepository metodoPagamentoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ConsumidorRepository consumidorRepository;

    @Mock
    private MessageProperty messageProperty;

    @Mock
    private EnderecoEntregaRepository enderecoEntregaRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoServiceImpl;

    @Test
    void checkout__carrinho_vazio() {
        //Given
        String cpf = "31522933808";
        String mensagemException = "teste";

        //When
        when(carrinhoRepository.findByConsumidorCpf(cpf)).thenReturn(new ArrayList<>());
        when(messageProperty.getMenssagemCarrinhoVazio()).thenReturn(mensagemException);

        //Then
        CarrinhoVazioException exception = assertThrows(CarrinhoVazioException.class, () -> {pedidoServiceImpl.checkout(cpf);});
        assertEquals(mensagemException, exception.getMessage());
    }

     @Test
     void checkout__com_sucesso() {
         Produto produto1 = new Produto();
         produto1.setNome("Batata");

         Cotacao cotacao1 = new Cotacao();
         cotacao1.setProduto(produto1);
         cotacao1.setPreco(BigDecimal.valueOf(3.50));

         Carrinho carrinho1 = new Carrinho();
         carrinho1.setQuantidade(3.0);
         carrinho1.setCotacao(cotacao1);

         Produto produto2 = new Produto();
         produto2.setNome("Banana");

         Cotacao cotacao2 = new Cotacao();
         cotacao2.setProduto(produto2);
         cotacao2.setPreco(BigDecimal.valueOf(2.00));

         Carrinho carrinho2 = new Carrinho();
         carrinho2.setQuantidade(5.0);
         carrinho2.setCotacao(cotacao2);

         Produto produto3 = new Produto();
         produto3.setNome("Goiaba");

         Cotacao cotacao3 = new Cotacao();
         cotacao3.setProduto(produto3);
         cotacao3.setPreco(BigDecimal.valueOf(6.25));

         Carrinho carrinho3 = new Carrinho();
         carrinho3.setQuantidade(2.0);
         carrinho3.setCotacao(cotacao3);

         List<Carrinho> lista = new ArrayList<>();
         lista.add(carrinho1);
         lista.add(carrinho2);
         lista.add(carrinho3);

         List<MetodoPagamento> lista2 = new ArrayList<>();

         when(carrinhoRepository.findByConsumidorCpf("31522933808")).thenReturn(lista);
         when(metodoPagamentoRepository.findAll()).thenReturn(lista2);

         CheckoutDTO checkoutdto = pedidoServiceImpl.checkout("31522933808");

         assertTrue(checkoutdto.getValorTotal().equals(new BigDecimal(33.00).setScale(2)));
    }

    @Test
    void avaliarPedido__pedido_nao_achado() {
        //Given
        Long idPedido = 1L;
        AvaliacaoPedidoDTO avaliacao = new AvaliacaoPedidoDTO();

        //Then
        LivresException exception = assertThrows(LivresException.class, () -> pedidoServiceImpl.avaliarPedido(idPedido, avaliacao));
        assertEquals("Pedido não achado", exception.getMessage());
    }

    @Test
    void avaliarPedido__item_do_pedido_nao_achado() {
        //Given
        Long idPedido = 1L;

        AvaliacaoPedidoDTO avaliacao = new AvaliacaoPedidoDTO();
        avaliacao.setOperacao(OperacaoAvaliacaoPedido.APROVAR_PEDIDO);

        AlteracaoItemCarrinhoDTO alteracao = new AlteracaoItemCarrinhoDTO();
        alteracao.setId(idPedido);

        avaliacao.setAlteracoes(Collections.singletonList(alteracao));

        //When
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(new Pedido()));
        when(itemPedidoRepository.findById(idPedido)).thenReturn(Optional.empty());

        //Then
        LivresException exception = assertThrows(LivresException.class, () -> pedidoServiceImpl.avaliarPedido(idPedido, avaliacao));
        assertEquals("Item do pedido não achado", exception.getMessage());
    }

    @Test
    void avaliarPedido__com_sucesso() {
        //Given
        Long idPedido = 1L;

        AvaliacaoPedidoDTO avaliacao = new AvaliacaoPedidoDTO();
        avaliacao.setOperacao(OperacaoAvaliacaoPedido.APROVAR_PEDIDO);

        //When
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(new Pedido()));

        //Then
        pedidoServiceImpl.avaliarPedido(idPedido, avaliacao);

        verify(pedidoRepository, times(1)).save(any());
    }

}