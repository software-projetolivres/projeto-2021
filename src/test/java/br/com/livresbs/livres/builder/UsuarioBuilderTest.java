package br.com.livresbs.livres.builder;

import br.com.livresbs.livres.model.TipoPerfil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioBuilderTest {

    @ParameterizedTest
    @EnumSource(value = TipoPerfil.class)
    public void deve_retornar_perfil_admin(TipoPerfil tipo){

        // When
        final UsuarioBuilder builder = new UsuarioBuilder();
        builder.setPerfil(tipo);

        // Then
        assertTrue(builder.getPerfis().contains(tipo));
    }

    @Test
    public void deve_retornar_null_pointer_exception(){

        // When
        final UsuarioBuilder builder = new UsuarioBuilder();

        // Then
        assertThrows(NullPointerException.class, () -> builder.setPerfil(null));
    }

}