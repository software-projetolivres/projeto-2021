package br.com.livresbs.livres.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CheckoutDTO {

    private List<ProdutoCompradoDTO> produtos;
    private BigDecimal valorTotal;
    private List<MetodoPagamentoDTO> metodosPagamento;

}
