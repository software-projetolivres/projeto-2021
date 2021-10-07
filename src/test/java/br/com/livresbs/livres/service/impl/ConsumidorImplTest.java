package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.dto.ConsumidorDTO;
import br.com.livresbs.livres.exception.AuthorizationException;
import br.com.livresbs.livres.functions.ValidaCPF;
import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.model.PreComunidade;
import br.com.livresbs.livres.repository.ConsumidorRepository;
import br.com.livresbs.livres.repository.PreComunidadeRepository;
import br.com.livresbs.livres.security.JWTUtil;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConsumidorImplTest {

    @Mock
    ConsumidorRepository cons;

    @Mock
    PreComunidadeRepository pre;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private ConsumidorImpl consumidor;

    @Test
    void cadastraConsumidor__com_sucesso() {
        //Given
        ConsumidorDTO consumidorDTO =
                ConsumidorDTO
                        .builder()
                        .id("1")
                        .cpf("57693173021")
                        .email("teste@bol.com")
                        .build();

        //Then
        ResponseEntity<String> resposta = consumidor.cadastraConsumidor(consumidorDTO);

        assertEquals("Cadastrado com Sucesso!", resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void cadastraConsumidor__email_ja_cadastrado() {
        //Given
        ConsumidorDTO consumidorDTO = ConsumidorDTO
                .builder()
                .id("1")
                .cpf("57693173021")
                .nome("nome teste")
                .sobrenome("sobrenome teste")
                .senha("senhateste")
                .email("teste@email.com.br")
                .build();

        PreComunidade precomunidade = new PreComunidade();
        precomunidade.setNome("Nome precomunidade");

        //When
        Mockito.when(cons.existsByEmail(consumidorDTO.getEmail())).thenReturn(true);
        Mockito.when(pre.findById(consumidorDTO.getPrecomunidade())).thenReturn(Optional.of(precomunidade));

        //Then
        ResponseEntity<String> resposta = consumidor.cadastraConsumidor(consumidorDTO);

        assertEquals("Email já Cadastrado!", resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void editaConsumidor__com_sucesso() {
        //Given
        ConsumidorDTO consumidorDTO = ConsumidorDTO
                .builder()
                .id("1")
                .cpf("57693173021")
                .nome("nome teste")
                .sobrenome("sobrenome teste")
                .senha("senhateste")
                .email("teste@email.com.br")
                .build();

        PreComunidade precomunidade = new PreComunidade();
        precomunidade.setNome("Nome precomunidade");

        //When
        Mockito.when(cons.existsByEmail(consumidorDTO.getEmail())).thenReturn(true);
        Mockito.when(pre.findById(consumidorDTO.getPrecomunidade())).thenReturn(Optional.of(precomunidade));

        //Then
        ResponseEntity<String> resposta = consumidor.editaConsumidor(consumidorDTO);

        assertEquals("Editado com Sucesso!", resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deletarConsumidor__com_sucesso() {
        //Given
        String id = "1";

        //When
        Mockito.when(cons.existsById(id)).thenReturn(true);

        //Then
        ResponseEntity<String> resposta = consumidor.deletarConsumidor(id);

        assertEquals("Consumidor deletado com sucesso", resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void findById__com_sucesso() {
        //Given
        String id = "1";
        Consumidor entrada = new Consumidor();
        entrada.setNome("Consumidor nome");

        //When
        Mockito.when(jwtUtil.authorized(id)).thenReturn(true);
        Mockito.when(cons.findById(id)).thenReturn(Optional.of(entrada));

        //Then
        Consumidor resposta = consumidor.findById(id);

        Assert.assertEquals(resposta.getNome(), entrada.getNome());
    }
}