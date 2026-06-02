package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaService;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.exceptions.RegistrosVinculadosException;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuartoServiceTest {

    @Mock
    private QuartoRepository repository;

    @Mock
    private ReservaService reservaService;

    private QuartoService service;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new QuartoService(repository, reservaService);
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_um_quarto_novo {

        private Quarto novo;

        @BeforeEach
        void setUp() {
            novo = mockFactory.novoQuarto();
        }

        @Nested
        class Quando_salvar_com_nome_unico {

            @BeforeEach
            void setUp() {
                when(repository.findByNome(novo.getNome())).thenReturn(null);
                when(repository.save(novo)).thenReturn(novo);
            }

            @Test
            void Entao_deve_criar_o_quarto() {
                Quarto resultado = service.salvar(novo);

                assertThat(resultado).isEqualTo(novo);
            }
        }

        @Nested
        class Quando_salvar_com_nome_duplicado {

            @BeforeEach
            void setUp() {
                Quarto existente = mockFactory.quartoExistente();
                when(repository.findByNome(novo.getNome())).thenReturn(existente);
            }

            @Test
            void Entao_deve_informar_que_o_nome_ja_existe() {
                assertThatThrownBy(() -> service.salvar(novo))
                        .isInstanceOf(RegistroDuplicadoException.class)
                        .hasMessageContaining("Já existe um quarto com esse nome");
            }
        }
    }

    @Nested
    class Dado_um_quarto_existente {

        private Quarto existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.quartoExistente();
        }

        @Nested
        class Quando_salvar_com_mesmo_nome {

            @BeforeEach
            void setUp() {
                when(repository.save(existente)).thenReturn(existente);
            }

            @Test
            void Entao_deve_atualizar_o_quarto() {
                Quarto resultado = service.salvar(existente);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_salvar_com_nome_diferente {

            @BeforeEach
            void setUp() {
                when(repository.save(existente)).thenReturn(existente);
            }

            @Test
            void Entao_deve_atualizar_o_quarto() {
                existente.setNome("Suite 02");
                Quarto resultado = service.salvar(existente);

                assertThat(resultado).isEqualTo(existente);
            }
        }
    }

    @Nested
    class Dado_uma_lista_de_quartos {

        private List<Quarto> quartos;

        @BeforeEach
        void setUp() {
            quartos = List.of(
                mockFactory.novoQuarto(),
                mockFactory.quartoComIdENome(2L, "Suite 02")
            );
        }

        @Nested
        class Quando_salvar_em_lote {

            @BeforeEach
            void setUp() {
                when(repository.findByNome(anyString())).thenReturn(null);
                when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
            }

            @Test
            void Entao_deve_criar_todos_os_quartos() {
                List<Quarto> resultado = service.salvarEmLote(quartos);

                assertThat(resultado).hasSize(2);
            }
        }
    }

    @Nested
    class Dado_um_id_existente {

        private Quarto existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.quartoExistente();
        }

        @Nested
        class Quando_buscar_por_id {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
            }

            @Test
            void Entao_deve_retornar_o_quarto() {
                Quarto resultado = service.buscarPorId(1L);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_excluir_sem_reservas_vinculadas {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
                when(reservaService.buscarPorQuarto(existente)).thenReturn(List.of());
            }

            @Test
            void Entao_deve_remover_o_quarto() {
                Quarto resultado = service.excluir(1L);

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
    class Dado_um_quarto_com_reservas_vinculadas {

        private Quarto existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.quartoExistente();
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
                when(reservaService.buscarPorQuarto(existente)).thenReturn(List.of(new Reserva()));
            }

            @Test
            void Entao_deve_informar_que_existem_reservas_vinculadas() {
                assertThatThrownBy(() -> service.excluir(1L))
                        .isInstanceOf(RegistrosVinculadosException.class)
                        .hasMessageContaining("reservas vinculadas");
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
                        .thenReturn(new PageImpl<>(List.of(mockFactory.quartoExistente())));
            }

            @Test
            void Entao_deve_retornar_a_lista_de_quartos() {
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
                when(repository.findAll((Specification<Quarto>) null, pageable))
                        .thenReturn(new PageImpl<>(List.of()));
            }

            @Test
            void Entao_deve_retornar_todos_os_quartos() {
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
                when(repository.findAll((Specification<Quarto>) null, pageable))
                        .thenReturn(new PageImpl<>(List.of()));
            }

            @Test
            void Entao_deve_retornar_todos_os_quartos() {
                var resultado = service.buscarPorSpecification("   ", pageable);

                assertThat(resultado).isNotNull();
            }
        }
    }
}