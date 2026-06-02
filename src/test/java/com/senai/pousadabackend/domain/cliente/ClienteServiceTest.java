package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    private ClienteService service;

    @BeforeEach
    void setUp() {
        service = new ClienteService(repository);
    }

    private Cliente novoCliente() {
        return Cliente.builder()
                .nome("João Silva")
                .cpf("123.456.789-09")
                .email("joao@test.com")
                .celular("(11) 91234-5678")
                .sexo(Sexo.MASCULINO)
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .endereco(Endereco.builder().id(1L).build())
                .build();
    }

    private Cliente clienteExistente() {
        Cliente c = novoCliente();
        c.setId(1L);
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    @Test
    void salvar_novoCliente_semDuplicatas_sucesso() {
        Cliente cliente = novoCliente();

        when(repository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(repository.findByCelular(cliente.getCelular())).thenReturn(Optional.empty());
        when(repository.findByEmail(cliente.getEmail())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(cliente);

        assertThatCode(() -> service.salvar(cliente)).doesNotThrowAnyException();
        verify(repository).save(cliente);
    }

    @Test
    void salvar_cpfDuplicado_lancaRegistroDuplicadoException() {
        Cliente novo = novoCliente();
        Cliente existente = clienteExistente();

        when(repository.findByCpf(novo.getCpf())).thenReturn(Optional.of(existente));

        assertThatThrownBy(() -> service.salvar(novo))
                .isInstanceOf(RegistroDuplicadoException.class)
                .hasMessageContaining("CPF");
    }

    @Test
    void salvar_celularDuplicado_lancaRegistroDuplicadoException() {
        Cliente novo = novoCliente();
        Cliente existente = clienteExistente();

        when(repository.findByCpf(novo.getCpf())).thenReturn(Optional.empty());
        when(repository.findByCelular(novo.getCelular())).thenReturn(Optional.of(existente));

        assertThatThrownBy(() -> service.salvar(novo))
                .isInstanceOf(RegistroDuplicadoException.class)
                .hasMessageContaining("celular");
    }

    @Test
    void salvar_emailDuplicado_lancaRegistroDuplicadoException() {
        Cliente novo = novoCliente();
        Cliente existente = clienteExistente();

        when(repository.findByCpf(novo.getCpf())).thenReturn(Optional.empty());
        when(repository.findByCelular(novo.getCelular())).thenReturn(Optional.empty());
        when(repository.findByEmail(novo.getEmail())).thenReturn(Optional.of(existente));

        assertThatThrownBy(() -> service.salvar(novo))
                .isInstanceOf(RegistroDuplicadoException.class)
                .hasMessageContaining("email");
    }

    @Test
    void salvar_clienteExistente_mesmoCpf_mesmoId_naoLancaExcecao() {
        Cliente existente = clienteExistente();

        when(repository.findByCpf(existente.getCpf())).thenReturn(Optional.of(existente));
        when(repository.findByCelular(existente.getCelular())).thenReturn(Optional.empty());
        when(repository.findByEmail(existente.getEmail())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(existente);

        assertThatCode(() -> service.salvar(existente)).doesNotThrowAnyException();
    }

    @Test
    void salvar_clienteExistente_cpfDeOutroCliente_lancaExcecao() {
        Cliente clienteAtualizado = clienteExistente();
        clienteAtualizado.setId(2L);

        Cliente outroCliente = clienteExistente();
        outroCliente.setId(1L);

        when(repository.findByCpf(clienteAtualizado.getCpf())).thenReturn(Optional.of(outroCliente));

        assertThatThrownBy(() -> service.salvar(clienteAtualizado))
                .isInstanceOf(RegistroDuplicadoException.class);
    }
}
