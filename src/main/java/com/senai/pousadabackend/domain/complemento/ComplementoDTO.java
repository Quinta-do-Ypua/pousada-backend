package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ComplementoDTO {

    @Null(message = "O id deve estar vazio", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O id é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor tem um limite mínimo de 0,01 R$")
    @DecimalMax(value = "9999.99", message = "O valor tem um limite máximo de 9.999,99 R$")
    @Digits(integer = 4, fraction = 2, message = "O valor deve conter no máximo 4 números inteiros e 2 decimais")
    private BigDecimal valor;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres")
    private String descricao;

}
