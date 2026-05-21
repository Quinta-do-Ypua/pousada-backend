package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.core.enums.TipoPagamento;
import com.senai.pousadabackend.domain.meioPagamento.dto.MeioPagamentoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MeioPagamentoMapperTest {

    private MeioPagamentoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new MeioPagamentoMapper();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        MeioPagamento mp = MeioPagamento.builder()
                .id(1L)
                .nome("Cartão Crédito")
                .descricao("Pagamento com cartão de crédito")
                .tipo(TipoPagamento.CREDITO)
                .ativo(true)
                .taxaPercentual(new BigDecimal("2.50"))
                .taxaFixa(BigDecimal.ZERO)
                .prazoCompensacao(30)
                .permiteParcelamento(true)
                .maxParcelas(12)
                .build();

        MeioPagamentoDTO dto = mapper.toDTO(mp);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Cartão Crédito");
        assertThat(dto.getTipo()).isEqualTo("CREDITO");
        assertThat(dto.getAtivo()).isTrue();
        assertThat(dto.getTaxaPercentual()).isEqualByComparingTo("2.50");
        assertThat(dto.getPrazoCompensacao()).isEqualTo(30);
        assertThat(dto.getPermiteParcelamento()).isTrue();
        assertThat(dto.getMaxParcelas()).isEqualTo(12);
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        MeioPagamentoDTO dto = MeioPagamentoDTO.builder()
                .id(2L)
                .nome("PIX")
                .descricao("Pagamento instantâneo")
                .tipo("PIX")
                .ativo(true)
                .taxaPercentual(BigDecimal.ZERO)
                .taxaFixa(BigDecimal.ZERO)
                .prazoCompensacao(0)
                .permiteParcelamento(false)
                .maxParcelas(1)
                .build();

        MeioPagamento mp = mapper.toEntity(dto);

        assertThat(mp.getId()).isEqualTo(2L);
        assertThat(mp.getNome()).isEqualTo("PIX");
        assertThat(mp.getTipo()).isEqualTo(TipoPagamento.PIX);
        assertThat(mp.getAtivo()).isTrue();
        assertThat(mp.getPermiteParcelamento()).isFalse();
    }

    @Test
    void toEntity_tipoDebito_mapeiaCorreto() {
        MeioPagamentoDTO dto = MeioPagamentoDTO.builder()
                .nome("Débito")
                .tipo("DEBITO")
                .ativo(true)
                .taxaPercentual(BigDecimal.ZERO)
                .build();

        MeioPagamento mp = mapper.toEntity(dto);

        assertThat(mp.getTipo()).isEqualTo(TipoPagamento.DEBITO);
    }
}
