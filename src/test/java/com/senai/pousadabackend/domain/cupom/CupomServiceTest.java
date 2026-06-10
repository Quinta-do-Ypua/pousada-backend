package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CupomServiceTest {

    @Mock
    private CupomRepository repository;

    @Mock
    private ReservaRepository reservaRepository;

    private CupomService service;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new CupomService(repository, reservaRepository);
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_um_cupom_novo {

        private Cupom novo;

        @BeforeEach
        void setUp() {
            novo = mockFactory.novoCupom();
        }

        @Nested
        class Quando_salvar_com_codigo_unico_e_periodo_valido {

            @BeforeEach
            void setUp() {
                when(repository.findByCodigo("DESC10")).thenReturn(null);
                when(repository.save(novo)).thenReturn(novo);
            }

            @Test
            void Entao_deve_criar_o_cupom() {
                Cupom resultado = service.salvar(novo);

                assertThat(resultado).isEqualTo(novo);
            }
        }

        @Nested
        class Quando_salvar_com_codigo_repetido {

            @BeforeEach
            void setUp() {
                Cupom existente = mockFactory.cupomExistente();
                when(repository.findByCodigo("DESC10")).thenReturn(existente);
            }

            @Test
            void Entao_deve_informar_que_o_codigo_ja_existe() {
                assertThatThrownBy(() -> service.salvar(novo))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("Já existe um cupom salvo com o mesmo código");
            }
        }

        @Nested
        class Quando_salvar_com_data_inicio_posterior_a_vencimento {

            @BeforeEach
            void setUp() {
                novo.setDataDeInicio(LocalDate.now().plusMonths(2));
                novo.setDataDeVencimento(LocalDate.now().plusMonths(1));
            }

            @Test
            void Entao_deve_informar_que_o_periodo_e_invalido() {
                assertThatThrownBy(() -> service.salvar(novo))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("data de início não deve ser posterior a data de vencimento");
            }
        }

        @Nested
        class Quando_salvar_com_data_inicio_anterior_a_data_atual {

            @BeforeEach
            void setUp() {
                novo.setDataDeInicio(LocalDate.now().minusDays(1));
            }

            @Test
            void Entao_deve_informar_que_a_data_inicial_deve_ser_posterior_a_data_atual() {
                assertThatThrownBy(() -> service.salvar(novo))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("A data inicial deve ser posterior ou igual a data atual");
            }
        }
    }

    @Nested
    class Dado_um_cupom_existente {

        private Cupom existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.cupomExistente();
        }

        @Nested
        class Quando_salvar_com_mesmo_codigo {

            @BeforeEach
            void setUp() {
                when(repository.findByCodigo("DESC10")).thenReturn(existente);
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
                when(repository.save(existente)).thenReturn(existente);
            }

            @Test
            void Entao_deve_atualizar_o_cupom() {
                Cupom resultado = service.salvar(existente);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_salvar_com_codigo_diferente {

            @BeforeEach
            void setUp() {
                when(repository.findByCodigo("DESC10")).thenReturn(null);
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
                when(repository.save(existente)).thenReturn(existente);
            }

            @Test
            void Entao_deve_atualizar_o_cupom() {
                Cupom resultado = service.salvar(existente);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_salvar_com_data_inicio_anterior_a_data_inicial_cadastrada {

            @Test
            void Entao_deve_informar_que_a_data_inicio_nao_pode_ser_anterior() {
                LocalDate dataInicialCadastrada = LocalDate.now();
                existente.setDataDeInicio(dataInicialCadastrada);
                existente.setDataDeVencimento(LocalDate.now().plusMonths(1));
                
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
                
                Cupom cupomAtualizado = mockFactory.cupomExistente();
                cupomAtualizado.setDataDeInicio(dataInicialCadastrada.minusDays(1));
                
                assertThatThrownBy(() -> service.salvar(cupomAtualizado))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("data de início não pode ser anterior a data inicial cadastrada");
            }
        }
    }

    @Nested
    class Dado_uma_lista_de_cupons {

        private List<Cupom> cupons;

        @BeforeEach
        void setUp() {
            cupons = List.of(
                mockFactory.novoCupom(),
                mockFactory.novoCupom()
            );
        }

        @Nested
        class Quando_salvar_em_lote {

            @BeforeEach
            void setUp() {
                when(repository.findByCodigo(anyString())).thenReturn(null);
                when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
            }

            @Test
            void Entao_deve_criar_todos_os_cupons() {
                List<Cupom> resultado = service.salvarEmLote(cupons);

                assertThat(resultado).hasSize(2);
            }
        }
    }

    @Nested
    class Dado_um_id_existente {

        private Cupom existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.cupomExistente();
        }

        @Nested
        class Quando_buscar_por_id {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
            }

            @Test
            void Entao_deve_retornar_o_cupom() {
                Cupom resultado = service.buscarPorId(1L);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
            }

            @Test
            void Entao_deve_remover_o_cupom() {
                Cupom resultado = service.excluir(1L);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_validar_existencia {

            @BeforeEach
            void setUp() {
                when(repository.existsById(1L)).thenReturn(true);
            }

            @Test
            void Entao_deve_confirmar_que_o_registro_existe() {
                assertThatCode(() -> service.throwIfNotExists(1L)).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_um_id_inexistente {

        private static final Long ID_INEXISTENTE = 99L;

        @Nested
        class Quando_buscar_por_id {

            @BeforeEach
            void setUp() {
                when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() {
                assertThatThrownBy(() -> service.buscarPorId(ID_INEXISTENTE))
                        .isInstanceOf(RegistroNaoEncontradoException.class)
                        .hasMessageContaining("99");
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() {
                assertThatThrownBy(() -> service.excluir(ID_INEXISTENTE))
                        .isInstanceOf(RegistroNaoEncontradoException.class);
            }
        }

        @Nested
        class Quando_validar_existencia {

            @BeforeEach
            void setUp() {
                when(repository.existsById(ID_INEXISTENTE)).thenReturn(false);
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() {
                assertThatThrownBy(() -> service.throwIfNotExists(ID_INEXISTENTE))
                        .isInstanceOf(RegistroNaoEncontradoException.class)
                        .hasMessageContaining("99");
            }
        }
    }

    @Nested
    class Dado_um_id_nulo {

        @Nested
        class Quando_buscar_por_id {

            @Test
            void Entao_deve_informar_que_o_id_e_obrigatorio() {
                assertThatThrownBy(() -> service.buscarPorId(null))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("obrigatório");
            }
        }

        @Nested
        class Quando_validar_existencia {

            @Test
            void Entao_deve_informar_que_o_id_e_obrigatorio() {
                assertThatThrownBy(() -> service.throwIfNotExists(null))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    class Dada_uma_paginacao {

        private Pageable pageable;

        @BeforeEach
        void setUp() {
            pageable = PageRequest.of(0, 10);
        }

        @Nested
        class Quando_listar_paginado {

            @BeforeEach
            void setUp() {
                when(repository.findAll(pageable))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.cupomExistente())));
            }

            @Test
            void Entao_deve_retornar_a_lista_de_cupons() {
                var resultado = service.listarPaginado(pageable);

                assertThat(resultado.getContent()).hasSize(1);
            }
        }
    }

    @Nested
    class Dada_uma_specification_nula {

        private Pageable pageable;

        @BeforeEach
        void setUp() {
            pageable = PageRequest.of(0, 10);
        }

        @Nested
        class Quando_buscar_por_specification {

            @BeforeEach
            void setUp() {
                when(repository.findAll((Specification<Cupom>) null, pageable))
                        .thenReturn(new PageImpl<>(List.of()));
            }

            @Test
            void Entao_deve_retornar_todos_os_cupons() {
                var resultado = service.buscarPorSpecification(null, pageable);

                assertThat(resultado).isNotNull();
            }
        }
    }

    @Nested
    class Dada_uma_specification_em_branco {

        private Pageable pageable;

        @BeforeEach
        void setUp() {
            pageable = PageRequest.of(0, 10);
        }

        @Nested
        class Quando_buscar_por_specification {

            @BeforeEach
            void setUp() {
                when(repository.findAll((Specification<Cupom>) null, pageable))
                        .thenReturn(new PageImpl<>(List.of()));
            }

            @Test
            void Entao_deve_retornar_todos_os_cupons() {
                var resultado = service.buscarPorSpecification("   ", pageable);

                assertThat(resultado).isNotNull();
            }
        }
    }
}
