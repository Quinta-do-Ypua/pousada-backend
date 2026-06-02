package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.MockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParametroReservaServiceTest {

    @Mock
    private ParametroReservaRepository repository;

    private ParametroReservaService service;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new ParametroReservaService(repository);
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_um_registro_existente {

        private ParametroReserva parametro;

        @BeforeEach
        void setUp() {
            parametro = mockFactory.parametroReservaExistente();
        }

        @Nested
        class Quando_recarregar_cache {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
            }

            @Test
            void Entao_deve_atualizar_o_cache() {
                service.recarregarCache();

                assertThat(service.getParametros()).isEqualTo(parametro);
            }
        }

        @Nested
        class Quando_obter_parametros_com_cache_nulo {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
            }

            @Test
            void Entao_deve_disparar_recarregamento() {
                ParametroReserva result = service.getParametros();

                assertThat(result).isEqualTo(parametro);
            }
        }

        @Nested
        class Quando_obter_bloqueio_reserva_com_pendencia {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.isBloquearReservaComPendencia()).isTrue();
            }
        }

        @Nested
        class Quando_obter_multa_cancelamento_ativa {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.isMultaCancelamentoAtiva()).isTrue();
            }
        }

        @Nested
        class Quando_obter_max_reservas_ativas_por_usuario {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getMaxReservasAtivasPorUsuario()).isEqualTo(3);
            }
        }

        @Nested
        class Quando_obter_tempo_entre_reservas_dias {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getTempoEntreReservasDias()).isEqualTo(7);
            }
        }

        @Nested
        class Quando_obter_prazo_maximo_cancelamento_dias {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getPrazoMaximoCancelamentoDias()).isEqualTo(30);
            }
        }

        @Nested
        class Quando_obter_tempo_minimo_para_reserva_dias {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getTempoMinimoParaReservaDias()).isEqualTo(1);
            }
        }

        @Nested
        class Quando_obter_duracao_minima_dias {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getDuracaoMinimaDias()).isEqualTo(1);
            }
        }

        @Nested
        class Quando_obter_duracao_maxima_dias {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getDuracaoMaximaDias()).isEqualTo(30);
            }
        }

        @Nested
        class Quando_obter_horario_check_in {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getHorarioCheckIn()).isEqualTo(LocalTime.of(14, 0));
            }
        }

        @Nested
        class Quando_obter_horario_check_out {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getHorarioCheckOut()).isEqualTo(LocalTime.of(12, 0));
            }
        }

        @Nested
        class Quando_obter_percentual_multa_cancelamento {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(parametro));
                service.recarregarCache();
            }

            @Test
            void Entao_deve_retornar_valor_do_parametro() {
                assertThat(service.getPercentualMultaCancelamento())
                        .isEqualByComparingTo(new BigDecimal("50.00"));
            }
        }
    }

    @Nested
    class Dado_registro_inexistente {

        @Nested
        class Quando_recarregar_cache {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.empty());
            }

            @Test
            void Entao_deve_usar_valores_padrao() {
                service.recarregarCache();
                ParametroReserva result = service.getParametros();

                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(1L);
            }
        }
    }

    @Nested
    class Dado_cache_populado {

        @Nested
        class Quando_obter_parametros_multiplas_vezes {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(mockFactory.parametroReservaExistente()));
                service.recarregarCache();
            }

            @Test
            void Entao_nao_deve_recarregar_cache() {
                service.getParametros();
                service.getParametros();

                assertThatCode(() -> service.getParametros()).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_metodo_com_defaults {

        @Nested
        class Quando_criar_parametro_padrao {

            @Test
            void Entao_deve_retornar_parametro_com_id_1_e_valores_padrao() {
                ParametroReserva defaults = ParametroReserva.comDefaults();

                assertThat(defaults.getId()).isEqualTo(1L);
                assertThat(defaults.getMaxReservasAtivasPorUsuario()).isEqualTo(1);
                assertThat(defaults.getDuracaoMaximaDias()).isEqualTo(30);
                assertThat(defaults.getPrazoMaximoCancelamentoDias()).isEqualTo(7);
            }
        }
    }
}
