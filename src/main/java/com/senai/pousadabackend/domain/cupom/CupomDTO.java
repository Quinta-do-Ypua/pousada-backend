package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CupomDTO {

    @Null(message = "O id deve estar vazio", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O id é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Long id;

    @NotBlank(message = "O código é obrigatório")
    @Size(max = 10, message = "O código deve ter no máximo 10 caracteres")
    private String codigo;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataDeInicio;

    @NotNull(message = "A data de vencimento é obrigatória")
    private LocalDate dataDeVencimento;

    @NotNull(message = "A porcentagem é obrigatória")
    @Min(value = 1, message = "A porcentagem tem um limite mínimo de 1 %")
    @Max(value = 100, message = "A porcentagem tem um limite máximo de 100 %")
    @Digits(integer = 3, fraction = 0, message = "A porcentagem deve conter no máximo 3 números inteiros")
    private Double porcentagemDeDesconto;

    @NotNull(message = "A quantidade máxima é obrigatória")
    @Min(value = 1, message = "A quantidade máxima tem um limite mínimo de 1 unidade")
    private Integer quantidadeMaximaDeUso;
}
