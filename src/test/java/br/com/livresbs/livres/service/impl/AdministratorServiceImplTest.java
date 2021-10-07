package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.dto.AdministratorDTO;
import br.com.livresbs.livres.model.Administrator;
import br.com.livresbs.livres.repository.AdministratorRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdministratorServiceImplTest {

    private AdministratorRepository admin = Mockito.mock(AdministratorRepository.class);
    private BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private AdministratorServiceImpl administratorService = new AdministratorServiceImpl(admin, passwordEncoder);

    @Test
    public void cadastraAdministrator__quando_existe_admin_email_ja_cadastrado_deve_retornar_conflito() {
        // Given
        String email = "teste@unisantos.com.br";
        AdministratorDTO administratorDTO = AdministratorDTO.builder()
                .email(email)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .senha("Senha").build();

        // When
        Mockito.when(admin.existsByEmail(email)).thenReturn(true);

        // Then
        ResponseEntity<?> responseEntity = administratorService.cadastraAdministrator(administratorDTO);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    public void cadastraAdministrator__quando_nao_existe_admin_email_ja_cadastrado_deve_retornar_sucesso() {
        // Given
        String email = "teste@unisantos.com.br";
        AdministratorDTO administratorDTO = AdministratorDTO.builder()
                .email(email)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .senha("Senha").build();

        // When
        Mockito.when(admin.existsByEmail(email)).thenReturn(false);

        // Then
        ResponseEntity<?> responseEntity = administratorService.cadastraAdministrator(administratorDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void editaAdministrator__quando_nao_existe_admin_email_ja_cadastrado_deve_retornar_conflito() {
        // Given
        String email = "teste@unisantos.com.br";
        AdministratorDTO administratorDTO = AdministratorDTO.builder()
                .email(email)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .senha("Senha").build();

        // When
        Mockito.when(admin.existsByEmail(email)).thenReturn(false);

        // Then
        ResponseEntity<?> responseEntity = administratorService.cadastraAdministrator(administratorDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void editaAdministrator__quando_existe_admin_email_ja_cadastrado_deve_retornar_sucesso() {
        // Given
        String email = "teste@unisantos.com.br";
        AdministratorDTO administratorDTO = AdministratorDTO.builder()
                .email(email)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .senha("Senha").build();

        // When
        Mockito.when(admin.existsByEmail(email)).thenReturn(true);

        // Then
        ResponseEntity<?> responseEntity = administratorService.cadastraAdministrator(administratorDTO);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}