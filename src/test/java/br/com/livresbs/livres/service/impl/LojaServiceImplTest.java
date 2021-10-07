package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.config.properties.ApplicationProperty;
import br.com.livresbs.livres.dto.ProdutosDisponiveisDTO;
import br.com.livresbs.livres.model.CategoriaProduto;
import br.com.livresbs.livres.model.Cotacao;
import br.com.livresbs.livres.model.DataEntrega;
import br.com.livresbs.livres.model.Produto;
import br.com.livresbs.livres.repository.CotacaoRepository;
import br.com.livresbs.livres.repository.DataEntregaRepository;
import br.com.livresbs.livres.repository.PreComunidadeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LojaServiceImplTest {

    @Mock
    private DataEntregaRepository dataEntregaRepository;

    @Mock
    private CotacaoRepository cotacaoRepository;

    @Mock
    private ApplicationProperty applicationProperty;

    @InjectMocks
    private LojaServiceImpl lojaService;

    @Test
    void listarProdutosDisponiveisCompraConsumidor__com_sucesso() {
        //Given
        String cpf = "23898209328";
        Integer pagina = 1;
        List<String> categorias = new ArrayList<String>();
        Produto produto = new Produto();
        produto.setNome("Produto teste");
        produto.setCategoria("Categoria teste");
        produto.setUnidade("Unidade teste");

        Cotacao cotacao = new Cotacao();
        cotacao.setProduto(produto);
        cotacao.setId(1L);
        cotacao.setPreco(BigDecimal.ONE);

        Page<Cotacao> cotacoes = new PageImpl<Cotacao>(Collections.singletonList(cotacao));

        DataEntrega dataEntrega = new DataEntrega();

        PageRequest pageRequest = PageRequest.of(
                pagina.intValue() - 1,
                10
        );


        //When
        Mockito.when(dataEntregaRepository.encontrarDataEntregaAtivaConsumidor(cpf)).thenReturn(dataEntrega);
        Mockito.when(applicationProperty.getQuantidadeIntesPagina()).thenReturn(10);
        Mockito.when(cotacaoRepository.findByDataEntrega(dataEntrega, pageRequest)).thenReturn(cotacoes);

        //Then
        ProdutosDisponiveisDTO resposta = lojaService.listarProdutosDisponiveisCompraConsumidor(cpf, pagina, categorias);

        Mockito.verify(cotacaoRepository, Mockito.times(1)).findByDataEntrega(dataEntrega, pageRequest);

        Assert.assertEquals(resposta.getProdutos().size(), 1);
        Assert.assertEquals(resposta.getTotalPaginas().intValue(), 1);
        Assert.assertEquals(resposta.getPaginaAtual().intValue(), 1);
    }
}