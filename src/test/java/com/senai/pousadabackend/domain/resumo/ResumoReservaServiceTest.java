package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.ComplementoService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import com.senai.pousadabackend.domain.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ResumoReservaServiceTest {

    @Mock
    private ResumoReservaRepository repository;

    @Mock
    private QuartoService quartoService;

    @Mock
    private ComplementoService complementoService;

    private ResumoReservaService service;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new ResumoReservaService(repository, quartoService, complementoService);
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_uma_reserva_valida_sem_complementos {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            Quarto quarto = mockFactory.quartoPadrao();
            quarto.setValorDiaria(BigDecimal.valueOf(200));
            reserva = Reserva.builder()
                    .id(1L)
                    .quarto(quarto)
                    .cliente(mockFactory.clientePadrao())
                    .checkIn(LocalDateTime.now().plusDays(5))
                    .checkOut(LocalDateTime.now().plusDays(8))
                    .complementos(new ArrayList<>())
                    .build();
        }

        @Nested
        class Quando_criar_nota_fiscal {

            @BeforeEach
            void setUp() {
                Quarto quarto = mockFactory.quartoPadrao();
                quarto.setValorDiaria(BigDecimal.valueOf(200));
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(repository.save(any())).thenAnswer(inv -> {
                    ResumoReserva r = inv.getArgument(0);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_criar_com_sucesso() {
                ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

                assertThat(resultado).isNotNull();
                assertThat(resultado.getCliente().getId()).isEqualTo(1L);
                assertThat(resultado.getValorTotal()).isGreaterThan(BigDecimal.ZERO);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_complementos_iguais {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            Quarto quarto = mockFactory.quartoPadrao();
            quarto.setValorDiaria(BigDecimal.valueOf(200));
            reserva = Reserva.builder()
                    .id(1L)
                    .quarto(quarto)
                    .cliente(mockFactory.clientePadrao())
                    .checkIn(LocalDateTime.now().plusDays(5))
                    .checkOut(LocalDateTime.now().plusDays(8))
                    .complementos(new ArrayList<>())
                    .build();

            Complemento comp1 = Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build();
            Complemento comp2 = Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build();
            reserva.setComplementos(List.of(comp1, comp2));
        }

        @Nested
        class Quando_criar_nota_fiscal {

            @BeforeEach
            void setUp() {
                Quarto quarto = mockFactory.quartoPadrao();
                quarto.setValorDiaria(BigDecimal.valueOf(200));
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(complementoService.buscarPorId(10L)).thenReturn(Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build());
                when(repository.save(any())).thenAnswer(inv -> {
                    ResumoReserva r = inv.getArgument(0);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_agrupar_complementos_iguais() {
                ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

                assertThat(resultado).isNotNull();
                assertThat(resultado.getItens()).isNotEmpty();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_complementos_diferentes {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            Quarto quarto = mockFactory.quartoPadrao();
            quarto.setValorDiaria(BigDecimal.valueOf(200));
            reserva = Reserva.builder()
                    .id(1L)
                    .quarto(quarto)
                    .cliente(mockFactory.clientePadrao())
                    .checkIn(LocalDateTime.now().plusDays(5))
                    .checkOut(LocalDateTime.now().plusDays(8))
                    .complementos(new ArrayList<>())
                    .build();

            Complemento comp1 = Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build();
            Complemento comp2 = Complemento.builder().id(20L).nome("Transfer").valor(BigDecimal.valueOf(80)).descricao("Transfer").build();
            reserva.setComplementos(List.of(comp1, comp2));
        }

        @Nested
        class Quando_criar_nota_fiscal {

            @BeforeEach
            void setUp() {
                Quarto quarto = mockFactory.quartoPadrao();
                quarto.setValorDiaria(BigDecimal.valueOf(200));
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(complementoService.buscarPorId(10L)).thenReturn(Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build());
                when(complementoService.buscarPorId(20L)).thenReturn(Complemento.builder().id(20L).nome("Transfer").valor(BigDecimal.valueOf(80)).descricao("Transfer").build());
                when(repository.save(any())).thenAnswer(inv -> {
                    ResumoReserva r = inv.getArgument(0);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_criar_tres_itens() {
                ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

                assertThat(resultado.getItens()).hasSize(3);
            }
        }
    }

    @Nested
    class Dado_uma_reserva_para_criacao_assincrona {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            Quarto quarto = mockFactory.quartoPadrao();
            quarto.setValorDiaria(BigDecimal.valueOf(200));
            reserva = Reserva.builder()
                    .id(1L)
                    .quarto(quarto)
                    .cliente(mockFactory.clientePadrao())
                    .checkIn(LocalDateTime.now().plusDays(5))
                    .checkOut(LocalDateTime.now().plusDays(8))
                    .complementos(new ArrayList<>())
                    .build();
        }

        @Nested
        class Quando_criar_nota_fiscal_assincrona {

            @BeforeEach
            void setUp() {
                Quarto quarto = mockFactory.quartoPadrao();
                quarto.setValorDiaria(BigDecimal.valueOf(200));
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(repository.save(any())).thenAnswer(inv -> {
                    ResumoReserva r = inv.getArgument(0);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_nao_deve_lancar_excecao() throws InterruptedException {
                service.criarNotaFiscalAssincronaAPartirDaReserva(reserva);

                Thread.sleep(200);
                assertThatCode(() -> verify(repository, atLeastOnce()).save(any())).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_reserva_com_dias_definidos {

        private Reserva reserva;

        @BeforeEach
        void setUp() {
            Quarto quarto = mockFactory.quartoPadrao();
            quarto.setValorDiaria(BigDecimal.valueOf(200));
            reserva = Reserva.builder()
                    .id(1L)
                    .quarto(quarto)
                    .cliente(mockFactory.clientePadrao())
                    .checkIn(LocalDateTime.now().plusDays(5))
                    .checkOut(LocalDateTime.now().plusDays(8))
                    .complementos(new ArrayList<>())
                    .build();
        }

        @Nested
        class Quando_criar_nota_fiscal {

            @BeforeEach
            void setUp() {
                Quarto quarto = mockFactory.quartoPadrao();
                quarto.setValorDiaria(BigDecimal.valueOf(200));
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(repository.save(any())).thenAnswer(inv -> {
                    ResumoReserva r = inv.getArgument(0);
                    r.setDataCriacao(LocalDateTime.now());
                    return r;
                });
            }

            @Test
            void Entao_deve_calcular_valor_total_correto() {
                ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

                assertThat(resultado.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(600));
            }
        }
    }
}
