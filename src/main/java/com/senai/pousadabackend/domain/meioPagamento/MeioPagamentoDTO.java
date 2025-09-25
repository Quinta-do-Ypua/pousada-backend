package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MeioPagamentoDTO {

    @Null(message = "O id deve estar vazio", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O id é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @Null(message = "O meio de pagamento não deve conter valor em 'ativo'", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O ativo é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Boolean ativo;

    @DecimalMin(value = "0.0", inclusive = false, message = "Taxa percentual deve ser maior que 0")
    @DecimalMax(value = "99.99", message = "Taxa percentual deve ser no máximo 99,99%")
    @Digits(integer = 3, fraction = 2, message = "Taxa percentual deve ter no máximo 3 dígitos inteiros e 2 decimais")
    private BigDecimal taxaPercentual;

    @DecimalMin(value = "0.0", inclusive = false, message = "Taxa fixa deve ser maior que 0")
    @Digits(integer = 8, fraction = 2, message = "Taxa fixa deve ter no máximo 8 dígitos inteiros e 2 decimais")
    private BigDecimal taxaFixa;

    @Min(value = 0, message = "Prazo de compensação não pode ser negativo")
    @Max(value = 365, message = "Prazo de compensação não pode ser maior que 365 dias")
    private Integer prazoCompensacao;

    @NotNull(message = "Permite parcelamento é obrigatório")
    private Boolean permiteParcelamento;

    @Min(value = 0, message = "Máximo de parcelas deve ser no mínimo 0")
    @Max(value = 24, message = "Máximo de parcelas deve ser no máximo 24")
    private Integer maxParcelas;
}
