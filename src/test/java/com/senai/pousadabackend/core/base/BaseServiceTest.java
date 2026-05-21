package com.senai.pousadabackend.core.base;

import com.senai.pousadabackend.domain.amenidade.Amenidade;
import com.senai.pousadabackend.domain.amenidade.AmenidadeRepository;
import com.senai.pousadabackend.domain.amenidade.AmenidadeService;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseServiceTest {

    @Mock
    private AmenidadeRepository repository;

    private AmenidadeService service;

    @BeforeEach
    void setUp() {
        service = new AmenidadeService(repository);
    }

    private Amenidade novaAmenidade() {
        return Amenidade.builder().nome("Piscina").build();
    }

    private Amenidade amenidadeExistente() {
        Amenidade a = Amenidade.builder().id(1L).nome("Piscina").build();
        a.setDataCriacao(LocalDateTime.now());
        return a;
    }

    @Test
    void salvar_novaEntidade_chamaRepoSave() {
        Amenidade nova = novaAmenidade();
        when(repository.save(nova)).thenReturn(nova);

        Amenidade resultado = service.salvar(nova);

        assertThat(resultado).isEqualTo(nova);
        verify(repository).save(nova);
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    void salvar_entidadeExistente_chamaRepoSaveAndFlush() {
        Amenidade existente = amenidadeExistente();
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.saveAndFlush(existente)).thenReturn(existente);

        Amenidade resultado = service.salvar(existente);

        assertThat(resultado).isEqualTo(existente);
        verify(repository).saveAndFlush(existente);
        verify(repository, never()).save(any());
    }

    @Test
    void salvarEmLote_chamaSalvarParaCadaEntidade() {
        Amenidade a1 = novaAmenidade();
        Amenidade a2 = Amenidade.builder().nome("WiFi").build();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        List<Amenidade> resultado = service.salvarEmLote(List.of(a1, a2));

        assertThat(resultado).hasSize(2);
        verify(repository, times(2)).save(any());
    }

    @Test
    void buscarPorId_encontrado_retornaEntidade() {
        Amenidade existente = amenidadeExistente();
        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        Amenidade resultado = service.buscarPorId(1L);

        assertThat(resultado).isEqualTo(existente);
    }

    @Test
    void buscarPorId_naoEncontrado_lancaRegistroNaoEncontradoException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    @Test
    void buscarPorId_idNulo_lancaIllegalArgumentException() {
        assertThatThrownBy(() -> service.buscarPorId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("obrigatório");
    }

    @Test
    void excluir_encontrado_deletaERetorna() {
        Amenidade existente = amenidadeExistente();
        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        Amenidade resultado = service.excluir(1L);

        assertThat(resultado).isEqualTo(existente);
        verify(repository).delete(existente);
    }

    @Test
    void excluir_naoEncontrado_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.excluir(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class);
    }

    @Test
    void throwIfNotExists_existe_semExcecao() {
        when(repository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> service.throwIfNotExists(1L)).doesNotThrowAnyException();
    }

    @Test
    void throwIfNotExists_naoExiste_lancaRegistroNaoEncontradoException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.throwIfNotExists(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    @Test
    void throwIfNotExists_idNulo_lancaIllegalArgumentException() {
        assertThatThrownBy(() -> service.throwIfNotExists(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void listarPaginado_retornaPagina() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Amenidade> page = new PageImpl<>(List.of(amenidadeExistente()));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Amenidade> resultado = service.listarPaginado(pageable);

        assertThat(resultado.getContent()).hasSize(1);
    }

    @Test
    void buscarPorSpecification_parametroNulo_usaSpecNula() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Amenidade> page = new PageImpl<>(List.of());
        when(repository.findAll((Specification<Amenidade>) null, pageable)).thenReturn(page);

        Page<Amenidade> resultado = service.buscarPorSpecification(null, pageable);

        assertThat(resultado).isNotNull();
        verify(repository).findAll((Specification<Amenidade>) null, pageable);
    }

    @Test
    void buscarPorSpecification_parametroBranco_usaSpecNula() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Amenidade> page = new PageImpl<>(List.of());
        when(repository.findAll((Specification<Amenidade>) null, pageable)).thenReturn(page);

        Page<Amenidade> resultado = service.buscarPorSpecification("   ", pageable);

        assertThat(resultado).isNotNull();
        verify(repository).findAll((Specification<Amenidade>) null, pageable);
    }
}
