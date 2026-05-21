package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.domain.parametro.dto.ParametroReservaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParametroReservaMapperTest {

    private ParametroReservaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ParametroReservaMapper();
    }

    private ParametroReserva parametroPadrao() {
        return ParametroReserva.builder()
                .id(1L)
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(false)
                .maxReservasAtivasPorUsuario(2)
                .tempoEntreReservasDias(3)
                .prazoMaximoCancelamentoDias(7)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(1)
                .duracaoMaximaDias(30)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("20.00"))
                .build();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        ParametroReserva parametro = parametroPadrao();

        ParametroReservaDTO dto = mapper.toDTO(parametro);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBloquearReservaComPendencia()).isTrue();
        assertThat(dto.getMultaCancelamentoAtiva()).isFalse();
        assertThat(dto.getMaxReservasAtivasPorUsuario()).isEqualTo(2);
        assertThat(dto.getTempoEntreReservasDias()).isEqualTo(3);
        assertThat(dto.getPrazoMaximoCancelamentoDias()).isEqualTo(7);
        assertThat(dto.getDuracaoMinimaDias()).isEqualTo(1);
        assertThat(dto.getDuracaoMaximaDias()).isEqualTo(30);
        assertThat(dto.getHorarioCheckIn()).isEqualTo(LocalTime.of(14, 0));
        assertThat(dto.getHorarioCheckOut()).isEqualTo(LocalTime.of(12, 0));
        assertThat(dto.getPercentualMultaCancelamento()).isEqualByComparingTo("20.00");
    }

    @Test
    void toDTO_nulo_retornaNulo() {
        assertThat(mapper.toDTO(null)).isNull();
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        ParametroReservaDTO dto = ParametroReservaDTO.builder()
                .id(1L)
                .bloquearReservaComPendencia(false)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(5)
                .tempoEntreReservasDias(2)
                .prazoMaximoCancelamentoDias(14)
                .tempoMinimoParaReservaDias(2)
                .duracaoMinimaDias(2)
                .duracaoMaximaDias(20)
                .horarioCheckIn(LocalTime.of(15, 0))
                .horarioCheckOut(LocalTime.of(11, 0))
                .percentualMultaCancelamento(new BigDecimal("10.00"))
                .build();

        ParametroReserva parametro = mapper.toEntity(dto);

        assertThat(parametro.getId()).isEqualTo(1L);
        assertThat(parametro.getBloquearReservaComPendencia()).isFalse();
        assertThat(parametro.getMultaCancelamentoAtiva()).isTrue();
        assertThat(parametro.getMaxReservasAtivasPorUsuario()).isEqualTo(5);
        assertThat(parametro.getDuracaoMaximaDias()).isEqualTo(20);
    }

    @Test
    void toEntity_nulo_retornaNulo() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toEntity_camposNulosNoDtoNaoSobrescrevemDefaults() {
        ParametroReservaDTO dto = ParametroReservaDTO.builder()
                .id(1L)
                .build(); // all fields null

        ParametroReserva parametro = mapper.toEntity(dto);

        assertThat(parametro.getId()).isEqualTo(1L);
        // Entity default is 1 — null DTO field does not overwrite it
        assertThat(parametro.getMaxReservasAtivasPorUsuario()).isEqualTo(1);
    }

    @Test
    void toDTOList_mapeiaLista() {
        List<ParametroReserva> lista = List.of(parametroPadrao(), parametroPadrao());

        List<ParametroReservaDTO> resultado = mapper.toDTOList(lista);

        assertThat(resultado).hasSize(2);
    }

    @Test
    void toDTOList_listaNula_retornaNulo() {
        assertThat(mapper.toDTOList(null)).isNull();
    }

    @Test
    void updateEntityFromDTO_atualizaApenasCamposNaoNulosDoDTO() {
        ParametroReserva parametro = parametroPadrao();
        ParametroReservaDTO dto = ParametroReservaDTO.builder()
                .maxReservasAtivasPorUsuario(10)
                .duracaoMaximaDias(60)
                .build();

        mapper.updateEntityFromDTO(dto, parametro);

        assertThat(parametro.getMaxReservasAtivasPorUsuario()).isEqualTo(10);
        assertThat(parametro.getDuracaoMaximaDias()).isEqualTo(60);
        assertThat(parametro.getDuracaoMinimaDias()).isEqualTo(1); // unchanged
    }

    @Test
    void updateEntityFromDTO_dtoNulo_semExcecao() {
        ParametroReserva parametro = parametroPadrao();

        mapper.updateEntityFromDTO(null, parametro);

        assertThat(parametro.getMaxReservasAtivasPorUsuario()).isEqualTo(2); // unchanged
    }

    @Test
    void updateEntityFromDTO_entidadeNula_semExcecao() {
        ParametroReservaDTO dto = ParametroReservaDTO.builder().maxReservasAtivasPorUsuario(5).build();

        mapper.updateEntityFromDTO(dto, null);
    }

    @Test
    void updateEntityFromDTO_todosOsCampos() {
        ParametroReserva parametro = parametroPadrao();
        ParametroReservaDTO dto = ParametroReservaDTO.builder()
                .bloquearReservaComPendencia(false)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(8)
                .tempoEntreReservasDias(5)
                .prazoMaximoCancelamentoDias(30)
                .tempoMinimoParaReservaDias(3)
                .duracaoMinimaDias(2)
                .duracaoMaximaDias(45)
                .horarioCheckIn(LocalTime.of(16, 0))
                .horarioCheckOut(LocalTime.of(10, 0))
                .percentualMultaCancelamento(new BigDecimal("30.00"))
                .build();

        mapper.updateEntityFromDTO(dto, parametro);

        assertThat(parametro.getBloquearReservaComPendencia()).isFalse();
        assertThat(parametro.getMultaCancelamentoAtiva()).isTrue();
        assertThat(parametro.getMaxReservasAtivasPorUsuario()).isEqualTo(8);
        assertThat(parametro.getPrazoMaximoCancelamentoDias()).isEqualTo(30);
        assertThat(parametro.getHorarioCheckIn()).isEqualTo(LocalTime.of(16, 0));
    }
}
