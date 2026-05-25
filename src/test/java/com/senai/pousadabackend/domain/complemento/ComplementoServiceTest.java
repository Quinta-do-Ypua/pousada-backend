package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplementoServiceTest {

    @Mock
    private ComplementoRepository complementoRepository;

    @Mock
    private ReservaRepository reservaRepository;

    private ComplementoService service;

    @BeforeEach
    void setUp() {
        service = new ComplementoService(complementoRepository, reservaRepository);
    }

    private Complemento novoComplemento() {
        return Complemento.builder()
                .nome("Café da manhã")
                .valor(BigDecimal.valueOf(30))
                .descricao("Café da manhã completo")
                .build();
    }

    private Complemento complementoExistente() {
        Complemento c = novoComplemento();
        c.setId(1L);
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    @Test
    void salvar_semDuplicataNome_sucesso() {
        Complemento novo = novoComplemento();

        when(complementoRepository.findByNome(novo.getNome())).thenReturn(null);
        when(complementoRepository.save(any())).thenReturn(novo);

        assertThatCode(() -> service.salvar(novo)).doesNotThrowAnyException();
        verify(complementoRepository).save(novo);
    }

    @Test
    void salvar_nomeDuplicado_lancaBusinessException() {
        Complemento novo = novoComplemento();
        Complemento existente = complementoExistente();

        when(complementoRepository.findByNome(novo.getNome())).thenReturn(existente);

        assertThatThrownBy(() -> service.salvar(novo))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("nome");
    }

    @Test
    void salvar_atualizandoProprioNome_mesmoId_sucesso() {
        Complemento existente = complementoExistente();

        when(complementoRepository.findByNome(existente.getNome())).thenReturn(existente);
        when(complementoRepository.save(any())).thenReturn(existente);

        assertThatCode(() -> service.salvar(existente)).doesNotThrowAnyException();
    }

    @Test
    void excluir_semReservasVinculadas_exclui() {
        Complemento existente = complementoExistente();

        when(reservaRepository.existsByComplementos_Id(1L)).thenReturn(false);
        when(complementoRepository.findById(1L)).thenReturn(Optional.of(existente));

        Complemento resultado = service.excluir(1L);

        assertThat(resultado).isEqualTo(existente);
        verify(complementoRepository).delete(existente);
    }

    @Test
    void excluir_comReservasVinculadas_lancaBusinessException() {
        when(reservaRepository.existsByComplementos_Id(1L)).thenReturn(true);

        assertThatThrownBy(() -> service.excluir(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("vinculado");
    }

    @Test
    void excluir_naoEncontrado_lancaRegistroNaoEncontradoException() {
        when(reservaRepository.existsByComplementos_Id(99L)).thenReturn(false);
        when(complementoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.excluir(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class);
    }
}
