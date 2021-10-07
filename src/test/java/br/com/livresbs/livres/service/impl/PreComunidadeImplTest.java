package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.repository.PreComunidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PreComunidadeImplTest {

    @Mock
    private PreComunidadeRepository pcr;

    @InjectMocks
    private PreComunidadeImpl preComunidade;

    @Test
    public void excluiPreComunidade__deve_retorrnar_pre_comunidade_nao_encontrada() {
        //Given
        Long id = 1L;

        //When
        Mockito.when(pcr.existsById(id)).thenReturn(false);

        //Then
        ResponseEntity<String> resposta = preComunidade.excluiPreComunidade(id);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Pre Comunidade Não Encontrada!", resposta.getBody());
    }

    @Test
    public void excluiPreComunidade__deve_retorrnar_pre_comunidade_excluida_com_sucesso() {
        //Given
        Long id = 1L;

        //When
        Mockito.when(pcr.existsById(id)).thenReturn(true);

        //Then
        ResponseEntity<String> resposta = preComunidade.excluiPreComunidade(id);

        Mockito.verify(pcr, Mockito.times(1)).deleteById(id);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Excluído com Sucesso!", resposta.getBody());
    }

}