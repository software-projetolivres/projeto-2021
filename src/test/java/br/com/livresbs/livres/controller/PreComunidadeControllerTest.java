package br.com.livresbs.livres.controller;

import br.com.livresbs.livres.service.PreComunidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PreComunidadeControllerTest extends ControllerTest {

    @Mock
    private PreComunidadeService pcr;

    @InjectMocks
    private PreComunidadeController controller;

    @Override
    public PreComunidadeController getController() {
        return controller;
    }

    @Test
    void listaPreComunidades() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/precomunidade"))
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(""));
    }

    @Test
    void listaPreComunidadeUnica() {
    }

    @Test
    void cadastraPreComunidade() {
    }

    @Test
    void editaPreComunidade() {
    }

    @Test
    void excluiPreComunidade() {
    }

}