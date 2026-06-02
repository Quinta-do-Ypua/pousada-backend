package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
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
class ComplementoServiceTest {

    @Mock
    private ComplementoRepository repository;

    @Mock
    private ReservaRepository reservaRepository;

    private ComplementoService service;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new ComplementoService(repository, reservaRepository);
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_um_complemento_novo {

        private Complemento novo;

        @BeforeEach
        void setUp() {
            novo = mockFactory.novoComplemento();
        }

        @Nested
        class Quando_salvar_com_nome_unico {

            @BeforeEach
            void setUp() {
                when(repository.findByNome("Café da manhã")).thenReturn(null);
                when(repository.save(novo)).thenReturn(novo);
            }

            @Test
            void Entao_deve_criar_o_complemento() {
                Complemento resultado = service.salvar(novo);

                assertThat(resultado).isEqualTo(novo);
            }
        }

        @Nested
        class Quando_salvar_com_nome_repetido {

            @BeforeEach
            void setUp() {
                Complemento existente = mockFactory.complementoExistente();
                when(repository.findByNome("Café da manhã")).thenReturn(existente);
            }

            @Test
            void Entao_deve_informar_que_o_nome_ja_existe() {
                assertThatThrownBy(() -> service.salvar(novo))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("Já existe um complemento salvo com o mesmo nome");
            }
        }
    }

    @Nested
    class Dado_um_complemento_existente {

        private Complemento existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.complementoExistente();
        }

        @Nested
        class Quando_salvar_com_mesmo_nome {

            @BeforeEach
            void setUp() {
                when(repository.findByNome("Café da manhã")).thenReturn(existente);
                when(repository.save(existente)).thenReturn(existente);
            }

            @Test
            void Entao_deve_atualizar_o_complemento() {
                Complemento resultado = service.salvar(existente);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_salvar_com_nome_diferente {

            @BeforeEach
            void setUp() {
                when(repository.findByNome("Café da manhã")).thenReturn(null);
                when(repository.save(existente)).thenReturn(existente);
            }

            @Test
            void Entao_deve_atualizar_o_complemento() {
                Complemento resultado = service.salvar(existente);

                assertThat(resultado).isEqualTo(existente);
            }
        }
    }

    @Nested
    class Dado_uma_lista_de_complementos {

        private List<Complemento> complementos;

        @BeforeEach
        void setUp() {
            complementos = List.of(
                mockFactory.novoComplemento(),
                mockFactory.complementoComIdENome(2L, "Almoço")
            );
        }

        @Nested
        class Quando_salvar_em_lote {

            @BeforeEach
            void setUp() {
                when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
            }

            @Test
            void Entao_deve_criar_todos_os_complementos() {
                List<Complemento> resultado = service.salvarEmLote(complementos);

                assertThat(resultado).hasSize(2);
            }
        }
    }

    @Nested
    class Dado_um_id_existente {

        private Complemento existente;

        @BeforeEach
        void setUp() {
            existente = mockFactory.complementoExistente();
        }

        @Nested
        class Quando_buscar_por_id {

            @BeforeEach
            void setUp() {
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
            }

            @Test
            void Entao_deve_retornar_o_complemento() {
                Complemento resultado = service.buscarPorId(1L);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_excluir_sem_vinculo_com_reserva {

            @BeforeEach
            void setUp() {
                when(reservaRepository.existsByComplementos_Id(1L)).thenReturn(false);
                when(repository.findById(1L)).thenReturn(Optional.of(existente));
            }

            @Test
            void Entao_deve_remover_o_complemento() {
                Complemento resultado = service.excluir(1L);

                assertThat(resultado).isEqualTo(existente);
            }
        }

        @Nested
        class Quando_excluir_com_vinculo_com_reserva {

            @BeforeEach
            void setUp() {
                when(reservaRepository.existsByComplementos_Id(1L)).thenReturn(true);
            }

            @Test
            void Entao_deve_informar_que_nao_pode_ser_excluido() {
                assertThatThrownBy(() -> service.excluir(1L))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("vinculado a uma reserva");
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
                when(reservaRepository.existsByComplementos_Id(ID_INEXISTENTE)).thenReturn(false);
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
                        .thenReturn(new PageImpl<>(List.of(mockFactory.complementoExistente())));
            }

            @Test
            void Entao_deve_retornar_a_lista_de_complementos() {
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
                when(repository.findAll((Specification<Complemento>) null, pageable))
                        .thenReturn(new PageImpl<>(List.of()));
            }

            @Test
            void Entao_deve_retornar_todos_os_complementos() {
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
                when(repository.findAll((Specification<Complemento>) null, pageable))
                        .thenReturn(new PageImpl<>(List.of()));
            }

            @Test
            void Entao_deve_retornar_todos_os_complementos() {
                var resultado = service.buscarPorSpecification("   ", pageable);

                assertThat(resultado).isNotNull();
            }
        }
    }
}