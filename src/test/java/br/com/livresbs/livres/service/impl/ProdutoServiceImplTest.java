package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.dto.ProdutoDTO;
import br.com.livresbs.livres.model.CategoriaProduto;
import br.com.livresbs.livres.model.Produto;
import br.com.livresbs.livres.repository.CategoriaRepository;
import br.com.livresbs.livres.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepo;

    @Mock
    private CategoriaRepository categoriaRepo;

    @InjectMocks
    ProdutoServiceImpl produtoService;


    @Test
    void cadastrar__com_sucesso() {
        //Given
        ProdutoDTO produtoDTO = ProdutoDTO.builder().id(1).categoria(1).nome("Produto teste").build();
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setCategoria("Categoria");
        categoriaProduto.setId(1);
        Optional<CategoriaProduto> categoria = Optional.of(categoriaProduto);


        //When
        Mockito.when(categoriaRepo.findById(produtoDTO.getCategoria())).thenReturn(categoria);

        //Then
        ResponseEntity<String> resposta = produtoService.cadastrar(produtoDTO);

        Mockito.verify(produtoRepo, Mockito.times(1)).save(Mockito.any());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Cadastrado com Sucesso!", resposta.getBody());
    }

    @Test
    void deletarProduto__categoria_nao_encontrada() {
        //Given
        Integer id = 1;

        //Then
        ResponseEntity<String> resposta = produtoService.deletarProduto(id);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Categoria não Encontrada!", resposta.getBody());
        Mockito.verify(produtoRepo, Mockito.times(1)).existsById(id);
    }
}