package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.model.CategoriaProduto;
import br.com.livresbs.livres.repository.CategoriaRepository;
import br.com.livresbs.livres.repository.PreComunidadeRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoriaImplTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaImpl categoria;

    @Test
    void deletarCategoria__erro_categoria_nao_encontrada() {
        //Given
        Integer id = 1;

        //When
        Mockito.when(repository.existsById(id)).thenReturn(false);

        //Then
        ResponseEntity<String> resposta = categoria.deletarCategoria(id);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Categoria não Encontrada!", resposta.getBody());
    }

    @Test
    void deletarCategoria__salva_sucesso() {
        //Given
        Integer id = 1;

        //When
        Mockito.when(repository.existsById(id)).thenReturn(true);

        //Then
        ResponseEntity<String> resposta = categoria.deletarCategoria(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Deletado com Sucesso!", resposta.getBody());
    }

    @Test
    void listaCategoriaId__retorna_null_categoria_nao_existe() {
        //Given
        Integer id = 1;

        //When
        Mockito.when(repository.existsById(id)).thenReturn(false);

        //Then
        CategoriaProduto resposta = categoria.listaCategoriaId(id);

        assertNull(resposta);
    }

    @Test
    void listaCategoriaId__retorna_categoria_existente() {
        //Given
        Integer id = 1;
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(id);
        categoriaProduto.setCategoria("Teste");

        //When
        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.of(categoriaProduto));

        //Then
        CategoriaProduto resposta = categoria.listaCategoriaId(id);

        assertEquals("Teste", resposta.getCategoria());
        assertEquals(id, resposta.getId());
    }

    @Test
    void editaCategoria_erro_nao_foi_possivel_salvar_categoria() {
        //Given
        CategoriaProduto categoriaProduto = new CategoriaProduto();

        //When
        Mockito.when(repository.save(categoriaProduto)).thenReturn(null);

        //Then
        ResponseEntity<String> resposta = categoria.editaCategoria(categoriaProduto);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Não foi Possível Editar a Categoria!", resposta.getBody());
    }

    @Test
    void editaCategoria_salvar_sucesso() {
        //Given
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(1);
        categoriaProduto.setCategoria("Teste");

        //When
        Mockito.when(repository.save(categoriaProduto)).thenReturn(categoriaProduto);

        //Then
        ResponseEntity<String> resposta = categoria.editaCategoria(categoriaProduto);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Editado com Sucesso!", resposta.getBody());
    }
}