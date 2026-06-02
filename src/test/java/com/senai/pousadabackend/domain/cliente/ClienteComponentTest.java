package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.infraestructure.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteComponentTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private EmailService emailService;

    private ClienteComponent component;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        component = new ClienteComponent(clienteService, emailService);
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_um_cliente_existente {

        private Cliente cliente;

        @BeforeEach
        void setUp() {
            cliente = mockFactory.clientePadrao();
        }

        @Nested
        class Quando_inativar {

            @BeforeEach
            void setUp() {
                when(clienteService.buscarPorId(1L)).thenReturn(cliente);
                when(clienteService.excluir(1L)).thenReturn(cliente);
            }

            @Test
            void Entao_deve_enviar_email_e_inativar_o_cliente() {
                Cliente resultado = component.inativar(1L);

                assertThat(resultado).isEqualTo(cliente);
            }
        }
    }

    @Nested
    class Dado_um_cliente_inexistente {

        private static final Long ID_INEXISTENTE = 99L;

        @Nested
        class Quando_inativar {

            @BeforeEach
            void setUp() {
                when(clienteService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() {
                assertThatThrownBy(() -> component.inativar(ID_INEXISTENTE))
                        .isInstanceOf(RegistroNaoEncontradoException.class);
            }
        }
    }
}
