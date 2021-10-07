package br.com.livresbs.livres.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidaCPFTest {

    @Test
    public void isCPF__quando_cpf_invalido_sem_formatacao_deve_retornar_falso() {
        assertFalse(ValidaCPF.isCPF("12345678912"));
    }

    @Test
    public void isCPF__quando_cpf_invalido_com_formatacao_deve_retornar_falso() {
        assertFalse(ValidaCPF.isCPF("123.456.789-12"));
    }

    @Test
    public void isCPF__quando_cpf_valido_sem_formatacao_deve_retornar_true() {
        assertTrue(ValidaCPF.isCPF("57693173021"));
    }

    @Test
    public void isCPF__quando_cpf_valido_com_formatacao_deve_retornar_false() {
        assertFalse(ValidaCPF.isCPF("452.525.560-91"));
    }

}