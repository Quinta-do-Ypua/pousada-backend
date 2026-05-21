package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.infraestructure.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteComponentTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private EmailService emailService;

    private ClienteComponent component;

    @BeforeEach
    void setUp() {
        component = new ClienteComponent(clienteService, emailService);
    }

    private Cliente clientePadrao() {
        Cliente c = Cliente.builder()
                .id(1L)
                .nome("Maria")
                .email("maria@email.com")
                .cpf("000.111.222-33")
                .celular("(11) 99999-0000")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.FEMININO)
                .endereco(Endereco.builder().id(1L).cidade("SP").estado("SP").rua("Rua A").build())
                .build();
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    @Test
    void inativar_clienteEncontrado_enviEmailEExclui() {
        Cliente cliente = clientePadrao();
        when(clienteService.buscarPorId(1L)).thenReturn(cliente);
        when(clienteService.excluir(1L)).thenReturn(cliente);

        Cliente resultado = component.inativar(1L);

        assertThat(resultado).isEqualTo(cliente);
        verify(emailService).enviar(eq("Inativação de perfil"), anyString(), eq(cliente));
        verify(clienteService).excluir(1L);
    }

    @Test
    void inativar_clienteNaoEncontrado_lancaExcecao() {
        when(clienteService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));

        assertThatThrownBy(() -> component.inativar(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class);

        verify(emailService, never()).enviar(anyString(), anyString(), any());
        verify(clienteService, never()).excluir(anyLong());
    }
}
