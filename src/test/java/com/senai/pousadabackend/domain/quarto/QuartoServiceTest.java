package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.service.ReservaService;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.exceptions.RegistrosVinculadosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuartoServiceTest {

    @Mock
    private QuartoRepository quartoRepository;

    @Mock
    private ReservaService reservaService;

    private QuartoService service;

    @BeforeEach
    void setUp() {
        service = new QuartoService(quartoRepository, reservaService);
    }

    private Quarto novoQuarto() {
        return Quarto.builder()
                .nome("Suite Master")
                .capacidade(2)
                .valorDiaria(BigDecimal.valueOf(200))
                .build();
    }

    private Quarto quartoExistente() {
        Quarto q = novoQuarto();
        q.setId(1L);
        q.setDataCriacao(LocalDateTime.now());
        return q;
    }

    @Test
    void salvar_novoQuarto_semDuplicatas_sucesso() {
        Quarto quarto = novoQuarto();

        when(quartoRepository.findByNome(quarto.getNome())).thenReturn(null);
        when(quartoRepository.save(any())).thenReturn(quarto);

        Quarto resultado = service.salvar(quarto);

        assertThat(resultado).isEqualTo(quarto);
        verify(quartoRepository).save(quarto);
    }

    @Test
    void salvar_novoQuarto_nomeDuplicado_lancaRegistroDuplicadoException() {
        Quarto novo = novoQuarto();
        Quarto existente = quartoExistente();

        when(quartoRepository.findByNome(novo.getNome())).thenReturn(existente);

        assertThatThrownBy(() -> service.salvar(novo))
                .isInstanceOf(RegistroDuplicadoException.class)
                .hasMessageContaining("nome");
    }

    @Test
    void salvar_quartoExistente_ignoraVerificacaoDuplicata() {
        Quarto existente = quartoExistente();

        when(quartoRepository.save(any())).thenReturn(existente);

        assertThatCode(() -> service.salvar(existente)).doesNotThrowAnyException();
        verify(quartoRepository, never()).findByNome(any());
    }

    @Test
    void excluir_semReservasVinculadas_exclui() {
        Quarto quarto = quartoExistente();

        when(quartoRepository.findById(1L)).thenReturn(Optional.of(quarto));
        when(reservaService.buscarPorQuarto(quarto)).thenReturn(List.of());

        Quarto resultado = service.excluir(1L);

        assertThat(resultado).isEqualTo(quarto);
        verify(quartoRepository).delete(quarto);
    }

    @Test
    void excluir_comReservasVinculadas_lancaRegistrosVinculadosException() {
        Quarto quarto = quartoExistente();
        Reserva reserva = new Reserva();

        when(quartoRepository.findById(1L)).thenReturn(Optional.of(quarto));
        when(reservaService.buscarPorQuarto(quarto)).thenReturn(List.of(reserva));

        assertThatThrownBy(() -> service.excluir(1L))
                .isInstanceOf(RegistrosVinculadosException.class)
                .hasMessageContaining("reservas");
    }

    @Test
    void excluir_naoEncontrado_lancaRegistroNaoEncontradoException() {
        when(quartoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.excluir(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class);
    }

    @Test
    void salvar_novoQuarto_nomeSemMatch_sucesso() {
        Quarto novo = novoQuarto();

        when(quartoRepository.findByNome(novo.getNome())).thenReturn(null);
        when(quartoRepository.save(any())).thenReturn(novo);

        Quarto resultado = service.salvar(novo);

        assertThat(resultado.getNome()).isEqualTo("Suite Master");
    }
}
