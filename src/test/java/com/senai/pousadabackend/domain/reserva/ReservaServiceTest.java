package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.complemento.ComplementoService;
import com.senai.pousadabackend.domain.cupom.CupomService;
import com.senai.pousadabackend.domain.parametro.ParametroReservaService;
import com.senai.pousadabackend.exceptions.*;
import com.senai.pousadabackend.infraestructure.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ParametroReservaService parametroReservaService;

    @Mock
    private EmailService emailService;

    @Mock
    private CupomService cupomService;

    @Mock
    private ComplementoService complementoService;

    private ReservaService service;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new ReservaService(reservaRepository, parametroReservaService, emailService, cupomService, complementoService);
        mockFactory = new MockFactory();
        configurarParametrosPadrao();
    }

    private void configurarParametrosPadrao() {
        when(parametroReservaService.getTempoMinimoParaReservaDias()).thenReturn(1);
        when(parametroReservaService.getDuracaoMinimaDias()).thenReturn(1);
        when(parametroReservaService.getDuracaoMaximaDias()).thenReturn(30);
        when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(0);
        when(parametroReservaService.isBloquearReservaComPendencia()).thenReturn(true);
        when(parametroReservaService.getMaxReservasAtivasPorUsuario()).thenReturn(5);
        when(parametroReservaService.getPrazoMaximoCancelamentoDias()).thenReturn(7);
        when(parametroReservaService.isMultaCancelamentoAtiva()).thenReturn(false);
    }

    @Nested
    class Dado_uma_reserva_nova_valida {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
        }

        @Nested
        class Quando_salvar_com_status_nulo {

            @BeforeEach
            void setUp() {
                reserva.setStatusDaReserva(null);
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
                when(reservaRepository.save(any())).thenAnswer(inv -> {
                    Reserva r = inv.getArgument(0);
                    r.setId(1L);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_definir_status_aberta() {
                Reserva salva = service.salvar(reserva);

                assertThat(salva.getStatusDaReserva()).isEqualTo(StatusDaReserva.ABERTA);
            }
        }

        @Nested
        class Quando_salvar_com_status_aberto {

            @BeforeEach
            void setUp() {
                reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
                when(reservaRepository.save(any())).thenAnswer(inv -> {
                    Reserva r = inv.getArgument(0);
                    r.setId(2L);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_enviar_email_de_confirmacao() {
                service.salvar(reserva);

                assertThatCode(() -> verify(emailService).enviar(anyString(), anyString(), any())).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_existente {

        private Reserva existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.reservaExistente();
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findById(1L)).thenReturn(Optional.of(existente));
                when(reservaRepository.saveAndFlush(any())).thenReturn(existente);
                when(reservaRepository.save(any())).thenReturn(existente);
            }

            @Test
            void Entao_deve_enviar_email_de_alteracao() {
                service.salvar(existente);

                assertThatCode(() -> verify(emailService).enviar(contains("Alteração"), anyString(), any())).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_check_in_nulo {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setCheckIn(null);
        }

        @Nested
        class Quando_salvar {

            @Test
            void Entao_deve_informar_que_o_check_in_e_obrigatorio() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(NullPointerException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_check_out_nulo {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setCheckOut(null);
        }

        @Nested
        class Quando_salvar {

            @Test
            void Entao_deve_informar_que_o_check_out_e_obrigatorio() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(NullPointerException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_check_in_depois_do_check_out {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setCheckIn(LocalDateTime.now().plusDays(5));
            reserva.setCheckOut(LocalDateTime.now().plusDays(3));
        }

        @Nested
        class Quando_salvar {

            @Test
            void Entao_deve_informar_que_as_datas_sao_invalidas() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(DataDaReservaInvalida.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_check_in_igual_ao_check_out {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            LocalDateTime agora = LocalDateTime.now().plusDays(5);
            reserva.setCheckIn(agora);
            reserva.setCheckOut(agora);
        }

        @Nested
        class Quando_salvar {

            @Test
            void Entao_deve_informar_que_as_datas_sao_invalidas() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(DataDaReservaInvalida.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_status_cancelado {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);
        }

        @Nested
        class Quando_salvar {

            @Test
            void Entao_deve_informar_que_nao_e_possivel_criar_reserva_cancelada() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(CancelamentoDeReservaConcluidaException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_quarto_ocupado {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any()))
                        .thenReturn(List.of(mockFactory.reservaExistente()));
            }

            @Test
            void Entao_deve_informar_que_o_quarto_esta_ocupado() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(ExisteReservaParaEssaDataException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_limite_excedido {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(parametroReservaService.getMaxReservasAtivasPorUsuario()).thenReturn(2);
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(2L);
                when(parametroReservaService.isBloquearReservaComPendencia()).thenReturn(false);
            }

            @Test
            void Entao_deve_informar_que_o_limite_de_reservas_foi_excedido() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(LimiteReservasExcedidoException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_bloqueio_desativado {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(parametroReservaService.isBloquearReservaComPendencia()).thenReturn(false);
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
                when(reservaRepository.save(any())).thenAnswer(inv -> {
                    Reserva r = inv.getArgument(0);
                    r.setId(1L);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_nao_deve_validar_pendencias() {
                assertThatCode(() -> service.salvar(reserva)).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_prazo_minimo_nao_respeitado {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
            reserva.setCheckIn(LocalDateTime.now());
            reserva.setCheckOut(LocalDateTime.now().plusDays(2));
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(parametroReservaService.getTempoMinimoParaReservaDias()).thenReturn(2);
            }

            @Test
            void Entao_deve_informar_que_o_prazo_minimo_nao_foi_respeitado() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(PrazoMinimoNaoRespeitadoException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_duracao_menor_que_minimo {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
            reserva.setCheckIn(LocalDateTime.now().plusDays(5));
            reserva.setCheckOut(LocalDateTime.now().plusDays(6));
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(parametroReservaService.getDuracaoMinimaDias()).thenReturn(3);
                when(parametroReservaService.getDuracaoMaximaDias()).thenReturn(30);
            }

            @Test
            void Entao_deve_informar_que_a_duracao_e_invalida() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(DuracaoReservaInvalidaException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_duracao_maior_que_maximo {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
            reserva.setCheckIn(LocalDateTime.now().plusDays(5));
            reserva.setCheckOut(LocalDateTime.now().plusDays(45));
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(parametroReservaService.getDuracaoMinimaDias()).thenReturn(1);
                when(parametroReservaService.getDuracaoMaximaDias()).thenReturn(30);
            }

            @Test
            void Entao_deve_informar_que_a_duracao_e_invalida() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(DuracaoReservaInvalidaException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_tempo_entre_reservas_nao_respeitado {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
            reserva.setCheckIn(LocalDateTime.now().plusDays(5));
            reserva.setCheckOut(LocalDateTime.now().plusDays(8));
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(10);
                when(reservaRepository.findUltimoCheckOutPorCliente(any()))
                        .thenReturn(LocalDateTime.now().minusDays(2));
            }

            @Test
            void Entao_deve_informar_que_o_tempo_entre_reservas_nao_foi_respeitado() {
                assertThatThrownBy(() -> service.salvar(reserva))
                        .isInstanceOf(TempoEntreReservasNaoRespeitadoException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_sem_historico {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(10);
                when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
                when(reservaRepository.save(any())).thenAnswer(inv -> {
                    Reserva r = inv.getArgument(0);
                    r.setId(1L);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_passar_validacao_de_tempo_entre_reservas() {
                assertThatCode(() -> service.salvar(reserva)).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_tempo_entre_reservas_zero {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.novaReserva();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }

        @Nested
        class Quando_salvar {

            @BeforeEach
            void setUp() {
                when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(0);
                when(reservaRepository.findConflitosDeQuarto(any(), any(), any(), any())).thenReturn(List.of());
                when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
                when(reservaRepository.save(any())).thenAnswer(inv -> {
                    Reserva r = inv.getArgument(0);
                    r.setId(1L);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_ignorar_validacao_de_tempo_entre_reservas() {
                assertThatCode(() -> service.salvar(reserva)).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_aberta {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.reservaExistente();
            reserva.setCheckIn(LocalDateTime.now().plusDays(10));
        }

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
                when(reservaRepository.saveAndFlush(any())).thenReturn(reserva);
                when(reservaRepository.save(any())).thenReturn(reserva);
            }

            @Test
            void Entao_deve_atualizar_status_para_cancelado() {
                Reserva cancelada = service.cancelarPorId(1L);

                assertThat(cancelada.getStatusDaReserva()).isEqualTo(StatusDaReserva.CANCELADA);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_nao_encontrada {

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findById(99L)).thenReturn(Optional.empty());
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() {
                assertThatThrownBy(() -> service.cancelarPorId(99L))
                        .isInstanceOf(RegistroNaoEncontradoException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_concluida {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.reservaExistente();
            reserva.setStatusDaReserva(StatusDaReserva.CONCLUIDA);
        }

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
            }

            @Test
            void Entao_deve_informar_que_nao_e_possivel_cancelar() {
                assertThatThrownBy(() -> service.cancelarPorId(1L))
                        .isInstanceOf(CancelamentoDeReservaConcluidaException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_prazo_excedido {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.reservaExistente();
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
            reserva.setCheckIn(LocalDateTime.now().plusDays(2));
        }

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
                when(parametroReservaService.getPrazoMaximoCancelamentoDias()).thenReturn(7);
            }

            @Test
            void Entao_deve_informar_que_o_prazo_foi_excedido() {
                assertThatThrownBy(() -> service.cancelarPorId(1L))
                        .isInstanceOf(PrazoCancelamentoExcedidoException.class);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_multa_inativa {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            reserva = mockFactory.reservaExistente();
            reserva.setCheckIn(LocalDateTime.now().plusDays(30));
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
                when(parametroReservaService.isMultaCancelamentoAtiva()).thenReturn(false);
                when(reservaRepository.saveAndFlush(any())).thenReturn(reserva);
                when(reservaRepository.save(any())).thenReturn(reserva);
            }

            @Test
            void Entao_deve_retornar_zero() {
                service.cancelarPorId(1L);

                assertThatCode(() -> verify(parametroReservaService).isMultaCancelamentoAtiva()).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_um_quarto {

        @Nested
        class Quando_buscar_por_quarto {

            @BeforeEach
            void setUp() {
                when(reservaRepository.findByQuarto(any())).thenReturn(List.of(mockFactory.reservaExistente()));
            }

            @Test
            void Entao_deve_retornar_a_lista_de_reservas() {
                List<Reserva> resultado = service.buscarPorQuarto(mockFactory.quartoPadrao());

                assertThat(resultado).hasSize(1);
            }
        }
    }

    @Nested
    class Dada_uma_paginacao {

        private Pageable pageable;

        @BeforeEach
        void setUp() {
            pageable = PageRequest.of(0, 15);
        }

        @Nested
        class Quando_listar_paginado {

            @BeforeEach
            void setUp() {
                Page<Reserva> page = new PageImpl<>(List.of(mockFactory.reservaExistente()));
                when(reservaRepository.buscarReservasAtivas(pageable)).thenReturn(page);
            }

            @Test
            void Entao_deve_usar_buscar_reservas_ativas() {
                Page<Reserva> resultado = service.listarPaginado(pageable);

                assertThat(resultado.getContent()).hasSize(1);
            }
        }
    }
}
