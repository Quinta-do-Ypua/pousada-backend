package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.exceptions.BusinessException;
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
class CupomServiceTest {

    @Mock
    private CupomRepository repository;

    private CupomService service;

    @BeforeEach
    void setUp() {
        service = new CupomService(repository);
    }

    private Cupom novoCupomValido() {
        return Cupom.builder()
                .codigo("PROMO10")
                .nome("Promoção 10%")
                .dataDeInicio(LocalDate.now().plusDays(1))
                .dataDeVencimento(LocalDate.now().plusDays(30))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();
    }

    private Cupom cupomExistente() {
        Cupom c = novoCupomValido();
        c.setId(1L);
        c.setDataCriacao(LocalDateTime.now());
        return c;
    }

    @Test
    void salvar_novoCupomValido_sucesso() {
        Cupom cupom = novoCupomValido();

        when(repository.findByCodigo(cupom.getCodigo())).thenReturn(null);
        when(repository.save(any())).thenReturn(cupom);

        assertThatCode(() -> service.salvar(cupom)).doesNotThrowAnyException();
        verify(repository).save(cupom);
    }

    @Test
    void salvar_dataInicioAposVencimento_lancaBusinessException() {
        Cupom cupom = novoCupomValido();
        cupom.setDataDeInicio(LocalDate.now().plusDays(10));
        cupom.setDataDeVencimento(LocalDate.now().plusDays(5));

        assertThatThrownBy(() -> service.salvar(cupom))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("início");
    }

    @Test
    void salvar_novoCupom_dataInicioNoPassado_lancaBusinessException() {
        Cupom cupom = novoCupomValido();
        cupom.setDataDeInicio(LocalDate.now().minusDays(1));
        cupom.setDataDeVencimento(LocalDate.now().plusDays(10));

        assertThatThrownBy(() -> service.salvar(cupom))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("data atual");
    }

    @Test
    void salvar_codigoDuplicado_lancaBusinessException() {
        Cupom novo = novoCupomValido();
        Cupom existente = cupomExistente();
        existente.setId(99L);

        when(repository.findByCodigo(novo.getCodigo())).thenReturn(existente);

        assertThatThrownBy(() -> service.salvar(novo))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("código");
    }

    @Test
    void salvar_atualizandoCupomExistente_mesmoCodigo_sucesso() {
        Cupom existente = cupomExistente();

        when(repository.findByCodigo(existente.getCodigo())).thenReturn(existente);
        when(repository.findById(existente.getId())).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenReturn(existente);

        assertThatCode(() -> service.salvar(existente)).doesNotThrowAnyException();
    }

    @Test
    void salvar_cupomExistente_dataInicioAnteriorAoCadastrado_lancaBusinessException() {
        Cupom existente = cupomExistente();
        existente.setDataDeInicio(LocalDate.now().plusDays(5));

        Cupom cupomCadastrado = cupomExistente();
        cupomCadastrado.setDataDeInicio(LocalDate.now().plusDays(10));

        when(repository.findById(existente.getId())).thenReturn(Optional.of(cupomCadastrado));

        assertThatThrownBy(() -> service.salvar(existente))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("data de início");
    }

    @Test
    void salvar_cupomExistente_dataInicioIgualAoCadastrado_sucesso() {
        Cupom existente = cupomExistente();
        existente.setDataDeInicio(LocalDate.now().plusDays(5));

        Cupom cupomCadastrado = cupomExistente();
        cupomCadastrado.setDataDeInicio(LocalDate.now().plusDays(5));

        when(repository.findByCodigo(existente.getCodigo())).thenReturn(null);
        when(repository.findById(existente.getId())).thenReturn(Optional.of(cupomCadastrado));
        when(repository.save(any())).thenReturn(existente);

        assertThatCode(() -> service.salvar(existente)).doesNotThrowAnyException();
    }
}
