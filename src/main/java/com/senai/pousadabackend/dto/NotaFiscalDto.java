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

    @NotBlank
    private String numero;

    @NotNull
    private LocalDate dataCadastro;

    @NotNull
    private ClienteDTO cliente;

    @NotNull
    private BigDecimal valorTotal;

    @NotNull
    private List<NotaFiscalItemReduzidoDto> itens;

}
