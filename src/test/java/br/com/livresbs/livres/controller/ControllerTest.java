package br.com.livresbs.livres.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@ExtendWith(MockitoExtension.class)
public abstract class ControllerTest {

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(new Object[]{getController()});
        standaloneMockMvcBuilder.addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }, new String[0]);
        this.mockMvc = standaloneMockMvcBuilder.build();
    }

    public abstract Object getController();

}
