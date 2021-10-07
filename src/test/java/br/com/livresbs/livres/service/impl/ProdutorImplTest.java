package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.model.Produtor;
import br.com.livresbs.livres.repository.ProdutorRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutorImplTest {

    @Mock
    ProdutorRepository pr;

    @InjectMocks
    ProdutorImpl produtor;

    @Test
    void listarUnicoProdutor__retorna_nulo() {
        //Given
        String id = "1";

        //When
        Mockito.when(pr.existsById(id)).thenReturn(false);

        //Then
        Produtor resposta = produtor.listarUnicoProdutor(id);

        Assert.assertNull(resposta);
    }

    @Test
    void cadastraProdutor__com_sucesso() {
        //Given
        Produtor entrada = new Produtor();
        entrada.setProdutor("Produtor");
        entrada.setId(1);

        //When
        Mockito.when(pr.existsById(entrada.getProdutor())).thenReturn(false);

        //Then
        ResponseEntity<String> resposta = produtor.cadastraProdutor(entrada);

        Mockito.verify(pr, Mockito.times(1)).save(entrada);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Produtor cadastrado com sucesso!", resposta.getBody());
    }

    @Test
    void atualizaProduto__com_sucesso() {
        //Given
        Produtor entrada = new Produtor();
        entrada.setId(1);
        entrada.setProdutor("produtor teste");

        //When
        Mockito.when(pr.existsById(entrada.getProdutor())).thenReturn(true);

        //Then
        ResponseEntity<String> resposta = produtor.atualizaProduto(entrada);

        Mockito.verify(pr, Mockito.times(1)).save(entrada);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Produtor cadastrado com sucesso!", resposta.getBody());
    }

    @Test
    void deletarProduto__com_sucesso() {
        //Given
        String id = "1";

        //When
        Mockito.when(pr.existsById(id)).thenReturn(true);

        //Then
        ResponseEntity<String> resposta = produtor.deletarProduto(id);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Produtor deletado com sucesso!", resposta.getBody());
    }
}