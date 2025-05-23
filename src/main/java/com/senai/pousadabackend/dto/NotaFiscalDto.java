package com.senai.pousadabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class NotaFiscalDto {

    private Long idNotaFiscal;

    @NotBlank(message = "O numero da nf é obrigatório")
    private String numero;

    @NotNull(message = "A data de cadastro é obrigatória")
    private LocalDate dataCadastro;

    @NotNull(message = "O cliente é obrigatório")
    private ClienteDTO cliente;

    @NotNull(message = "O valor total da nota deve ser preenchido")
    private BigDecimal valorTotal;

    @NotNull(message = "A nota deve conter itens")
    private List<NotaFiscalItemReduzidoDto> itens;

}
